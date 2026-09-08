import java.awt.Point;
import javax.swing.JComponent;

public class ManualCursorSource implements CursorSource {
    private final JComponent target;
    private final PositionListener positionListener;

    // --- Calibration constants: measure your real setup and adjust these ---
    private static final double SENSOR_BASELINE_CM = 200.0; // distance between the two sensors
    private static final double ROOM_DEPTH_CM = 150.0;      // usable depth in front of the sensors

    // --- Smoothing ---
    private static final double ALPHA = 0.3; // lower = smoother but laggier
    private double smoothedX = SENSOR_BASELINE_CM / 2.0; // start centered
    private double smoothedY = 0;

    public ManualCursorSource(JComponent target, PositionListener listener) {
        this.target = target;
        this.positionListener = listener;
    }

    @Override
    public Point getPosition() {
        double d1 = positionListener.getLatestD1();
        double d2 = positionListener.getLatestD2();
        double W = SENSOR_BASELINE_CM;

        // Trilateration: solve for (x, y) consistent with both distance readings
        double x = (d1 * d1 - d2 * d2 + W * W) / (2 * W);
        double ySq = d1 * d1 - x * x;
        double y = ySq > 0 ? Math.sqrt(ySq) : 0;

        // Reject physically impossible readings (sensor noise/echo glitches)
        if (x < -20 || x > W + 20) {
            x = smoothedX; // fall back to last good value instead of jumping
        }

        // Smooth to reduce jitter
        smoothedX = ALPHA * x + (1 - ALPHA) * smoothedX;
        smoothedY = ALPHA * y + (1 - ALPHA) * smoothedY;

        // Map real-world cm to on-screen pixels within the target component
        int screenX = (int) (smoothedX / W * target.getWidth());
        int screenY = (int) (smoothedY / ROOM_DEPTH_CM * target.getHeight());

        return new Point(screenX, screenY);
    }
}
