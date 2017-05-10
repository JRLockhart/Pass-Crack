package passbreak;

// Import tools to be used
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

// Define appearance of drop-down menu within the GUI
public class InterfaceOption extends JPanel {

    // Define GUI elements
    private final InterfacePanel GUI;
    private String option = "";
    private static final String[] OPTIONS = {"",
        "Brute-Force / Plaintext",
        "Hash Collision / MD5",
        "Random Password Generator"
    };
    private final JLabel OPTION_LBL = new JLabel(
            "Please select the type of input and function to perform:");
    private final JLabel INPUT_LBL = new JLabel(
            "Then enter the alphanumeric credentials to be analyzed:");
    private final JComboBox MENU = new JComboBox(OPTIONS);

    // GUI drop-down menu constructor
    public InterfaceOption(InterfacePanel gui) {
        // Add individual drop-down menu elements
        this.GUI = gui;
        setLayout(new GridLayout(3, 1, 30, 15));
        setBorder(new EmptyBorder(15, 30, 15, 30));
        option = (String) OPTIONS[0];
        add(OPTION_LBL);
        add(MENU);
        add(INPUT_LBL);
        // Specify action taken upon making drop-down menu selection
        MENU.addActionListener((ActionEvent e) -> {
            JComboBox cb = (JComboBox) e.getSource();
            option = (String) cb.getSelectedItem();
            MENU.setSelectedItem(option);
        } // End action method for drop-down menu selection
        );
    } // End drop-down menu constructor

    // Getter method for drop-down menu
    public String getOption() {
        return option;
    } // End drop-down menu getter method

    // Setter method for drop-down menu
    public void setOption(String option) {
        this.option = option;
        MENU.setSelectedItem(option);
    } // End drop-down menu setter method

    // Menu reactivation method
    public void setMenu(boolean active) {
        MENU.setEnabled(active);
    }
    
} // End GUI drop-down menu class
