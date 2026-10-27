import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class endscreen extends JPanel {
    public endscreen(JFrame frame, PositionListener esp32Listener, int score){
        setLayout(null);
        int w = GameConfig.screenWidth;

        JLabel title = new JLabel("Time's Up!", SwingConstants.CENTER);
        title.setFont(GameFonts.get(GameConfig.scale(48f)));
        title.setBounds(0, GameConfig.scale(120), w, GameConfig.scale(80));

        JLabel scoreLabel = new JLabel("Final Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(GameFonts.get(GameConfig.scale(32f)));
        scoreLabel.setBounds(0, GameConfig.scale(230), w, GameConfig.scale(60));

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(GameFonts.get(GameConfig.scale(24f)));
        playAgainButton.setBounds((w - GameConfig.scale(300)) / 2, GameConfig.scale(400),
                                GameConfig.scale(300), GameConfig.scale(60));
        
        playAgainButton.addActionListener(e -> {
            // reuses whatever difficulty was last selected on startscreen
            frame.setContentPane(new playingscreen(frame, esp32Listener));
            frame.revalidate();
            frame.repaint();
        });

        JButton mainMenuButton = new JButton("Back to Start");
        mainMenuButton.setFont(GameFonts.get(GameConfig.scale(24f)));
        mainMenuButton.setBounds((w - GameConfig.scale(400)) / 2, GameConfig.scale(480),
                                GameConfig.scale(400), GameConfig.scale(60));
        
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

