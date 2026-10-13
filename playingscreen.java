import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class playingscreen extends JPanel {

    private BufferedImage background;

    private static final int GRID_ROWS = 4;
    private static final int GRID_COLS = 6;

    private final JLabel scoreLabel;
    private final JLabel timeLabel;
    private final moles[] moles = new moles[GRID_ROWS * GRID_COLS];
    private final Random random = new Random();

    private int score = 0;
    private int secondsRemaining;
    private Timer spawnTimer;
    private Timer countdownTimer;
    private Timer cursorTimer;

    private final int spawnIntervalMs;
    private final int upDurationMs;

    private CursorManager cursorManager;
    private CursorDisplay cursorLabel;
    private HoverWhackController hoverWhackController;
    private MouseCursorSource mouseSource;
    private ManualCursorSource manualSource;

    private final PositionListener esp32Listener;   // new field

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public playingscreen(JFrame frame, PositionListener esp32Listener) {
        setLayout(null);
        try {
            java.net.URL url = getClass().getResource("/images/background.png");

            if (url == null) {
                System.err.println("Image not found: /images/background.png");
            } 
            
            else {
                background = ImageIO.read(url);
            }
        } 
        
        catch (IOException e) {

            e.printStackTrace();
        }
        this.esp32Listener = esp32Listener;          // store it

        int difficulty = startscreen.difficulty;

        switch (difficulty) {
            case 1 -> { spawnIntervalMs = 500; upDurationMs = 4000; secondsRemaining = 120; } //easy
            case 3 -> { spawnIntervalMs = 800;  upDurationMs = 1500;  secondsRemaining = 45; } //hard
            default -> { spawnIntervalMs = 650; upDurationMs = 2000; secondsRemaining = 60; } //medium
        }

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(520, 10, 200, 30);
        //scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        scoreLabel.setFont(GameFonts.get(30f));

        timeLabel = new JLabel("Time: " + secondsRemaining);
        timeLabel.setBounds(10, 10, 200, 30);
        //timeLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        timeLabel.setFont(GameFonts.get(28f));

        JLayeredPane playArea = new JLayeredPane();
        // originally x  - 150, y - 100, width - 900, height - 550
        playArea.setBounds(143, 203, GameConfig.areaWidth, GameConfig.areaHeight);
        playArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton backButton = new JButton("Back to Start");
        backButton.setBounds(10, 60, 160, 30);
        backButton.addActionListener(e -> {
            stopTimers();
            frame.setContentPane(new startscreen(frame, esp32Listener));  // pass it along
            frame.revalidate();
            frame.repaint();
        });

        buildMoleGrid(playArea);

        manualSource = new ManualCursorSource(esp32Listener);
        mouseSource = new MouseCursorSource(playArea);
        cursorManager = new CursorManager(manualSource);
        
        cursorLabel = new CursorDisplay(cursorManager);
        playArea.add(cursorLabel, JLayeredPane.PALETTE_LAYER);

        hoverWhackController = new HoverWhackController(playArea, cursorManager);
        hoverWhackController.start();

        add(scoreLabel);
        add(timeLabel);
        add(playArea);
        add(backButton);

        startSpawning();
        startCountdown(frame);
        updateCursorPosition();
    }

    /** Creates the grid of Mole buttons and positions them evenly inside playArea. */
    private void buildMoleGrid(JLayeredPane playArea) {
        // originally area width - 900, area height - 550
        int areaWidth = GameConfig.areaWidth;
        int areaHeight = GameConfig.areaHeight;
        int cellWidth = areaWidth / GRID_COLS;
        int cellHeight = areaHeight / GRID_ROWS;
        int moleSize = GameConfig.moleSize;  // use the constant from GameConfig

        int index = 0;
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                moles mole = new moles(this::onMoleWhacked);

                int x = col * cellWidth + (cellWidth - moleSize) / 2;
                int y = row * cellHeight + (cellHeight - moleSize) / 2;
                mole.setBounds(x, y, moleSize, moleSize);

                moles[index++] = mole;
                playArea.add(mole);
            }
        }
    }

    /** Called by a Mole when it gets successfully whacked. */
    private void onMoleWhacked() {
        score++;
        scoreLabel.setText("Score: " + score);
    }

    /** Main spawn loop: on each tick, pop up a random mole that isn't already up. */
    private void startSpawning() {
        spawnTimer = new Timer(spawnIntervalMs, e -> {
            moles candidate = pickRandomHiddenMole();
            if (candidate != null) {
                candidate.popUp(upDurationMs);
            }
        });
        spawnTimer.start();
    }

    private void updateCursorPosition() {
        // Get the position from the ESP32 listener
        cursorTimer = new Timer(16, e -> cursorLabel.updatePosition());
        cursorTimer.start();
    }

    private moles pickRandomHiddenMole() {
        // simple approach: try a handful of random picks before giving up this tick
        for (int attempt = 0; attempt < moles.length; attempt++) {
            moles candidate = moles[random.nextInt(moles.length)];
            if (!candidate.isUp()) {
                return candidate;
            }
        }
        return null; // all moles happened to be up already this tick
    }

    /** Counts down the game clock and ends the game at zero. */
  private void startCountdown(JFrame frame) {
        countdownTimer = new Timer(1000, e -> {
            secondsRemaining--;
            timeLabel.setText("Time: " + secondsRemaining);

            if (secondsRemaining <= 0) {
                stopTimers();
                for (moles mole : moles) {
                    mole.hidemole();
                }
                frame.setContentPane(new endscreen(frame, esp32Listener, score));  // pass it along
                frame.revalidate();
                frame.repaint();
            }
        });
        countdownTimer.start();
    }

    private void stopTimers() {
        if (spawnTimer != null) spawnTimer.stop();
        if (countdownTimer != null) countdownTimer.stop();
        if (hoverWhackController != null) hoverWhackController.stop();
        if (cursorLabel != null) cursorLabel.stopUpdating();
        if (mouseSource != null) mouseSource.dispose();
    }
}

