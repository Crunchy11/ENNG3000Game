import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class SoundPlayer {

    // Plays the sound once, silently, so the audio system is ready before the first real hit.
    public static void warmUp(String path) {
        play(path, true);
    }

    public static void play(String path) {
        play(path, false);
    }

    private static void play(String path, boolean silent) {
        try {
            URL url = SoundPlayer.class.getResource(path);
            if (url == null) {
                System.err.println("Sound not found: " + path);
                return;
            }
            AudioInputStream in = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(in);

            if (silent) {
                // mute so the warm up isn't heard
                javax.sound.sampled.FloatControl gain = (javax.sound.sampled.FloatControl)
                        clip.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                gain.setValue(gain.getMinimum());
            }

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();   // free the clip once it finishes
                }
            });
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}