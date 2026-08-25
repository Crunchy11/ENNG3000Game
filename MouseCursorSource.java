import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class MouseCursorSource implements CursorSource {
    private final JComponent target;
    private final AtomicReference<Point> position = new AtomicReference<>(new Point(0, 0));
    private final AWTEventListener mouseMotionListener;

    public MouseCursorSource(JComponent target) {
        this.target = target;
        mouseMotionListener = event -> updatePosition(event, target, position);
        Toolkit.getDefaultToolkit().addAWTEventListener(mouseMotionListener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    private static void updatePosition(AWTEvent event, JComponent target,
            AtomicReference<Point> position) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            Point screenPoint = me.getLocationOnScreen();
            Point relative = new Point(screenPoint);
            SwingUtilities.convertPointFromScreen(relative, target);
            position.set(relative);
        }
    }

    @Override
    public Point getPosition() {
        return position.get();
    }

    // Call this when the game screen is torn down, or the listener keeps
    // running in the background even after playingscreen is gone.
    public void dispose() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(mouseMotionListener);
    }
}