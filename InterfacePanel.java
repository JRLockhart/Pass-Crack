package passbreak;

// Import tools to be used
import java.awt.*;
import javax.swing.*;
import static javax.swing.BoxLayout.*;
import javax.swing.border.*;

// Define placement of individual elements within the GUI
public class InterfacePanel extends JPanel {

    // Load icon from application's top-level directory
    private static final ImageIcon ICON = new ImageIcon("icon.png");
    
    // Define GUI elements
    protected InterfaceReader text = new InterfaceReader(this);
    protected InterfaceOption menu = new InterfaceOption(this);
    protected InterfaceConsole btns = new InterfaceConsole(this);

    // GUI constructor
    public InterfacePanel() {
        // Add individual GUI components to the panel
        setLayout(new BoxLayout(this, Y_AXIS));
        setBorder(new LineBorder(new Color(52, 55, 59), 15));
        add(menu);
        menu.setPreferredSize(new Dimension(400, 150));
        add(text);
        text.setPreferredSize(new Dimension(400, 100));
        add(btns);
        
    } // End of GUI constructor

} // End of interface panel class
