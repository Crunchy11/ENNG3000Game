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
        double d1 = positionListener.getLatestD1();
        double d2 = positionListener.getLatestD2();

        final double SENSOR_RANGE = 450.0;   // sensor reading that maps to the far edge

        // horizontal is flipped, same as before
        double x = GameConfig.areaWidth - (d1 / SENSOR_RANGE) * GameConfig.areaWidth;
        double y = (d2 / SENSOR_RANGE) * GameConfig.areaHeight;

        // keep the hammer inside the play area if a reading goes past the range
        x = Math.max(0, Math.min(GameConfig.areaWidth, x));
        y = Math.max(0, Math.min(GameConfig.areaHeight, y));

        return new Point2D.Double(x, y);
    }
}
