package db_objs;



import com.mysql.cj.x.protobuf.MysqlxPrepare;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class MyJDBC {

    // Path to your configuration file
    private static final String CONFIG_FILE = "dbconfig.properties";

    // Database configuration loaded from the properties file
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;

    // Static block to load the properties once at class load time
    static {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            DB_URL = props.getProperty("db.url");
            DB_USERNAME = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get a database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // if valid return an object with the user's information
    public static User validateLogin(String username, String password) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ? AND password = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");
                    return new User(userId, username, password, currentBalance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // registers new user to the database
    // true - register success, false - register fails
    public static boolean register(String username, String password) {
        try {
            if (!checkUser(username)) {
                try (Connection connection = getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO users(username, password, current_balance) VALUES(?, ?, ?)")) {

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setBigDecimal(3, BigDecimal.ZERO);
                    preparedStatement.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // check if username already exists in the db
    // true - user exists, false - user doesn't exist
    private static boolean checkUser(String username) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?")) {

            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // true - update to db was a success, false - update fails
    public static boolean addTransactionToDatabase(Transaction transaction) {
        try (Connection connection = getConnection();
             PreparedStatement insertTransaction = connection.prepareStatement(
                     "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date) " +
                             "VALUES(?, ?, ?, NOW())")) {

            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());
            insertTransaction.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // true - update balance successful, false - update fails
    public static boolean updateCurrentBalance(User user) {
        try (Connection connection = getConnection();
             PreparedStatement updateBalance = connection.prepareStatement(
                     "UPDATE users SET current_balance = ? WHERE id = ?")) {

            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());
            updateBalance.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // true - transfer was a success, false - transfer fails
    public static boolean transfer(User user, String transferredUsername, float transferAmount) {
        try (Connection connection = getConnection();
             PreparedStatement queryUser = connection.prepareStatement(
                     "SELECT * FROM users WHERE username = ?")) {

            queryUser.setString(1, transferredUsername);
            try (ResultSet resultSet = queryUser.executeQuery()) {
                while (resultSet.next()) {
                    User transferredUser = new User(
                            resultSet.getInt("id"),
                            transferredUsername,
                            resultSet.getString("password"),
                            resultSet.getBigDecimal("current_balance")
                    );

                    Transaction transferTransaction = new Transaction(
                            user.getId(),
                            "Transfer",
                            new BigDecimal(-transferAmount),
                            null
                    );

                    Transaction receivedTransaction = new Transaction(
                            transferredUser.getId(),
                            "Transfer",
                            new BigDecimal(transferAmount),
                            null
                    );

                    transferredUser.setCurrentBalance(
                            transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount))
                    );
                    updateCurrentBalance(transferredUser);

                    user.setCurrentBalance(
                            user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount))
                    );
                    updateCurrentBalance(user);

                    addTransactionToDatabase(transferTransaction);
                    addTransactionToDatabase(receivedTransaction);

                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // get all transactions (used for past transactions)
    public static ArrayList<Transaction> getPastTransaction(User user) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement selectAllTransaction = connection.prepareStatement(
                     "SELECT * FROM transactions WHERE user_id = ?")) {

            selectAllTransaction.setInt(1, user.getId());
            try (ResultSet resultSet = selectAllTransaction.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction(
                            user.getId(),
                            resultSet.getString("transaction_type"),
                            resultSet.getBigDecimal("transaction_amount"),
                            resultSet.getDate("transaction_date")
                    );
                    pastTransactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pastTransactions;
    }
}
