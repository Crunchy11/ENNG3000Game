import java.awt.AWTEvent;
import java.awt.geom.Point2D;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class MouseCursorSource implements CursorSource {
    private final JComponent target;
    private final AtomicReference<Point2D.Double> position = new AtomicReference<>(new Point2D.Double(0, 0));
    private final AWTEventListener mouseMotionListener;

    public MouseCursorSource(JComponent target) {
        this.target = target;
        mouseMotionListener = event -> updatePosition(event, target, position);
        Toolkit.getDefaultToolkit().addAWTEventListener(mouseMotionListener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    private static void updatePosition(AWTEvent event, JComponent target,
            AtomicReference<Point2D.Double> position) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            Point2D.Double screenPoint = new Point2D.Double(me.getLocationOnScreen().getX(), me.getLocationOnScreen().getY());
            Point2D.Double relative = new Point2D.Double(screenPoint.x, screenPoint.y);
            //SwingUtilities.convertPointFromScreen(relative, target);
            position.set(relative);
        }
    }

    @Override
    public Point2D.Double getPosition() {
        return position.get();
    }

    // Call this when the game screen is torn down, or the listener keeps
    // running in the background even after playingscreen is gone.
    public void dispose() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(mouseMotionListener);
    }
}