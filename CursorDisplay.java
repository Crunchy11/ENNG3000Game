import java.awt.Image;
import java.awt.geom.Point2D;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CursorDisplay extends JLabel {

    // Where the centre of the hammer head sits inside hammer.png, as a fraction
    // of the image's width and height. This point is placed on the player's position.
    private static final double HOTSPOT_X = 0.3906;
    private static final double HOTSPOT_Y = 0.3906;

    private CursorSource source;
    private final Timer refreshTimer;
    private final int hotX;
    private final int hotY;

    public CursorDisplay(CursorSource initialSource) {
        this.source = initialSource;
        int size = GameConfig.cursorSize;

        URL url = CursorDisplay.class.getResource("/images/hammer.png");
        if (url != null) {
            Image scaled = new ImageIcon(url).getImage()
                    .getScaledInstance(size, size, Image.SCALE_REPLICATE);   // keep pixels sharp
            setIcon(new ImageIcon(scaled));
            hotX = (int) Math.round(size * HOTSPOT_X);
            hotY = (int) Math.round(size * HOTSPOT_Y);
        } else {
            System.err.println("Image not found: /images/hammer.png");
            setText("🖱");   // fall back to the old emoji
            hotX = size / 2;
            hotY = size / 2;
        }

        setSize(size, size);
        setOpaque(false);

        refreshTimer = new Timer(16, e -> updatePosition()); // ~60fps poll
        refreshTimer.start();
    }

    public void setSource(CursorSource newSource) {
        this.source = newSource;
    }

    public void stopUpdating() {
        refreshTimer.stop();
    }

    // Puts the centre of the hammer head exactly on the player's position.
    public void updatePosition() {
        Point2D.Double p = source.getPosition();
        setLocation((int) Math.round(p.x) - hotX, (int) Math.round(p.y) - hotY);
    }
}