import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

public class startscreen extends JPanel {
    public startscreen(JFrame frame) {
        setLayout(null);
        String font = "SansSerif";

        JLabel title = new JLabel("Wack-a-Mole");
        title.setFont(new Font(font, Font.BOLD, 48));
        title.setBounds(435, 150, 600, 80);

        JLabel diff = new JLabel("Please select a difficulty level:");
        diff.setFont(new Font(font, Font.ITALIC, 30));
        diff.setBounds(385, 250, 600, 80);

        JButton easyButton = new JButton("EASY");
        easyButton.setFont(new Font(font, Font.BOLD, 24));
        easyButton.setBounds(500, 400, 200, 60);
        easyButton.addActionListener(e -> {
            frame.setContentPane(new playingscreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        JButton mediumButton = new JButton("MEDIUM");
        mediumButton.setFont(new Font(font, Font.BOLD, 24));
        mediumButton.setBounds(500, 480, 200, 60);
        mediumButton.addActionListener(e -> {
            frame.setContentPane(new playingscreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        JButton hardButton = new JButton("HARD");
        hardButton.setFont(new Font(font, Font.BOLD, 24));
        hardButton.setBounds(500, 560, 200, 60);
        hardButton.addActionListener(e -> {
            frame.setContentPane(new playingscreen(frame));
            frame.revalidate();
            frame.repaint();
        });

        add(title);
        add(diff);
        add(easyButton);
        add(mediumButton);
        add(hardButton);
    }
}
