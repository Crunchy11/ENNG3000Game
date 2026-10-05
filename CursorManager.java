
import java.awt.geom.Point2D;

public class CursorManager implements CursorSource {
    private CursorSource activeSource;

    public CursorManager(CursorSource initialSource) {
        this.activeSource = initialSource;
    }

    public void setSource(CursorSource newSource) {
        this.activeSource = newSource;
    }

    @Override
    public Point2D.Double getPosition() {
        return activeSource.getPosition();
    }
}