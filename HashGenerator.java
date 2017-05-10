package passbreak;

// Import tools to be used
import java.io.*;
import java.security.*;
import javax.xml.bind.*;

// Start of class HashGenerator
public class HashGenerator {
    
    // Constructor method to build hash
    public String getMD5Hash(String data) {
        
        String result = null;
        
        // Try to create and throw method to error if it exists
        try {
            // Create object and use MD5 algorithm to create hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));

            // Make it printable
            return bytesToHex(hash); 
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.err.println(ex);
        } // End of catch
        
        return result;
    } // End of getMD5Hash
    
    /**
     * Use javax.xml.bind.DatatypeConverter class in JDK to convert byte array
     * to a hexadecimal string. Because it will make it all capitalize, we will
     * use toLowerCase method so that is is compatible with MD5 API
     */
    private String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash).toLowerCase();
    } // End of bytesToHex method
} // End of HashGen class
