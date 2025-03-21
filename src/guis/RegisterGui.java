package guis;

import db_objs.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    This class represents a graphical user interface (GUI) for registering a new user in a banking application.
    It extends BaseFrame, a presumed custom class that provides a basic JFrame setup, requiring us to implement
    the abstract method addGuiComponents() to define the layout and behavior of this registration window.
 */
public class RegisterGui extends BaseFrame {
    // Constructor that sets the window title by passing it to the BaseFrame superclass
    public RegisterGui() {
        super("Banking App Register");
    }

    // Override the abstract method from BaseFrame to add and configure all GUI components
    @Override
    protected void addGuiComponents() {
        // Create a title label for the banking application
        JLabel bankingAppLabel = new JLabel("Banking Application");

        // Set the position (x, y) and size (width, height) of the label
        // x=0 aligns left, y=20 adds top padding, width matches frame width, height=40
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);

        // Use a bold, large font to make the title prominent
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // Center the text horizontally within the label
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the label to the frame (inherited from BaseFrame)
        add(bankingAppLabel);

        // Create a label for the username input field
        JLabel usernameLabel = new JLabel("Username:");

        // Position it at x=20 (left margin), y=120 (below title), with nearly full width and height of 24
        // getWidth() returns the frame's width (e.g., 420), adjusted for margins
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);

        // Plain font style with size 20 for readability
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // Create a text field for the user to enter their username
        JTextField usernameField = new JTextField();

        // Position it below the username label, spanning most of the frame's width with a height of 40
        usernameField.setBounds(20, 160, getWidth() - 50, 40);

        // Larger font for the input text to enhance visibility
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(usernameField);

        // Create a label for the password input field
        JLabel passwordLabel = new JLabel("Password:");

        // Position it below the username field (y=220) with similar width and height constraints
        passwordLabel.setBounds(20, 220, getWidth() - 50, 24);

        // Consistent plain font style with size 20
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // Create a password field (masks input with dots) for secure password entry
        JPasswordField passwordField = new JPasswordField();

        // Position it below the password label, matching the username field's width and height
        passwordField.setBounds(20, 260, getWidth() - 50, 40);

        // Use the same large font as the username field for consistency
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(passwordField);

        // Create a label for the re-type password field to confirm the password
        JLabel rePasswordLabel = new JLabel("Re-type Password:");

        // Position it below the password field (y=320) with a slightly taller height for alignment
        rePasswordLabel.setBounds(20, 320, getWidth() - 50, 40);

        // Plain font style with size 20, consistent with other labels
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(rePasswordLabel);

        // Create a second password field for re-entering the password
        JPasswordField rePasswordField = new JPasswordField();

        // Position it below the re-type password label, matching other input fields
        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);

        // Consistent large font for input visibility
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));
        add(rePasswordField);

        // Create a button to submit the registration details
        JButton registerButton = new JButton("Register");

        // Position it near the bottom (y=460) with full width minus margins, and a height of 40
        registerButton.setBounds(20, 460, getWidth() - 50, 40);

        // Bold font to highlight the button's importance
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));

        // Add an action listener to handle the button click event
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the entered username
                String username = usernameField.getText();

                // Retrieve the password as a char array and convert it to a String
                String password = String.valueOf(passwordField.getPassword());

                // Retrieve the re-typed password as a char array and convert it to a String
                String rePassword = String.valueOf(rePasswordField.getPassword());

                // Validate the user input using a helper method
                if (validateUserInput(username, password, rePassword)) {
                    // If input is valid, attempt to register the user in the database
                    if (MyJDBC.register(username, password)) {
                        // Registration successful: close the current window
                        RegisterGui.this.dispose();

                        // Open the login GUI for the user to sign in
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);

                        // Show a success message attached to the login GUI
                        JOptionPane.showMessageDialog(loginGui, "Registered Account Successfully!");
                    } else {
                        // Registration failed (e.g., username already exists)
                        JOptionPane.showMessageDialog(RegisterGui.this, "Error: Username already taken");
                    }
                } else {
                    // Input validation failed: show an error message with specific requirements
                    JOptionPane.showMessageDialog(RegisterGui.this,
                            "Error: Username must be at least 6 characters\n" +
                            "and/or Password must match");
                }
            }
        });
        add(registerButton);

        // Create a clickable label for users who already have an account
        // HTML is used to style it as a hyperlink
        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Sign-in here</a></html>");

        // Position it at the bottom (y=510), centered across the frame's width
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);

        // Plain font style with size 20 for readability
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        // Center the text horizontally
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add a mouse listener to handle clicks on the login label
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Close the current registration window
                RegisterGui.this.dispose();

                // Open the login GUI for existing users
                new LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }

    // Helper method to validate user input before attempting registration
    private boolean validateUserInput(String username, String password, String rePassword) {
        // Check that all fields are non-empty
        if (username.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;

        // Ensure the username is at least 6 characters long for security
        if (username.length() < 6) return false;

        // Verify that the password and re-typed password match
        if (!password.equals(rePassword)) return false;

        // If all checks pass, the input is valid
        return true;
    }
}