package passbreak;

// Import tools to be used
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.*;
import javax.swing.border.*;

// Define appearance and layout of buttons within the GUI
public class InterfaceConsole extends JPanel {

    // Define GUI elements
    private final InterfacePanel GUI;
    private final JButton ANALYZE_BTN = new JButton("Analyze");
    private final JButton RESET_BTN = new JButton("Reset");
    private final JButton EXIT_BTN = new JButton("Exit");
    private final AnalogClock CLOCK = new AnalogClock(this);
    private final JLabel MESSAGE = new JLabel();
    private final StrengthChart STRENGTH = new StrengthChart(this);
    private final double TYPES = 62; // Stores number of allowed characters
    private int duration = 0;
    private boolean running = false;
    private String hash;
    private PassDialog frame;
    private final String[] STRENGTH_LBL = {
        "Very Weak", "Weak", "Fair", "Good", "Strong", "Excellent"};

    // Load icon from application's top-level directory
    private final ImageIcon ICON = new ImageIcon("icon.png");

    // Create a background process to execute brute force cracking
    SwingWorker worker;

    // Hash getter method
    public String getHash() {
        return hash;
    } // End hash getter method

    // Output color getter method
    public Color getColor() {
        if (duration < 1785) {
            return new Color(255 - duration / 7, 0 + duration / 7, 0);
        } else {
            return new Color(0, 255, 0);
        }
    } // End output color getter method
    
    // GUI button reactivation method
    public void resetButtons() {
        GUI.menu.setMenu(true);
        ANALYZE_BTN.setEnabled(true);
        RESET_BTN.setEnabled(true);
        EXIT_BTN.setEnabled(true);
    } // End GUI button reactivation method
    
    // GUI buttons console constructor
    public InterfaceConsole(InterfacePanel gui) {

        // Add individual button console elements
        this.GUI = gui;
        CLOCK.setBackground(new Color(52, 55, 59));
        CLOCK.setBorder(new EmptyBorder(0, 0, 0, 0));
        CLOCK.setPreferredSize(new Dimension(150, 150));
        MESSAGE.setBackground(new Color(52, 55, 59));
        MESSAGE.setOpaque(true);
        MESSAGE.setBorder(new EmptyBorder(30, 0, 30, 0));
        MESSAGE.setHorizontalAlignment(JTextField.CENTER);
        MESSAGE.setPreferredSize(new Dimension(100, 150));
        STRENGTH.setBackground(new Color(52, 55, 59));
        STRENGTH.setBorder(new LineBorder(new Color(52, 55, 59), 5));
        STRENGTH.setPreferredSize(new Dimension(50, 150));
        ANALYZE_BTN.setPreferredSize(new Dimension(75, 30));
        RESET_BTN.setPreferredSize(new Dimension(75, 30));
        EXIT_BTN.setPreferredSize(new Dimension(75, 30));
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(0, 30, 0, 30));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        add(CLOCK, c);
        c.gridx++;
        add(STRENGTH, c);
        c.gridx++;
        add(MESSAGE, c);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(30, 15, 30, 15);
        c.gridwidth = 1;
        add(ANALYZE_BTN, c);
        c.gridx++;
        add(RESET_BTN, c);
        c.gridx++;
        add(EXIT_BTN, c);

