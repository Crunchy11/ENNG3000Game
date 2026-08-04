import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Create the JFrame (The Window)
            JFrame frame = new JFrame("My Application Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app on 'X' click
            int width = 1200;
            int height = 800;
            frame.setSize(width, height); 
            
            // 2. Create the JPanel 
            JPanel panel = new JPanel();
            panel.setLayout(null); // disable layout manager allows for custom control of placement
            
            // 3. Create UI Components
            //score
            JLabel score = new JLabel("Score:");
            score.setBounds(width/2-50, 10, 100, 30); // x, y, width, height
            score.setFont(new Font("SansSerif", Font.BOLD, 30));

           //time
            JLabel time = new JLabel("Time:");
            time.setBounds(10, 10, 100, 30); // x, y, width, height
            time.setFont(new Font("SansSerif", Font.BOLD, 28));    
            //JButton button = new JButton("Click Me"); example of hoe to implement a button
            
            //Play Area
            JPanel playArea = new JPanel();
            playArea.setBounds(150, 100, 900, 550);   // x, y, width, height
            playArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            // 4. Add UI Components to the JPanel
            panel.add(score);
            panel.add(time);
            panel.add(playArea);
            //panel.add(button);
            
            // 5. Add the JPanel to the JFrame's Content Pane
            frame.add(panel);
            
            // 6. Make the Window Visible
            frame.setVisible(true);
        });
    }
}


