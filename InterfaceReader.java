package passbreak;

// Import tools to be used
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

// Define appearance of text input field within the GUI
public class InterfaceReader extends JPanel {

    // Define GUI elements 
    private final InterfacePanel GUI;
    private final JTextField INPUT_TXT = new JTextField("");
    private final JTextField OUTPUT_TXT = new JTextField("");
    private String input = "";
    private String output = "";

    // GUI text input field constructor
    public InterfaceReader(InterfacePanel gui) {
        // Add individual text input field elements
        this.GUI = gui;
        setLayout(new GridLayout(2, 1, 15, 15));
        setBorder(new EmptyBorder(0, 30, 0, 30));
        INPUT_TXT.setBorder(new EmptyBorder(0, 15, 0, 15));
        INPUT_TXT.setBackground(Color.BLACK);
        INPUT_TXT.setForeground(Color.WHITE);
        OUTPUT_TXT.setHorizontalAlignment(SwingConstants.CENTER);
        OUTPUT_TXT.setBackground(new Color(52, 55, 59));
        OUTPUT_TXT.setBorder(new EmptyBorder(0, 0, 0, 0));
        OUTPUT_TXT.setEditable(false);
        add(INPUT_TXT);
        add(OUTPUT_TXT);
    } // End text input field constructor

    // Getter method for text inputs
    public String getInput() {
        return input;
    } // End text input getter method

    // Setter method for text inputs
    public void setInput(String input) {
        this.input = input;
        INPUT_TXT.setText(input);
    } // End text input setter method

    // Setter method for output field
    public String getOutput() {
        return output;
    } // End text input setter method

    // Setter method for output field
    public void setOutput(String output, Color color) {
        this.output = output;
        OUTPUT_TXT.setText(output);
        OUTPUT_TXT.setForeground(color);
    } // End text input setter method

    // Test raw input for compliance with formatting requirements
    public boolean testInput() {
        input = INPUT_TXT.getText();
        boolean validity = false;
        OUTPUT_TXT.setForeground(Color.ORANGE);
        // Input validation conditions
        if (input.length() < 3 || input.length() > 6) {
            output = "Please enter 3 to 6 alphanumeric characters and try again.";
        } else if (!input.matches("[a-zA-Z0-9]+")) {
            output = "Special characters are not allowed. Please try again.";
        } else {
            output = "";
            validity = true;
            OUTPUT_TXT.setForeground(Color.GREEN);
        } // End input validation conditions
        OUTPUT_TXT.setText(output);
        return validity;
    } // End input validation method

} // End GUI text input field class
