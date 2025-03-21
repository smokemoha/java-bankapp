package guis;

import db_objs.MyJDBC;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGui extends BaseFrame {
    
    // Define colors for the theme
    private static final Color DARK_PURPLE = new Color(75, 0, 130);
    private static final Color LIGHT_PURPLE = new Color(147, 112, 219);
    private static final Color ACCENT_COLOR = new Color(186, 85, 211);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color FIELD_BACKGROUND = new Color(245, 245, 255);
    
    public RegisterGui() {
        super("Banking App Register");
    }

    @Override
    protected void addGuiComponents() {
        // Set background gradient panel
        setContentPane(new GradientPanel());
        setLayout(null);
        
        // Create banking app label with stylish look
        JLabel bankingAppLabel = new JLabel("Banking Application");
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        bankingAppLabel.setForeground(TEXT_COLOR);
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankingAppLabel);

        // Create a main panel to hold form components
        JPanel formPanel = new JPanel(null);
        formPanel.setOpaque(false);
        formPanel.setBounds(20, 80, getWidth() - 40, 390);
        add(formPanel);

        // Username label with improved style
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 10, formPanel.getWidth() - 20, 24);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(TEXT_COLOR);
        formPanel.add(usernameLabel);

        // Styled username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(10, 40, formPanel.getWidth() - 20, 40);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBackground(FIELD_BACKGROUND);
        usernameField.setForeground(DARK_PURPLE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        formPanel.add(usernameField);

        // Password label with improved style
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 90, formPanel.getWidth() - 20, 24);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordLabel.setForeground(TEXT_COLOR);
        formPanel.add(passwordLabel);

        // Styled password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(10, 120, formPanel.getWidth() - 20, 40);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBackground(FIELD_BACKGROUND);
        passwordField.setForeground(DARK_PURPLE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        formPanel.add(passwordField);

        // Re-type password label
        JLabel rePasswordLabel = new JLabel("Re-type Password");
        rePasswordLabel.setBounds(10, 170, formPanel.getWidth() - 20, 24);
        rePasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rePasswordLabel.setForeground(TEXT_COLOR);
        formPanel.add(rePasswordLabel);

        // Styled re-type password field
        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(10, 200, formPanel.getWidth() - 20, 40);
        rePasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rePasswordField.setBackground(FIELD_BACKGROUND);
        rePasswordField.setForeground(DARK_PURPLE);
        rePasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        formPanel.add(rePasswordField);

        // Stylish register button with hover effect
        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(10, 270, formPanel.getWidth() - 20, 45);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        registerButton.setForeground(TEXT_COLOR);
        registerButton.setBackground(ACCENT_COLOR);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(DARK_PURPLE);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(ACCENT_COLOR);
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // get re password
                String rePassword = String.valueOf(rePasswordField.getPassword());

                // we will need to validate the user input
                if(validateUserInput(username, password, rePassword)){
                    // attempt to register the user to the database
                    if(MyJDBC.register(username, password)){
                        // register success
                        // dispose of this gui
                        RegisterGui.this.dispose();

                        // launch the login gui
                        LoginGui loginGui = new LoginGui();
                        loginGui.setVisible(true);

                        // create a result dialog
                        JOptionPane.showMessageDialog(loginGui, "Registered Account Successfully!");
                    }else{
                        // register failed
                        JOptionPane.showMessageDialog(RegisterGui.this, "Error: Username already taken");
                    }
                }else{
                    // invalid user input
                    JOptionPane.showMessageDialog(RegisterGui.this,
                            "Error: Username must be at least 6 characters\n" +
                            "and/or Password must match");
                }
            }
        });
        formPanel.add(registerButton);

        // User requirements hint label
        JLabel hintLabel = new JLabel("<html><small>Username must be at least 6 characters</small></html>");
        hintLabel.setBounds(10, 320, formPanel.getWidth() - 20, 20);
        hintLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        hintLabel.setForeground(new Color(230, 230, 255));
        formPanel.add(hintLabel);

        // Create login label with stylish look
        JLabel loginLabel = new JLabel("<html><a style='color:#E0E0FF;'>Have an account? Sign-in here</a></html>");
        loginLabel.setBounds(0, 480, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        loginLabel.setForeground(TEXT_COLOR);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setText("<html><a style='color:#FFC0FF;'>Have an account? Sign-in here</a></html>");
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setText("<html><a style='color:#E0E0FF;'>Have an account? Sign-in here</a></html>");
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose of this gui
                RegisterGui.this.dispose();

                // launch the login gui
                new LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }

    private boolean validateUserInput(String username, String password, String rePassword) {
        // all fields must have a value
        if(username.length() == 0 || password.length() == 0 || rePassword.length() == 0) return false;

        // username has to be at least 6 characters long
        if(username.length() < 6) return false;

        // password and repassword must be the same
        if(!password.equals(rePassword)) return false;

        // passes validation
        return true;
    }
    
    // Inner class for gradient background
    private class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            int w = getWidth();
            int h = getHeight();
            
            // Create gradient from dark to light purple
            GradientPaint gradient = new GradientPaint(0, 0, DARK_PURPLE, 0, h, LIGHT_PURPLE);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, w, h);
            
            // Add a subtle pattern overlay
            g2d.setColor(new Color(255, 255, 255, 15));
            for (int i = 0; i < h; i += 5) {
                g2d.drawLine(0, i, w, i);
            }
            
            g2d.dispose();
        }
    }
}