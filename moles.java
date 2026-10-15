import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

public class moles extends JButton {

    private static final int DIG_FRAMES = 12;  
    private static final int IDLE_FRAMES = 8;       // frame count in idleSheet.png
    private static final int IDLE_FRAME_MS = 150;   // time per frame, lower is faster

    private Timer idleTimer;

    private final ImageIcon moleIcon = loadIcon("images/mole.png");
    private final ImageIcon hiddenIcon = loadIcon("images/hiddenMole.png");
    private final ImageIcon[] digFrames = loadSheet("images/digSheet.png", DIG_FRAMES);
    private final ImageIcon[] idleFrames = loadSheet("images/moleSheet.png", IDLE_FRAMES);

    private boolean isUp = false;
    private boolean isDigging = false;
    private final Runnable onWhack;
    private Timer autoHideTimer;
    private Timer digTimer;

    public moles(Runnable onWhack) {
        this.onWhack = onWhack;

        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);

        //setIcon(hiddenIcon);

        addActionListener(e -> whack());
    }

private void startIdle() {
    if (idleTimer != null) idleTimer.stop();

    int[] frame = {0};
    setIcon(idleFrames[0]);

    idleTimer = new Timer(IDLE_FRAME_MS, e -> {
        frame[0] = (frame[0] + 1) % IDLE_FRAMES;   // wraps back to 0 to loop
        setIcon(idleFrames[frame[0]]);
    });
    idleTimer.start();
}

    private static ImageIcon loadIcon(String path) {
        URL url = moles.class.getResource(path);
        if (url == null) {
            System.err.println("Image not found: " + path);
            return new ImageIcon();
        }
        Image scaled = new ImageIcon(url).getImage()
                .getScaledInstance(GameConfig.moleSize, GameConfig.moleSize, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // Cuts a horizontal sprite sheet into equal frames and scales each one.
    private static ImageIcon[] loadSheet(String path, int count) {
        ImageIcon[] frames = new ImageIcon[count];
        for (int i = 0; i < count; i++) frames[i] = new ImageIcon();

        URL url = moles.class.getResource(path);
        if (url == null) {
            System.err.println("Image not found: " + path);
            return frames;
        }
        try {
            BufferedImage sheet = ImageIO.read(url);
            if (sheet == null) {
                System.err.println("Could not decode image: " + path);
                return frames;
            }
            int frameW = sheet.getWidth() / count;
            int frameH = sheet.getHeight();
            for (int i = 0; i < count; i++) {
                BufferedImage sub = sheet.getSubimage(i * frameW, 0, frameW, frameH);
                frames[i] = new ImageIcon(sub.getScaledInstance(
                        GameConfig.moleSize, GameConfig.moleSize, Image.SCALE_SMOOTH));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frames;
    }

    // Plays the dig frames over digDurationMs, then pops up.
    public void startDig(int digDurationMs, int upDurationMs) {
        if (isUp || isDigging) return;
        isDigging = true;

        int frameDelay = Math.max(1, digDurationMs / DIG_FRAMES);
        int[] frame = {0};
        setIcon(digFrames[0]);

        digTimer = new Timer(frameDelay, e -> {
            frame[0]++;
            if (frame[0] >= DIG_FRAMES) {
                digTimer.stop();
                isDigging = false;
                popUp(upDurationMs);
            } else {
                setIcon(digFrames[frame[0]]);
            }
        });
        digTimer.start();
    }

    // Shows the mole and hides it after upDurationMs if not whacked.
    public void popUp(int upDurationMs) {
        isUp = true;
        startIdle();

        if (autoHideTimer != null) {
            autoHideTimer.stop();
        }
        autoHideTimer = new Timer(upDurationMs, e -> hidemole());
        autoHideTimer.setRepeats(false);
        autoHideTimer.start();
    }

    public void hidemole() {
        isUp = false;
        isDigging = false;
        setIcon(hiddenIcon);
        if (autoHideTimer != null) autoHideTimer.stop();
        if (digTimer != null) digTimer.stop();
        if (idleTimer != null) idleTimer.stop();

    }

    public boolean isUp() {
        return isUp;
    }

    // True only when the mole is fully down and not digging.
    public boolean isHidden() {
        return !isUp && !isDigging;
    }

    private void whack() {
        if (isUp) {
            hidemole();
            onWhack.run();
        }
    }
}