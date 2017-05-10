package passbreak;

import java.awt.event.WindowAdapter;
import javax.swing.*;

public class PassDialog extends JFrame {

    // Load icon from application's top-level directory
    private static final ImageIcon ICON = new ImageIcon("icon.png");

    // Define GUI elements
    private final InterfacePanel GUI;

    // Application primary interface constructor
    public PassDialog(InterfacePanel gui,
            String input, String title, int width, int height) {
        super(title);
        this.GUI = gui;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        add(new PasswordGenerator(gui, input));

        // Overridden method responding to program termination by user
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                setVisible(false);
                GUI.btns.resetButtons();
            } // End overridden window closing method
        };
        addWindowListener(windowAdapter);
    } // End primary interface constructor

    // Method to display primary interface
    public void display() {
        setVisible(true);
    } // End primary interface display method

}
