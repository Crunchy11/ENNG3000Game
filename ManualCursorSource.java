import java.awt.geom.Point2D;

public class ManualCursorSource implements CursorSource {
    private PositionListener positionListener;

    public ManualCursorSource(PositionListener positionListener) {
        this.positionListener = positionListener;
    }

    public void setPositionListener(PositionListener listener) {
        this.positionListener = listener;
    }

    @Override
    public Point2D.Double getPosition() {
        Double d1 = positionListener.getLatestD1();
        Double d2 = positionListener.getLatestD2();
  
        return new Point2D.Double(d1, d2);
    }
}
