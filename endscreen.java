import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class endscreen extends JPanel {

    public endscreen(JFrame frame, int score) {
        setLayout(null);
        String font = "SansSerif";

        JLabel title = new JLabel("Time's Up!");
        title.setFont(new Font(font, Font.BOLD, 48));
        title.setBounds(435, 120, 600, 80);

        JLabel scoreLabel = new JLabel("Final Score: " + score);
        scoreLabel.setFont(new Font(font, Font.PLAIN, 32));
        scoreLabel.setBounds(400, 230, 600, 60);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font(font, Font.BOLD, 24));
        playAgainButton.setBounds(500, 400, 200, 60);
        playAgainButton.addActionListener(e -> {
            // reuses whatever difficulty was last selected on startscreen
            frame.setContentPane(new playingscreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        JButton mainMenuButton = new JButton("Back to Start");
        mainMenuButton.setFont(new Font(font, Font.BOLD, 24));
        mainMenuButton.setBounds(500, 480, 200, 60);
        mainMenuButton.addActionListener(e -> {
            frame.setContentPane(new startscreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        add(title);
        add(scoreLabel);
        add(playAgainButton);
        add(mainMenuButton);
    }
}