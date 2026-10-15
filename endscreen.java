import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class endscreen extends JPanel {
    public endscreen(JFrame frame, PositionListener esp32Listener, int score){
        setLayout(null);
        String font = "SansSerif";

        JLabel title = new JLabel("Time's Up!");
        //title.setFont(new Font(font, Font.BOLD, 48));
        title.setFont(GameFonts.get(48f));
        title.setBounds(435, 120, 600, 80);

        JLabel scoreLabel = new JLabel("Final Score: " + score);
        //scoreLabel.setFont(new Font(font, Font.PLAIN, 32));
        scoreLabel.setFont(GameFonts.get(32f));
        scoreLabel.setBounds(400, 230, 600, 60);

        JButton playAgainButton = new JButton("Play Again");
        //playAgainButton.setFont(new Font(font, Font.BOLD, 24));
        playAgainButton.setFont(GameFonts.get(24f));
        playAgainButton.setBounds(500, 400, 300, 60);
        playAgainButton.addActionListener(e -> {
            // reuses whatever difficulty was last selected on startscreen
            frame.setContentPane(new playingscreen(frame, esp32Listener));
            frame.revalidate();
            frame.repaint();
        });

        JButton mainMenuButton = new JButton("Back to Start");
        mainMenuButton.setFont(GameFonts.get(24f));
        //mainMenuButton.setFont(new Font(font, Font.BOLD, 24));
        mainMenuButton.setBounds(500, 480, 400, 60);
        mainMenuButton.addActionListener(e -> {
            frame.setContentPane(new startscreen(frame , esp32Listener));
            frame.revalidate();
            frame.repaint();
        });

        add(title);
        add(scoreLabel);
        add(playAgainButton);
        add(mainMenuButton);
    }
}

