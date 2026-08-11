import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;

public class moles extends JButton {
    private boolean isUp = false;
    private final Runnable onWhack;
    private Timer autoHideTimer;

    public moles(Runnable onWhack) {
        this.onWhack = onWhack;

        setFont(new Font("SansSerif", Font.BOLD, 20));
        setText("");                 // no text while hidden
        setBackground(Color.LIGHT_GRAY);
        setFocusPainted(false);
        setVisible(false);           // hidden until popped up

        addActionListener(e -> whack());
    }

    //Shows this mole and schedules it to auto-hide after upDurationMs if not whacked. 
    public void popUp(int upDurationMs) {
        isUp = true;
        setText("\uD83D\uDC39");     // simple mole emoji, swap for an icon/image later if you like
        setVisible(true);

        if (autoHideTimer != null) {
            autoHideTimer.stop();
        }
        autoHideTimer = new Timer(upDurationMs, e -> hidemole());
        autoHideTimer.setRepeats(false);
        autoHideTimer.start();
    }

    public void hidemole() {
        isUp = false;
        setText("");
        setVisible(false);
        if (autoHideTimer != null) {
            autoHideTimer.stop();
        }
    }

    public boolean isUp() {
        return isUp;
    }

    private void whack() {
        if (isUp) {
            hidemole();
            onWhack.run(); // notify playingscreen a mole was successfully whacked
        }
    }
}

