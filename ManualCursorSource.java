import java.awt.Point;
import javax.swing.JComponent;


public class ManualCursorSource implements CursorSource {
    private final JComponent target;
    PositionListener positionListener;


    public ManualCursorSource(JComponent target, PositionListener listener) {
        this.target = target;
        this.positionListener = listener;
    }


@Override
public Point getPosition() {
    return new Point((int) positionListener.latestD1, (int) positionListener.latestD2);
}
}


