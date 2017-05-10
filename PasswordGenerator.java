package passbreak;

// Import tools to be used
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

// Start of Password Generator class
public class PasswordGenerator extends JPanel {
    
    // Define GUI elements
    private final InterfacePanel GUI;
    private final JCheckBox BOX_1 = new JCheckBox("Add capitals");
    private final JCheckBox BOX_2 = new JCheckBox("Add lowercase");
    private final JCheckBox BOX_3 = new JCheckBox("Add numerals");
    private final JCheckBox BOX_4 = new JCheckBox("Add symbols");
    private final JButton OK_BTN = new JButton("OK");
    private final JButton CANCEL_BTN = new JButton("Cancel");
    private boolean capital = false;
    private boolean lowercase = false;
    private boolean numeral = false;
    private boolean symbol = false;
    
    // Finalize values to not allow changes
    private static final String CAPITALS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*_=+-/";

    // Password Generator constructor
    public PasswordGenerator(InterfacePanel gui, String input) {
        this.GUI = gui;
        // Add individual GUI components to the panel
        setLayout(new GridLayout(3, 2, 15, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        add(BOX_1);
        add(BOX_2);
        add(BOX_3);
        add(BOX_4);
        add(OK_BTN);
        add(CANCEL_BTN);        
        
        // Specify action taken upon clicking the "capitals" checkbox
        BOX_1.addActionListener ((ActionEvent e) -> {
            AbstractButton ab = (AbstractButton) e.getSource();
            capital = ab.getModel().isSelected();
        }); // End action method for the "capitals" checkbox
        
        // Specify action taken upon clicking the "lowercase" checkbox
        BOX_2.addActionListener ((ActionEvent e) -> {
            AbstractButton ab = (AbstractButton) e.getSource();
            lowercase = ab.getModel().isSelected();
        }); // End action method for the "lowercase" checkbox
        
        // Specify action taken upon clicking the "numerals" checkbox
        BOX_3.addActionListener ((ActionEvent e) -> {
            AbstractButton ab = (AbstractButton) e.getSource();
            numeral = ab.getModel().isSelected();
        }); // End action method for the "numerals" checkbox
        
        // Specify action taken upon clicking the "symbols" checkbox
        BOX_4.addActionListener ((ActionEvent e) -> {
            AbstractButton ab = (AbstractButton) e.getSource();
            symbol = ab.getModel().isSelected();
        }); // End action method for the "symbols" checkbox
        
        // Specify action taken upon clicking the "OK" button
        OK_BTN.addActionListener((ActionEvent e) -> {
            String newPass = generatePswd(
                    input, capital, lowercase, numeral, symbol);
            gui.text.setOutput("Your new password is: " + newPass, Color.GREEN);
        }); // End action method for the "OK" button  
        
        // Specify action taken upon clicking the "Cancel" button
        CANCEL_BTN.addActionListener((ActionEvent e) -> {
            SwingUtilities.getWindowAncestor(this).setVisible(false);
            GUI.btns.resetButtons();
        }); // End action method for the "Cancel" button
        
    } // End Password Generator constructor
    
    // Value brought in are used to determing password requested by user
    public static String generatePswd(String input,
            boolean capital, boolean lowercase, boolean numeral, boolean symbol) {
        // Create random object that will be used to store value
        Random rnd = new Random();
        // Formulate a new password between 3-16 characters based on specified changes
        int cap = 0;
        int low = 0;
        int num = 0;
        int sym = 0;
        if (capital) cap = rnd.nextInt(3)+1;
        if (lowercase) low = rnd.nextInt(3)+1;
        if (numeral) num = rnd.nextInt(3)+1;
        if (symbol) sym = rnd.nextInt(3)+1;
        int max = input.length() + cap + low + num + sym;
        // Create array of the length size
        char[] pswd = new char[max];
        int index = 0;
        // Loops to create the requested value of each type of character
        for (int i = 0; i < cap; i++) {
            index = getNextIndex(rnd, max);
            pswd[index] = CAPITALS.charAt(rnd.nextInt(CAPITALS.length()));
        }
        for (int i = 0; i < low; i++) {
            index = getNextIndex(rnd, max);
            pswd[index] = LOWERS.charAt(rnd.nextInt(LOWERS.length()));
        }
        for (int i = 0; i < num; i++) {
            index = getNextIndex(rnd, max);
            pswd[index] = NUMBERS.charAt(rnd.nextInt(NUMBERS.length()));
        }
        for (int i = 0; i < sym; i++) {
            index = getNextIndex(rnd, max);
            pswd[index] = SYMBOLS.charAt(rnd.nextInt(SYMBOLS.length()));
        }
        // Loop to fill remaining length with original characters
        for (int i = 0; i < max; i++) {
            if (pswd[i] == 0) pswd[i] = input.charAt(rnd.nextInt(input.length()));
        }
        // Return value
        return String.valueOf(pswd);
    } // End generatePswd method

    // Check length to see see if next character is requested and assign
    private static int getNextIndex(Random rnd, int length) {
        int index = rnd.nextInt(length);
        return index;
    } // End of getNextIndex method

} // End of Password Generator class
