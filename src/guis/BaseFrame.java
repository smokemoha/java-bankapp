package guis;

import db_objs.User;

import javax.swing.*;
import java.awt.*;

/**
 * Creating an abstract class that serves as the blueprint for all GUIs.
 * This class provides consistent styling and initialization for all screens.
 */
public abstract class BaseFrame extends JFrame {
    // Store user information
    protected User user;
    
    // Theme colors for consistent styling across the application
    protected static final Color DARK_PURPLE = new Color(75, 0, 130);
    protected static final Color LIGHT_PURPLE = new Color(147, 112, 219);
    protected static final Color ACCENT_COLOR = new Color(186, 85, 211);
    protected static final Color TEXT_COLOR = Color.WHITE;
    protected static final Color FIELD_BACKGROUND = new Color(245, 245, 255);
    protected static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    protected static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    protected static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    protected static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 18);
    protected static final Font LINK_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    
    public BaseFrame(String title) {
        initialize(title);
    }
    
    public BaseFrame(String title, User user) {
        // Initialize user
        this.user = user;
        initialize(title);
    }
    
    private void initialize(String title) {
        // Instantiate JFrame properties and add a title to the bar
        setTitle(title);
        
        // Set size (in pixels)
        setSize(420, 600);
        
        // Terminate program when the GUI is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Set layout to null to have absolute layout
        setLayout(null);
        
        // Prevent GUI from being resized
        setResizable(false);
        
        // Launch the GUI in the center of the screen
        setLocationRelativeTo(null);
        
        // Set application icon (optional)
        // setIconImage(new ImageIcon("path/to/icon.png").getImage());
        
        // Call on the subclass' addGuiComponent()
        addGuiComponents();
    }
    
    /**
     * This method will need to be defined by subclasses when this class is being inherited from
     */
    protected abstract void addGuiComponents();
    
    /**
     * Creates a gradient panel that serves as the background for all screens
     */
    protected class GradientPanel extends JPanel {
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
    
    /**
     * Creates a styled button with hover effects
     */
    protected JButton createStyledButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(ACCENT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(DARK_PURPLE);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }
        });
        
        return button;
    }
    
    /**
     * Creates a styled text field
     */
    protected JTextField createStyledTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setFont(FIELD_FONT);
        textField.setBackground(FIELD_BACKGROUND);
        textField.setForeground(DARK_PURPLE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return textField;
    }
    
    /**
     * Creates a styled password field
     */
    protected JPasswordField createStyledPasswordField(int x, int y, int width, int height) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, width, height);
        passwordField.setFont(FIELD_FONT);
        passwordField.setBackground(FIELD_BACKGROUND);
        passwordField.setForeground(DARK_PURPLE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return passwordField;
    }
    
    /**
     * Creates a styled label
     */
    protected JLabel createStyledLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    /**
     * Creates a styled clickable link label
     */
    protected JLabel createStyledLinkLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel("<html><a style='color:#E0E0FF;'>" + text + "</a></html>");
        label.setBounds(x, y, width, height);
        label.setFont(LINK_FONT);
        label.setForeground(TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                label.setText("<html><a style='color:#FFC0FF;'>" + text + "</a></html>");
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                label.setText("<html><a style='color:#E0E0FF;'>" + text + "</a></html>");
            }
        });
        
        return label;
    }
    
    /**
     * Shows a styled message dialog
     */
    protected void showStyledMessage(Component parentComponent, String message, String title, int messageType) {
        UIManager.put("OptionPane.background", new Color(230, 230, 250));
        UIManager.put("Panel.background", new Color(230, 230, 250));
        UIManager.put("OptionPane.messageForeground", DARK_PURPLE);
        UIManager.put("Button.background", ACCENT_COLOR);
        UIManager.put("Button.foreground", TEXT_COLOR);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
        
        // Reset UI manager defaults
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.font", null);
    }
}