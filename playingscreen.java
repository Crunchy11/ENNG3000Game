import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class playingscreen extends JPanel {
    
    public playingscreen(JFrame frame) {
        setLayout(null);
        int difficulty = startscreen.difficulty;
        System.out.println("Difficulty level selected: " + difficulty);
        JLabel score = new JLabel("Score: 0");
        score.setBounds(520, 10, 200, 30);
        score.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel time = new JLabel("Time: 00:00");
        time.setBounds(10, 10, 200, 30);
        time.setFont(new Font("SansSerif", Font.BOLD, 28));

        JPanel playArea = new JPanel();
        playArea.setBounds(150, 100, 900, 550);
        playArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton backButton = new JButton("Back to Start");
        backButton.setBounds(10, 60, 160, 30);
        backButton.addActionListener(e -> {
            frame.setContentPane(new startscreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        add(score);
        add(time);
        add(playArea);
        add(backButton);
    }


}
