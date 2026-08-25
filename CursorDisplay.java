import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.Timer;

public class CursorDisplay extends JLabel {
    private CursorSource source;
    private final Timer refreshTimer;

    public CursorDisplay(CursorSource initialSource) {
        this.source = initialSource;
        setText("\uD83D\uDDB1");   // pointer emoji, swap for whatever fits your theme
        setSize(24, 24);
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

    private void updatePosition() {
        Point p = source.getPosition();
        setLocation(p.x - getWidth() / 2, p.y - getHeight() / 2);
    }
}