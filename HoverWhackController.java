import java.awt.Component;
import java.awt.Container;
import java.awt.geom.Point2D;
import java.util.Objects;
import javax.swing.AbstractButton;
import javax.swing.Timer;

public class HoverWhackController {
    private final Container moleContainer;
    private final CursorSource source;
    private final Timer pollTimer;
    private Component lastHit;

    public HoverWhackController(Container moleContainer, CursorSource source) {
        this.moleContainer = Objects.requireNonNull(moleContainer);
        this.source = Objects.requireNonNull(source);
        this.pollTimer = new Timer(16, e -> checkHover());
    }

    public void start() { pollTimer.start(); }
    public void stop() { pollTimer.stop(); }

    private void checkHover() {
        Point2D.Double p = source.getPosition();
        if (p == null) {
            lastHit = null;
            return;
        }

        Component hit = findMoleAt(p);

        if (hit != null && hit != lastHit) {
            ((AbstractButton) hit).doClick();
        }

        lastHit = hit;
    }

    // Manually checks mole bounds instead of getComponentAt(), so the
    // cursor label (which sits visually on top) can never intercept the hit test.
    private Component findMoleAt(Point2D.Double p) {
        for (Component c : moleContainer.getComponents()) {
            if (c instanceof AbstractButton && c.isVisible() && c.getBounds().contains(p)) {
                return c;
            }
        }
        return null;
    }
}