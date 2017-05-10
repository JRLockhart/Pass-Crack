package passbreak;

// Import tools to be used
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class AnalogClock extends JPanel {

    private static final float THREE_PI = (float) (3.0 * Math.PI);
    private static final float RADIUS_SECMIN = (float) (Math.PI / 30.0);
    private static final int SPACING = 10;
    private final InterfaceConsole CONSOLE;
    private final Timer TIMER;        // Runs timer to increment second count
    private BufferedImage clockImage; // Stores current clock face image
    private int seconds = 0;          // Offset due to initial constructor call
    private int minutes = 0;          // Stores value of seconds divided by 60
    private int size;                 // Determine height and width of clock face
    private int centerX;              // Stores horizontal axis middle of clock
    private int centerY;              // Stores vertical axis middle of clock
    private boolean stopped = false;  // Triggers hiding or displaying clock hands

    // Analog clock constructor

    public AnalogClock(InterfaceConsole console) {
        this.CONSOLE = console;
        TIMER = new Timer(1000, (ActionEvent e) -> {
            updateClock();
        });
    } // End analog clock constructor

    // Timer setter method
    public void setClock(int seconds) {
        this.seconds = seconds;
        this.minutes = seconds / 60;
    } // End timer setter method

    // Duration getter method
    public int getSeconds() {
        return seconds;
    } // End duration getter method
    
    // Timer start method
    public void startClock() {
        TIMER.start();
        // Deactivate trigger to display clock hands
        stopped = false;
    } // End timer start method

    // Timer update method
    public void updateClock() {
        this.repaint();
        if (!stopped) seconds++;
        if (seconds >= 60 && seconds % 60 == 0) minutes++;
    } // End timer update method

    // Timer stop method
    public void stopClock() {
        TIMER.stop();
        // Round down to last minute if more than 1 minute
        if (seconds >= 60) setClock(seconds -= seconds % 60);
        // Activate trigger to erase clock hands
        stopped = true; 
        this.repaint();
    } // End timer stop method

    // Overridden paint method refreshes clock hand positions on update
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Generate new image as quickly as possible
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        size = 150 - 2 * SPACING;
        centerX = size / 2 + SPACING;
        centerY = size / 2 + SPACING;
        if (clockImage == null) {
            clockImage = (BufferedImage) (this.createImage(150, 150));
            Graphics2D gc = clockImage.createGraphics();
            // Generate new image as quickly as possible
            gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            drawClockFace(gc);
        }
        // Draw the clock face from the precomputed image
        g2.drawImage(clockImage, null, 0, 0);
        // Draw the clock hands if still running
        drawClockHands(g);
    } // End overridden paint method 

    // Draw method for clock hands
    private void drawClockHands(Graphics g) {
        // Define the second hand angle
        int secondRadius = size / 2;
        float fseconds = seconds;
        float secondAngle = THREE_PI - (RADIUS_SECMIN * fseconds);
        // Add highlighting to clock face, shade from red to green over 30 min
        for (int i = 0; i < minutes + 1; i++) {
            Color newColor;
            if (i <= 30) newColor = new Color(255 - (i * 8), i * 8, 0);
            else newColor = new Color(0, 255, 0);
            g.setColor(newColor);
            int offset = -i * 6;
            int progress = (-seconds + (61 * minutes)) * 6;
            int startArc = 90 + offset;
            int endArc = progress;
            if (i < minutes || offset < progress + offset) {
                if (i != minutes) drawArc(g, SPACING, SPACING, startArc, -6, size);
            } else {
                drawArc(g, SPACING, SPACING, startArc, endArc, size);
            }
        }
        // Add the tic marks
        g.setColor(Color.BLACK);
        for (int sec = 0; sec < 60; sec++) {
            int ticStart;
            if (sec % 5 == 0) ticStart = size / 2 - 10;
            else ticStart = size / 2 - 1;
            drawRadius(g, centerX, centerY, RADIUS_SECMIN * sec, ticStart, size / 2);
        }
        // Add the second hand if clock is running or set to 0
        if (!stopped || seconds < 1) {
            g.setColor(Color.WHITE);
            drawRadius(g, centerX, centerY, secondAngle, 0, secondRadius);
        }
    } // End draw clock hands method

    // Draw method for clock face
    private void drawClockFace(Graphics g) {
        // Add background field
        g.setColor(new Color(52, 55, 59));
        g.fillRect(0, 0, 150, 150);
        // Add the clock face
        g.setColor(Color.BLACK);
        g.fillOval(SPACING / 2, SPACING / 2, size + SPACING, size + SPACING);
        g.setColor(Color.BLACK);
        g.fillOval(SPACING / 2, SPACING / 2, size + SPACING, size + SPACING);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(SPACING, SPACING, size, size);
    } // End draw clock face method

    // Draw method for clock hands
    private void drawRadius(Graphics g, int x, int y, double angle, int minRadius, int maxRadius) {
        float sine = (float) Math.sin(angle);
        float cosine = (float) Math.cos(angle);
        int dxmin = (int) (minRadius * sine);
        int dymin = (int) (minRadius * cosine);
        int dxmax = (int) (maxRadius * sine);
        int dymax = (int) (maxRadius * cosine);
        g.drawLine(x + dxmin, y + dymin, x + dxmax, y + dymax);
    } // End draw radius method

    // Draw method for clock highlighting
    private void drawArc(Graphics g, int x, int y, double minAngle, double maxAngle, int diameter) {
        g.fillArc(x, y, diameter, diameter, (int) (minAngle), (int) (maxAngle));
    } // End draw arc method
}
