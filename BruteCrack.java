package passbreak;

// Import tools to be used
import java.util.Iterator;

// Start of class BruteCrack
public class BruteCrack implements Iterator<String> {

    // Characters that are used to crack the password
    private static final char[] CHARACTERS = new char[]{
        'a','e','o','r','i','s','n','1','t','l','2','m','d',
        '0','c','p','3','h','b','u','k','4','5','g',
        '9','6','8','7','y','f','w','j','v','z','x',
        'q','A','S','E','R','B','T','M','L','N','P',
        'O','I','D','C','H','G','K','F','J','U','W',
        'Y','V','Z','Q','X',' '};
    
    // Set the value of characters to final so it can't be changed
    private static final int MAX_INDEX = CHARACTERS.length - 1;
    private boolean findNext = true;
    private final int[] indexes;

    // Length is passed from main method, set the length of the indexes
    public BruteCrack(final int length) {
        indexes = new int[length];
    }

    @Override
    public boolean hasNext() {
        if (!findNext) {
            return false;
        }
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] < MAX_INDEX) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String next() {
        if (!findNext || !hasNext()) {
            return null;
        }
        String next = produceString();
        adjustIndexes();
        return next;
    }

    // Append the characters together and return the string once the password matches
    private String produceString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indexes.length; i++) {
            sb.append(CHARACTERS[indexes[i]]);
        }
        return sb.toString();
    }

    // Move the index to the next character
    private void adjustIndexes() {
        int i;
        for(i = 0 ; i < indexes.length ; i++) {
            if(indexes[i] < MAX_INDEX) {
                indexes[i] = indexes[i] + 1;
                break;
            }
        }
        for(int j=0; j < i; j++) {
            indexes[j] = 0;
        }
    }
} // End of classBruteCrack