        // Specify action taken upon clicking the "Analyze" button
        ANALYZE_BTN.addActionListener((ActionEvent e) -> {

            // Retrieve current values from console
            String option = gui.menu.getOption();
            boolean valid = gui.text.testInput();
            String input = gui.text.getInput();
            int length = input.length();
            int process;

            // Clear previous results and check for menu selection if valid input
            if (valid) {

                CLOCK.setClock(0);
                STRENGTH.setChart(0);
                MESSAGE.setText("");

                // Proceed to analysis only if valid choice made from drop-down menu
                if (option.compareTo("") != 0) {

                    // Specify action in response to Password Generator selection
                    if (option.equals("Random Password Generator")) {
                        // Disable all main application interface controls
                        GUI.menu.setMenu(false);
                        ANALYZE_BTN.setEnabled(false);
                        RESET_BTN.setEnabled(false);
                        EXIT_BTN.setEnabled(false);
                        frame = new PassDialog(gui, input,
                                "Specify desired changes to password", 375, 240);
                        // Overlay dialog directly on top of non-functional GUI buttons
                        frame.setLocationRelativeTo(this);
                        frame.display();
                    } // End Password Generator selection condition

                    // Specify action in response to Hash Collision selection
                    if (option.equals("Hash Collision / MD5")) {
                        // Hash the plaintext input to produce MD5 signature
                        HashGenerator hashCode = new HashGenerator();
                        hash = hashCode.getMD5Hash(input);
                        // Start of timer
                        long startTime = System.nanoTime();

                        // Attempt to perform MD5 database match on user-provided hashes
                        try {
                            MD5Database db = new MD5Database(InterfaceConsole.this);

                            // Determine if match exists in the MD5 database
                            if (db.getMatch()) {
                                long endTime = System.nanoTime();
                                duration = (int) ((endTime - startTime) / 1000000000);
                                CLOCK.setClock(duration);
                                CLOCK.stopClock();
                                CLOCK.updateClock();
                                gui.text.setOutput("'" + hash
                                        + "' matched in " + CLOCK.getSeconds()
                                        + " sec", Color.GREEN);
                            } else {
                                long endTime = System.nanoTime();
                                int time = (int) ((endTime - startTime) / 1000000000);
                                duration = 1800;
                                CLOCK.setClock(time);
                                CLOCK.stopClock();
                                CLOCK.updateClock();
                                gui.text.setOutput("No match found for '" + hash
                                        + "'", Color.GREEN);
                            } // End MD5 hash matching condition

                            // Graphically represent password strength as bargraph
                            STRENGTH.setChart(duration);
                            // Choose and color label based on match
                            MESSAGE.setText(STRENGTH_LBL[duration / 360]);
                            MESSAGE.setForeground(getColor());
                        } catch (Exception ex) {
                            gui.text.setOutput(
                                    "Environment configuration required "
                                    + "to enable this feature", Color.ORANGE);
                            JOptionPane.showMessageDialog(null,
                                    "The program returned the following error:\n" + ex
                                    + "\nPlease ensure that you are connected "
                                    + "to the Internet,\nand that you are running "
                                    + "Java SE version 8.0 or newer.\nYou can update "
                                    + "Java using the following link:\nhttp://"
                                    + "www.oracle.com/technetwork/java/javase/downloads",
                                    "Misconfigured Runtime Environment!", ERROR_MESSAGE);
                        } // End MD5 database match handler
                    } // End Hash Collision selection condition

                    // Specify action in response to Brute Cracking selection
                    if (option.equals("Brute-Force / Plaintext")) {
                        // Divide possible combos by guesses per second to calculate delay
                        double combos = Math.pow(TYPES, length);
                        int min = (int) (combos / 29000000) / 60;
                        String disclaimer = "Please note that actual processing "
                                + "time may differ\nfrom reported results, "
                                + "and vary between systems.\n"
                                + "Okay to proceed with brute-force attack?";
                        process = 1; // Set default selection to do not accept/cancel run

                        // Prompt the user to acknowledge possible processing delay
                        if (min < 1) {
                            process = JOptionPane.showConfirmDialog(null,
                                    "Preparing to analyze the password '"
                                    + gui.text.getInput()
                                    + "' ...\nEstimated time-to-crack is under "
                                    + "1 minute.\n" + disclaimer,
                                    "Initializing Brute-Force Attack...",
                                    YES_NO_OPTION);
                        } else {
                            process = JOptionPane.showConfirmDialog(null,
                                    "Preparing to analyze the password '"
                                    + gui.text.getInput()
                                    + "' ...\nEstimated processing time is around "
                                    + min + " minutes.\n" + disclaimer,
                                    "Initializing Brute-Force Attack...",
                                    YES_NO_OPTION);
                        } // End processing delay condition

                        // Initialize BruteCrack on confirmation
                        if (process == 0) {
                            // Add processing status notification to output panel
                            gui.text.setOutput("Running ... please wait", Color.CYAN);
                            CLOCK.startClock();
                            running = true;

                            // Run BruteCrack as a background process to prevent GUI from locking
                            worker = new SwingWorker() {

                                // Overridden background method to handle BruteCrack
                                @Override
                                protected Void doInBackground() {
                                    // Create crack object and pass length to constructor
                                    BruteCrack crack = new BruteCrack(length);
                                    // Start of timer
                                    long startTime = System.nanoTime();
                                    // Performs analysis on each elements until completion or error
                                    crack.forEachRemaining(passCrack -> {
                                        if (input.equals(passCrack)) {
                                            long endTime = System.nanoTime();
                                            duration = (int) ((endTime - startTime) / 1000000000);
                                            worker.cancel(true);
                                        } // End conditional loop to identify matching values
                                    });
                                    return null;
                                } // End overridden background processing method

                                // Overridden method to set output parameters on completion
                                @Override
                                protected void done() {
                                    // Update the clock face with final time
                                    CLOCK.setClock(duration);
                                    CLOCK.stopClock();
                                    CLOCK.updateClock();
                                    // Print run information to output field
                                    gui.text.setOutput("The value '" + input
                                            + "' was cracked in " + CLOCK.getSeconds()
                                            + " sec", Color.GREEN);
                                    // Graphically represent password strength as bargraph
                                    STRENGTH.setChart(duration);
                                    // Choose and color label based on time-to-crack
                                    MESSAGE.setForeground(getColor());
                                    if (duration <= 1800) {
                                        MESSAGE.setText(STRENGTH_LBL[duration / 360]);
                                    } else {
                                        // Do not exceed maximum array index of 5 (1800/360=5)
                                        MESSAGE.setText(STRENGTH_LBL[5]);
                                    }
                                    running = false;
                                } // End overridden thread completion method

                            };
                            worker.execute(); // Run the background process and return results
                        } // End processing delay acceptance condition
                    } // End Brute Cracking selection condition
                } else {
                    gui.text.setOutput("Please select an option "
                            + "from the drop-down menu.", Color.ORANGE);
                } // End drop-down menu option condition
            } // End input validation condition

        }); // End action method for the "Analyze" button

