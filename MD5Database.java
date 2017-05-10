package passbreak;

// Import tools to be used
import java.io.*;
import java.net.*;
import javax.swing.*;

// Start of call MD5Database
public class MD5Database extends JPanel {

    private final InterfaceConsole CONSOLE;
    private boolean match = false;
    
    // Match getter method
    public boolean getMatch() {
        return match;
    } // End match getter method

    // Constructor to pull in hash string
    public MD5Database(InterfaceConsole console) throws Exception {
        this.CONSOLE = console;

        // Try and send the hash through the api and check if the hash exists
        URL url = new URL("https://md5db.net/api/" + CONSOLE.getHash());
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String strTemp = "";

        // If the hash is found, print out the value
        while (null != (strTemp = br.readLine())) {
            match = true;
        }

    } // End of constructor

} // End of class MD5Database
