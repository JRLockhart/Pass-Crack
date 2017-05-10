package passbreak;

// Import tools to be used
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import javax.swing.border.*;

// Start of main class
public class PassBreak extends JFrame {

    // Load icon from application's top-level directory
    private static final ImageIcon ICON = new ImageIcon("icon.png");

    // Application primary interface constructor
    public PassBreak(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new InterfacePanel());

        // Overridden method responding to program termination by user
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                setVisible(false);
                JOptionPane.showMessageDialog(null,
                        "Thank you for using the Encryption Analysis Engine!",
                        "Exiting EAE v1.6 ...", 0, ICON);
                System.exit(0);
            } // End overridden window closing method
        };
        addWindowListener(windowAdapter);
    } // End primary interface constructor

    // Configure global user interface settings to apply theme to all windows
    public static void configureUI() {
        // Handle importing JTattoo from project library and further define theme
        try {
            // Iterate through each existing user interface element to apply theme
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                UIManager.put("RootPane.frameBorder", new LineBorder(Color.BLACK, 10));
                UIManager.put("RootPane.dialogBorder", new LineBorder(Color.BLACK, 10));
                UIManager.put("RootPane.errorDialogBorder", new LineBorder(Color.BLACK, 10));
                UIManager.put("OptionPane.border", new EmptyBorder(15, 15, 15, 15));
            } // End iterating through existing user interface elements
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        } // End of exception handler for JTattoo import
    } // End global user interface configuration method

    // Method to display primary interface
    public void display() {
        setVisible(true);
    } // End primary interface display method

    // Start of main method
    public static void main(String[] args) {
        configureUI();  // Call method to implement global interface theme
        // Formatted string to display on the application's welcome screen
        String disclaimer = "<html><body style='width:300px; padding:5px;'>"
                + "<h3 text-align:center'>Welcome to the Encryption Analysis Engine!</h3>"
                + "<hr><br>"
                + "<p>This program has been developed for educational purposes.</p>"
                + "<p>By operating this program, you agree to the following:</p>"
                + "<ol style='margin:12px;'>"
                + "<li>This system may not be used to crack any password without "
                + "obtaining prior consent from the authorized account holder.</li>"
                + "<li>This system may not be employed in a malicious manner nor "
                + "included in any suite of tools intended for illegal activities.</li>"
                + "<li>The users of this system accept full legal liability for "
                + "ensuring compliance with standards of acceptable use.</li>"
                + "<li>The developers of this system are in no way liable for "
                + "improper activities conducted by users of this system.</ol>"
                + "<p>Do you agree to abide by these Terms & Conditions?</p>";
        // Display a welcome message dialog box with predefined icon and formatting
        int consent = JOptionPane.showConfirmDialog(null, disclaimer,
                "Launching EAE v1.6 ...",
                INFORMATION_MESSAGE, YES_NO_OPTION, ICON);

        // Display the main interface upon obtaining user consent, otherwise exit
        if (consent == 0) {
            PassBreak gui = new PassBreak("Encryption Analysis Engine v1.6", 400, 300);
            gui.pack();
            gui.display();
        } else {
            System.exit(0);   // Exits program normally
        } // End of conditional statement for displaying the main interface

    } // End of main method 

} // End of class passbreak
