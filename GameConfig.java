public class GameConfig {

    private static final double BASE_WIDTH = 1286;
    private static final double BASE_HEIGHT = 800;
    
    public static final int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width; // Screen width of the game window
    public static final int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height; // Screen height of the game window

    private static final double factor = Math.min(screenWidth / BASE_WIDTH, screenHeight / BASE_HEIGHT);

    public static int scale(int px) { return (int) Math.round(px * factor); }
    public static float scale(float px) { return (float) (px * factor); }

    public static final int gridRows = 4;
    public static final int gridCols = 4;

    public static final int areaWidth = scale(850); // Width of the play area
    public static final int areaHeight = scale(600); // Height of the play area

    public static final int moleSize = scale(170);
    public static final int cursorSize = scale(75);    // hammer cursor size in pixels
    public static final int setBoundDiffX = 550; // sets the x bound for the difficulty buttons on the start screen

    public static final int areaX = (screenWidth - areaWidth) / 2;
    public static final int areaYOffset = scale(75);   // how far to push the grid down
    public static final int areaY = (screenHeight - areaHeight) / 2 + areaYOffset;
}
