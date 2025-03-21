package guis;

import db_objs.MyJDBC;
import db_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    This class represents a graphical user interface (GUI) for logging into a banking application.
    It extends BaseFrame, a presumed custom class that provides a basic JFrame setup, meaning we must
    implement the abstract method addGuiComponents() to define the layout and behavior of this login window.
 */
public class LoginGui extends BaseFrame {
    // Constructor that sets the window title by passing it to the BaseFrame superclass
    public LoginGui() {
        super("Banking App Login");
    }

    // Override the abstract method from BaseFrame to add and configure all GUI components
    @Override
    protected void addGuiComponents() {
        // Create a title label for the banking application
        JLabel bankingAppLabel = new JLabel("Banking Application");

        // Set the position (x, y) and size (width, height) of the label
        // x=0 starts at the left, y=20 gives some top padding, width matches frame width, height=40
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);

        // Set a bold, large font for the title to make it stand out
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // Center the text horizontally within the label
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the frame (inherited from BaseFrame, which is a JFrame)
        add(bankingAppLabel);

        // Create a label for the username input field
        JLabel usernameLabel = new JLabel("Username:");

        // Position the label at x=20 (left margin), y=120 (below the title), with nearly full width and a height of 24
        // getWidth() returns the frame's width (e.g., 420), adjusted slightly for margins
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);

        // Use a plain font style with size 20 for readability
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // Create a text field where the user can input their username
        JTextField usernameField = new JTextField();

        // Position it below the username label, spanning most of the frame's width with a larger height for input
        usernameField.setBounds(20, 160, getWidth() - 50, 40);

        // Use a larger font for the input text to improve visibility
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        // Create a label for the password input field
        JLabel passwordLabel = new JLabel("Password:");

        // Position it further down (y=280) with similar width and height constraints
        passwordLabel.setBounds(20, 280, getWidth() - 50, 24);

        // Consistent font styling with the username label
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // Create a password field (masks input with dots) for secure password entry
        JPasswordField passwordField = new JPasswordField();

        // Position it below the password label, matching the username field's width and height
        passwordField.setBounds(20, 320, getWidth() - 50, 40);

        // Use the same large font as the username field for consistency
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // Create a button for submitting the login credentials
        JButton loginButton = new JButton("Login");

        // Position it near the bottom (y=460) with full width minus margins, and a height of 40
        loginButton.setBounds(20, 460, getWidth() - 50, 40);

        // Bold font to emphasize the button's importance
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));

        // Add an action listener to handle the button click event
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the text entered in the username field
                String username = usernameField.getText();

                // Retrieve the password as a char array and convert it to a String
                // JPasswordField.getPassword() returns a char[] for security reasons
                String password = String.valueOf(passwordField.getPassword());

                // Validate the login credentials using a custom MyJDBC class method
                // This presumably checks a database for a matching username and password
                User user = MyJDBC.validateLogin(username, password);

                // Check if the login was successful (user object is not null)
                if (user != null) {
                    // If valid, close the current login window
                    LoginGui.this.dispose();

                    // Launch the main banking application GUI, passing the authenticated user object
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    // Display a success message dialog attached to the new banking GUI
                    JOptionPane.showMessageDialog(bankingAppGui, "Login Successfully!");
                } else {
                    // If login fails (user is null), show an error message attached to the login GUI
                    JOptionPane.showMessageDialog(LoginGui.this, "Login failed...");
                }
            }
        });
        add(loginButton);

        // Create a clickable label for users who need to register
        // HTML is used to style it as a hyperlink
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html>");

        // Position it at the bottom (y=510), centered across the frame's width
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);

        // Plain font style, size 20, for readability
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        // Center the text horizontally
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add a mouse listener to handle clicks on the register label
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Close the current login window
                LoginGui.this.dispose();

                // Open the registration GUI for new users
                new RegisterGui().setVisible(true);
            }
        });

        // Add the register label to the frame
        add(registerLabel);
    }
}