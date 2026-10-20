import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class startscreen extends JPanel {
    public static int difficulty = 0;

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 60;

    private final JLabel title;
    private final JLabel diff;
    private final JButton easyButton;
    private final JButton mediumButton;
    private final JButton hardButton;

    public startscreen(JFrame frame, PositionListener esp32Listener) {
        setLayout(null);

        //text - centred horizontally inside its own bounds
        title = new JLabel("Wack-a-Mole", SwingConstants.CENTER);
        title.setFont(GameFonts.get(48f));

        diff = new JLabel("Please select a difficulty level:", SwingConstants.CENTER);
        diff.setFont(GameFonts.get(30f));

        //Difficulty buttons
        easyButton = new JButton("EASY");
        easyButton.setFont(GameFonts.get(24f));
        easyButton.addActionListener(e -> {
            difficulty = 1;
            frame.setContentPane(new playingscreen(frame, esp32Listener));
            frame.revalidate();
            frame.repaint();
        });

        mediumButton = new JButton("MEDIUM");
        mediumButton.setFont(GameFonts.get(24f));
        mediumButton.addActionListener(e -> {
            difficulty = 2;
            frame.setContentPane(new playingscreen(frame, esp32Listener));
            frame.revalidate();
            frame.repaint();
        });

        hardButton = new JButton("HARD");
        hardButton.setFont(GameFonts.get(24f));
        hardButton.addActionListener(e -> {
            difficulty = 3;
            frame.setContentPane(new playingscreen(frame, esp32Listener));
            frame.revalidate();
            frame.repaint();
        });

        add(title);
        add(diff);
        add(easyButton);
        add(mediumButton);
        add(hardButton);
    }

    // Called by Swing whenever this screen is shown or resized, so everything
    // is positioned from the real width of the window instead of fixed numbers.
    @Override
    public void doLayout() {
        int w = getWidth();
        int buttonX = (w - BUTTON_WIDTH) / 2;

        title.setBounds(0, 150, w, 80);   // full width + CENTER = centred text
        diff.setBounds(0, 250, w, 80);

        easyButton.setBounds(buttonX, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
        mediumButton.setBounds(buttonX, 480, BUTTON_WIDTH, BUTTON_HEIGHT);
        hardButton.setBounds(buttonX, 560, BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}