import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

public class moles extends JButton {

    private final ImageIcon moleIcon = loadIcon("images/mole.png");
    private final ImageIcon hiddenIcon = loadIcon("images/hiddenMole.png");

    private boolean isUp = false;
    private final Runnable onWhack;
    private Timer autoHideTimer;

    public moles(Runnable onWhack) {
        this.onWhack = onWhack;

        // setFont(new Font("SansSerif", Font.BOLD, 20));
        // setText("");                 // no text while hidden
        //setBackground(java.awt.Color.RED);
        // setFocusPainted(false);
        // setVisible(false);           // hidden until popped up

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(true);
        setOpaque(false);

        setIcon(hiddenIcon);

        addActionListener(e -> whack());
    }

private static ImageIcon loadIcon(String path) {
    java.net.URL url = moles.class.getResource(path);
    if (url == null) {
        System.err.println("Image not found: " + path);
        return new ImageIcon();
    }
    Image scaled = new ImageIcon(url).getImage()
            .getScaledInstance(GameConfig.moleSize, GameConfig.moleSize, Image.SCALE_SMOOTH);
    return new ImageIcon(scaled);
}

    //Shows this mole and schedules it to auto-hide after upDurationMs if not whacked. 
    public void popUp(int upDurationMs) {
        isUp = true;
        setIcon(moleIcon);
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
        setIcon(hiddenIcon);
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

