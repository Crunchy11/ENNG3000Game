import java.awt.Font;
import java.io.InputStream;

public class GameFonts {
    private static Font base;

    static {
        try (InputStream in = GameFonts.class.getResourceAsStream("/fonts/NewHiScoreFont.ttf")) {
            if (in == null) {
                System.err.println("Font not found: /fonts/NewHiScoreFont.ttf");
            } else {
                base = Font.createFont(Font.TRUETYPE_FONT, in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Font get(float size) {
        if (base == null) {
            return new Font("SansSerif", Font.BOLD, (int) size); // fallback
        }
        return base.deriveFont(size);
    }
}