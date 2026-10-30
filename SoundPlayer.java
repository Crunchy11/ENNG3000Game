import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    public static void play(String path) {
        try {
            URL url = SoundPlayer.class.getResource(path);
            if (url == null) {
                System.err.println("Sound not found: " + path);
                return;
            }
            AudioInputStream in = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();   // free the clip once it finishes
                }
            });
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}