        // Specify action taken upon clicking the "Reset" button
        RESET_BTN.addActionListener((ActionEvent e) -> {

            // Terminate any running processes and clear existing selections
            if (running) {
                worker.cancel(true);
                gui.text.setOutput("Brute force attack terminated by user", Color.ORANGE);
            } else {
                gui.text.setOutput("", Color.ORANGE);
            } // End clear selection and processes condition

            // Reset all fields to default states
            gui.menu.setOption("");
            gui.text.setInput("");
            CLOCK.stopClock();
            CLOCK.setClock(0);
            CLOCK.updateClock();
            STRENGTH.setChart(0);
            MESSAGE.setText("");

        }); // End action method for the "Reset" button

        // Specify action taken upon clicking the "Exit" button
        EXIT_BTN.addActionListener((ActionEvent e) -> {

            // Hide the all application interfaces and display goodbye message
            if (frame != null) frame.setVisible(false);
            SwingUtilities.getWindowAncestor(gui).setVisible(false);
            JOptionPane.showMessageDialog(null,
                    "Thank you for using the Encryption Analysis Engine!",
                    "Exiting EAE v1.6 ...", 0, ICON);
            System.exit(0);   // Exits program normally

        }); // End action method for the "Exit" button

    } // End buttons console constructor

} // End GUI buttons console class
