import javax.swing.JButton;

public class moles extends JButton {
    private boolean isUp = false;

    public moles() {
        setBounds(0, 0, 80, 80); // playingscreen will position it
        setVisible(false); // hidden until popped up
        addActionListener(e -> whack());
    }

    public void popUp() {
        isUp = true;
        setVisible(true);
    }

    public void hide() {
        isUp = false;
        setVisible(false);
    }

    public boolean isUp() {
        return isUp;
    }

    private void whack() {
        if (isUp) {
            hide();
            // notify score somehow — see below
        }
    }
}

