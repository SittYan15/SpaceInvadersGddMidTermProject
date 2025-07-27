package gdd;

public class Global {

    private Global() {
        // Prevent instantiation
    }
    public static boolean spacePressed = false;

    // Need to set default values for the game
    public static boolean isScene1Win = false;
    public static boolean isScene2Win = false;
    public static int shotDamage = 1;
    public static int gunCount = 1;
    public static long shotCooldownMillis = 700;
    public static int playerSpeed = 1;
    public static int playerHealth = 10;
    public static double playerPower = 0;
    public static int kill = 0;
    public static int distance = 0;

    public static double rocketSpeed = 3.0;

    public static long lastShotTime = 0;

    public static final int SCALE_FACTOR = 2; // Scaling factor for sprites
    public static final double BOSS_SCALE_FACTOR = 0.7;
    public static final double SCALE_DOWN_FACTOR = 0.4; // Scaling down factor for images

    public static final int BOARD_WIDTH = 716; // Doubled from 358
    public static final int BOARD_HEIGHT = 700; // Doubled from 350
    public static final int BORDER_RIGHT = 60; // Doubled from 30
    public static final int BORDER_LEFT = 10; // Doubled from 5

    public static final int GROUND = BOARD_HEIGHT; // Doubled from 290
    public static final int BOMB_HEIGHT = 10; // Doubled from 5

    public static final int ALIEN_HEIGHT = 25; // Doubled from 12
    public static final int ALIEN_WIDTH = 25; // Doubled from 12
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

    public static final String IMG_BOSS1 = "src/Foozle_Enemy_1/Kla'ed/Base/PNGs/Kla'ed - Battlecruiser - Base.png";
    public static final String IMG_BOSS1_ENGINES = "src/Foozle_Enemy_1/Kla'ed/Engine/PNGs/Kla'ed - Battlecruiser - Engine.png";
    public static final String IMG_BOSS1_SHIELDS = "src/Foozle_Enemy_1/Kla'ed/Shield/PNGs/Kla'ed - Battlecruiser - Shield.png";
    public static final String IMG_BOSS1_DESTRUCTION = "src/Foozle_Enemy_1/Kla'ed/Destruction/PNGs/Kla'ed - Battlecruiser - Destruction.png";
    public static final String IMG_BOSS1_WEAPON = "src/Foozle_Enemy_1/Kla'ed/Weapons/PNGs/Kla'ed - Battlecruiser - Weapons.png";
    public static final String IMG_BOSS1_POWERMODE = "src/Foozle_Enemy_1/Kla'ed/Weapons/PNGs/Kla'ed - Battlecruiser - Weapons.png";

    public static final String IMG_ROCKET1 = "src/Foozle_Enemy_1/Kla'ed/Projectiles/PNGs/Kla'ed - Torpedo.png";

    public static final String IMG_ALIEN0 = "src/Foozle_Enemy_1/Kla'ed/Base/PNGs/Kla'ed - Support ship - Base.png";
    public static final String IMG_ALIEN0_ENGINES = "src/Foozle_Enemy_1/Kla'ed/Engine/PNGs/Kla'ed - Support ship - Engine.png";
    public static final String IMG_ALIEN0_DESTRUCTION = "src/Foozle_Enemy_1/Kla'ed/Destruction/PNGs/Kla'ed - Support ship - Destruction.png";

    public static final String IMG_ALIEN1 = "src/Foozle_Enemy_1/Kla'ed/Base/PNGs/Kla'ed - Scout - Base.png";
    public static final String IMG_ALIEN1_ENGINES = "src/Foozle_Enemy_1/Kla'ed/Engine/PNGs/Kla'ed - Scout - Engine.png";
    public static final String IMG_ALIEN1_DESTRUCTION = "src/Foozle_Enemy_1/Kla'ed/Destruction/PNGs/Kla'ed - Scout - Destruction.png";

    public static final String IMG_ALIEN2 = "src/Foozle_Enemy_1/Kla'ed/Base/PNGs/Kla'ed - Fighter - Base.png";
    public static final String IMG_ALIEN2_ENGINES = "src/Foozle_Enemy_1/Kla'ed/Engine/PNGs/Kla'ed - Fighter - Engine.png";
    public static final String IMG_ALIEN2_DESTRUCTION = "src/Foozle_Enemy_1/Kla'ed/Destruction/PNGs/Kla'ed - Fighter - Destruction.png";

    public static final String IMG_PLAYER = "src/images/NVTurboPlayerV2.png";
    public static final String IMG_PLAYER_DESTRUCTION = "src/Foozle_Enemy_1/Kla'ed/Destruction/PNGs/Kla'ed - Frigate - Destruction.png";
    public static final String IMG_SHOT = "src/images/NV_Shot.png";
    public static final String IMG_LASER_RAY = "src/Foozle_Enemy_1/Kla'ed/Projectiles/PNGs/Kla'ed - Ray.png";
    public static final String IMG_EXPLOSION = "src/images/explosion.png";
    public static final String IMG_TITLE = "src/images/title.png";

    public static final String IMG_POWERUP_SPEEDUP = "src/images/powerup_lightning.png";
    public static final String IMG_POWERUP_HEALTHUP = "src/images/powerup_health.png";
    public static final String IMG_POWERUP_SHOTSIZEUP = "src/images/powerup_bulletUp.png";
    public static final String IMG_POWERUP_DAMGEUP = "src/images/powerup_damageUp.png";
    public static final String IMG_POWERUP_MULTISHOTUP = "src/images/laser_gun.png";

    public static final String SOUND_BG_TITLE = "src/audio/title.wav";
    public static final String SOUND_BG_SCENE1 = "src/audio/scene1.wav";
    public static final String SOUND_EFFECT_LAZER_GUN = "src/audio/mixkit-sci-fi-plasma-gun-power-up-1679.wav";
    public static final String SOUND_EFFECT_LAZER_GUN_SMALL = "src/audio/mixkit-short-laser-gun-shot-1670.wav";
    public static final String SOUND_EFFECT_ARCADE_EXPLOSION = "src/audio/mixkit-arcade-game-explosion-2759.wav";
    public static final String SOUND_EFFECT_ENEMY_EXPLOSION = "src/audio/enemyExplosion.wav";
    public static final String SOUND_EFFECT_LAZER_POWER = "src/audio/edm-zap-246568.wav";
    public static final String SOUND_EFFECT_LAZER_SHIELD = "src/audio/laser-EnegyShield.wav";
    public static final String SOUND_EFFECT_BOSS_EXPLOSION_BIG = "src/audio/boss-explosion-big.wav";
    public static final String SOUND_EFFECT_BOSS_EXPLOSION_SMALL = "src/audio/boss-explosion-small.wav";
    public static final String SOUND_EFFECT_BOSS_POWERMODE = "src/audio/boss_powerMode.wav";
    public static final String SOUND_EFFECT_BOMB = "src/audio/pew-laser-fx_C_minor.wav";

    public static final String SOUND_EFFECT_PLAYER_HIT1 = "src/audio/player-hit.wav";
    public static final String SOUND_EFFECT_PLAYER_HIT2 = "src/audio/player-hit2.wav";
    public static final String SOUND_EFFECT_PLAYER_LEVELUP = "src/audio/player-levelUp.wav";
    public static final String SOUND_EFFECT_PLAYER_LAZER_MODE = "src/audio/riser-fx_C_major.wav";

    public static final String SOUND_BG_WIN = "src/audio/retrogame-win-song_67bpm_F_major.wav";
}
