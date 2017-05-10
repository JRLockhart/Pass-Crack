package passbreak;

// Import tools to be used
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class StrengthChart extends JPanel {

    private final InterfaceConsole CONSOLE;
    private int seconds = 0;
    private final int WIDTH = 75;      // Determine width of chart bar
    private BufferedImage chartImage;  // Stores current bar graph image

    // Analog chart constructor
    public StrengthChart(InterfaceConsole console) {
        this.CONSOLE = console;
        setChart(0);
    } // End chart constructor

    // Chart setter method
    public void setChart(int seconds) {
        this.seconds = seconds;
        this.repaint();
    } // End timer setter method

    // Overridden paint method refreshes chart bar on update
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Generate new image as quickly as possible
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (chartImage == null) {
            chartImage = (BufferedImage) (this.createImage(100, 150));
            Graphics2D gc = chartImage.createGraphics();
            // Generate new image as quickly as possible
            gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            drawChart(gc);
        }
        // Draw the chart from the precomputed image
        g2.drawImage(chartImage, null, 0, 0);
        drawChart(g2);
    } // End overridden paint method 

    // Draw method for chart bar
    private void drawChart(Graphics2D g) {
        // Add background field
        g.setColor(new Color(52, 55, 59));
        g.fillRect(0, 0, 150, 150);
        // Add highlighting to chart bar, shade from red to green over 30 minutes
        Color shade1 = new Color(0, 255, 0);
        Color shade2 = new Color(255, 0, 0);
        int height = 0;
        int y = 134;
        if (seconds <= 1785) {
            shade1 = new Color(255 - seconds / 7, 0 + seconds / 7, 0);
            if (seconds > 0 && seconds < 15) {
                height = 1;
            } else {
                height = seconds / 15;
                y = 135 - (seconds / 15);
            }
        } else {
            // Do not exceed RGB maximum value of 255 (1785/7=255)
            shade1 = new Color(0, 255, 0);
            // Do not exceed chart bar maximum height of 120 (1800/15=120)
            height = 120;
            y = 15;
        }
        g.setPaint(new GradientPaint(0, y, shade1, 0, 135, shade2));
        g.fillRect(0, y, WIDTH, height);
        // Add the tic marks
        g.setColor(Color.BLACK);
        drawRuler(g);
    } // End draw chart bar method

    // Draw method for chart ruler
    private void drawRuler(Graphics g) {
        g.fillRect(97, 15, 99, 120);
        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                g.drawLine(92, i * 12 + 3, 99, i * 12 + 3);
            } else {
                g.drawLine(84, i * 12 + 3, 99, i * 12 + 3);
            }
        }
    } // End draw chart ruler method

}
