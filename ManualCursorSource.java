import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.w3c.dom.events.MouseEvent;

public class ManualCursorSource implements CursorSource {
    private final JComponent target;
    private final AtomicReference<Point> position = new AtomicReference<>(new Point(0, 0));
    PositionListener positionListener;


    public ManualCursorSource(JComponent target, PositionListener listener) {
        this.target = target;
        this.positionListener = listener;
    }

    private void updatePosition() {
        position.set(new Point((int) positionListener.latestD1, (int) positionListener.latestD2));
    }

     @Override
    public Point getPosition() { return position.get(); }
}


