
import java.awt.Point;

public class CursorManager implements CursorSource {
    private CursorSource activeSource;

    public CursorManager(CursorSource initialSource) {
        this.activeSource = initialSource;
    }

    public void setSource(CursorSource newSource) {
        this.activeSource = newSource;
    }

    @Override
    public Point getPosition() {
        return activeSource.getPosition();
    }
}