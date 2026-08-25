import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        PositionListener ESP32Listener = new PositionListener();
        ESP32Listener.start(); 

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Wack-a-Mole");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new startscreen(frame, ESP32Listener));
            frame.setVisible(true);
        });
    }
}


