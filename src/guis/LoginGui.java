package guis;

import db_objs.MyJDBC;
import db_objs.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    This gui will allow user to login or launch the register gui
    This extends from the BaseFrame which means we will need to define our own addGuiComponents()
 */
public class LoginGui extends BaseFrame{
    
    // Define colors for the theme
    private static final Color DARK_PURPLE = new Color(75, 0, 130);
    private static final Color LIGHT_PURPLE = new Color(147, 112, 219);
    private static final Color ACCENT_COLOR = new Color(186, 85, 211);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color FIELD_BACKGROUND = new Color(245, 245, 255);
    
    public LoginGui(){
        super("Banking App Login");
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
        formPanel.setBounds(20, 80, getWidth() - 40, 360);
        add(formPanel);

        // Username label with improved style
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 20, formPanel.getWidth() - 20, 24);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usernameLabel.setForeground(TEXT_COLOR);
        formPanel.add(usernameLabel);

        // Styled username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(10, 50, formPanel.getWidth() - 20, 40);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setBackground(FIELD_BACKGROUND);
        usernameField.setForeground(DARK_PURPLE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        formPanel.add(usernameField);

        // Password label with improved style
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 110, formPanel.getWidth() - 20, 24);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordLabel.setForeground(TEXT_COLOR);
        formPanel.add(passwordLabel);

        // Styled password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(10, 140, formPanel.getWidth() - 20, 40);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBackground(FIELD_BACKGROUND);
        passwordField.setForeground(DARK_PURPLE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        formPanel.add(passwordField);

        // Stylish login button with hover effect
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(10, 220, formPanel.getWidth() - 20, 45);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setForeground(TEXT_COLOR);
        loginButton.setBackground(ACCENT_COLOR);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(DARK_PURPLE);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(ACCENT_COLOR);
            }
        });
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // validate login
                User user = MyJDBC.validateLogin(username, password);

                // if user is null it means invalid otherwise it is a valid account
                if(user != null){
                    // means valid login

                    // dispose this gui
                    LoginGui.this.dispose();

                    // launch bank app gui
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    // show success dialog
                    JOptionPane.showMessageDialog(bankingAppGui, "Login Successfully!");
                }else{
                    // invalid login
                    JOptionPane.showMessageDialog(LoginGui.this, "Login failed...");
                }
            }
        });
        formPanel.add(loginButton);

        // Create register label with stylish look
        JLabel registerLabel = new JLabel("<html><a style='color:#E0E0FF;'>Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0, 480, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        registerLabel.setForeground(TEXT_COLOR);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setText("<html><a style='color:#FFC0FF;'>Don't have an account? Register Here</a></html>");
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setText("<html><a style='color:#E0E0FF;'>Don't have an account? Register Here</a></html>");
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose of this gui
                LoginGui.this.dispose();

                // launch the register gui
                new RegisterGui().setVisible(true);
            }
        });
        add(registerLabel);
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