package gdd;

public class Global {
    private Global() {
        // Prevent instantiation
    }

    public static final int SCALE_FACTOR = 2; // Scaling factor for sprites

    public static final double SCALE_DOWN_FACTOR = 0.5; // Scaling down factor for images

    public static final int BOARD_WIDTH = 716; // Doubled from 358
    public static final int BOARD_HEIGHT = 700; // Doubled from 350
    public static final int BORDER_RIGHT = 60; // Doubled from 30
    public static final int BORDER_LEFT = 10; // Doubled from 5

    public static final int GROUND = 580; // Doubled from 290
    public static final int BOMB_HEIGHT = 10; // Doubled from 5

    public static final int ALIEN_HEIGHT = 24; // Doubled from 12
    public static final int ALIEN_WIDTH = 24; // Doubled from 12
    public static final int ALIEN_INIT_X = 300; // Doubled from 150
    public static final int ALIEN_INIT_Y = 10; // Doubled from 5
    public static final int ALIEN_GAP = 30; // Gap between aliens

    public static final int GO_DOWN = 30; // Doubled from 15
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 8; // Original 20 scale 1.2
    public static final int PLAYER_HEIGHT = 35; // Original 38 scale 1.2

    public static int destoryedEnemies = 0; // Counter for destroyed enemies

    // Images
    public static final String IMG_ENEMY = "src/images/NVTurboEnemy.png";
    public static final String IMG_PLAYER = "src/images/NVTurboPlayerV2.png";
    public static final String IMG_SHOT = "src/images/NV_Shot.png";
    public static final String IMG_EXPLOSION = "src/images/explosion.png";
    public static final String IMG_TITLE = "src/images/title.png";

    public static final String IMG_POWERUP_SPEEDUP = "src/images/powerup_lightning.png";
    public static final String IMG_POWERUP_HEALTHUP = "src/images/powerup_health.png";
}
