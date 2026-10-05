import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CursorDisplay extends JLabel {
    private CursorSource source;
    private final Timer refreshTimer;

    public CursorDisplay(CursorSource initialSource) {
        this.source = initialSource;
        setText("\uD83D\uDDB1");   // pointer emoji, swap for whatever fits your theme
        setSize(50, 50);
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

    public void updatePosition() {
        Point2D.Double p = source.getPosition();
        setLocation((int) p.x -  getWidth() / 2, (int)p.y - getHeight() / 2);
    }
}