import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        PositionListener ESP32Listener = new PositionListener();
        ESP32Listener.start(); 

        SwingUtilities.invokeLater(() -> {
            // JFrame frame = new JFrame("Wack-a-Mole");
            // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // frame.getContentPane().setPreferredSize(new java.awt.Dimension(GameConfig.screenWidth, GameConfig.screenHeight));
            // // frame.pack();
            // // frame.setLocationRelativeTo(null);
            // frame.setContentPane(new startscreen(frame, ESP32Listener));
            // frame.setVisible(true);
            // frame.setUndecorated(true);
            // frame.setBounds(0, 0, GameConfig.screenWidth, GameConfig.screenHeight);
            JFrame frame = new JFrame("Wack-a-Mole");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);   // must come before the frame is shown or packed
            frame.setBounds(0, 0, GameConfig.screenWidth, GameConfig.screenHeight);
            frame.setContentPane(new startscreen(frame, ESP32Listener));

            // no title bar means no close button, so Esc quits
            frame.getRootPane()
                .getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(javax.swing.KeyStroke.getKeyStroke("ESCAPE"), "quit");
            frame.getRootPane().getActionMap().put("quit", new javax.swing.AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            });
            frame.setVisible(true);
        });
                
    }
}


