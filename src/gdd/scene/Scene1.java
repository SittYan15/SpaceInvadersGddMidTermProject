package gdd.scene;

import gdd.AudioPlayer;
import gdd.Game;
import static gdd.Global.*;
import gdd.SpawnDetails;
import gdd.powerup.DamageUp;
import gdd.powerup.HealthUp;
import gdd.powerup.MultiShotUp;
import gdd.powerup.PowerUp;
import gdd.powerup.ShotSizeUp;
import gdd.powerup.SpeedUp;
import gdd.sprite.Alien0;
import gdd.sprite.Alien1;
import gdd.sprite.Alien2;
import gdd.sprite.Bomb;
import gdd.sprite.Boss1;
import gdd.sprite.Enemy;
import gdd.sprite.EnemyBoss;
import gdd.sprite.Explosion;
import gdd.sprite.LazerRay;
import gdd.sprite.Player;
import gdd.sprite.Rocket;
import gdd.sprite.Shot;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Scene1 extends JPanel {

    private int frame = 0;
    private int winFrame = 0;
    private List<PowerUp> powerups;

    private boolean isIntro = true;

    private List<Enemy> enemies;
    private List<EnemyBoss> bosses;

    private List<Explosion> explosions;
    private List<Shot> shots;
    private List<Rocket> rockets;
    private List<Bomb> bombs;
    private List<LazerRay> lazers;
    private Player player;
    // private Shot shot;

    final int BLOCKHEIGHT = 50;
    final int BLOCKWIDTH = 50;

    final int BLOCKS_TO_DRAW = BOARD_HEIGHT / BLOCKHEIGHT;

    private int direction = -1;
    private int deaths = 0;

    private Color winColor = Color.WHITE;
    private int winColorFrame = 0;
    private boolean inGame = true;
    private String message = "Game Over";

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private final Random randomizer = new Random();

    private Timer timer;
    private final Game game;

    private long lazerActivatedTime = 0;

    private int currentRow = -1;
    // TODO load this map from a file
    private int mapOffset = 0;
    private final int[][] MAP = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    private HashMap<Integer, SpawnDetails> spawnMap = new HashMap<>();
    private AudioPlayer audioPlayer;
    private AudioPlayer audioPlayerWin;
    private AudioPlayer audioPlayerEffect;
    private int lastRowToShow;
    private int firstRowToShow;

    public Scene1(Game game) {
        this.game = game;
        // initBoard();
        // gameInit();
        loadSpawnDetails();
    }

    private void initAudio() {
        try {
            String filePath = SOUND_BG_SCENE1;
            audioPlayer = new AudioPlayer(filePath);
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing audio player: " + e.getMessage());
        }
    }

    private void loadSpawnDetails() {
        spawnMap.clear();

        spawnMap.put(799, new SpawnDetails("IntroEnd", 0, 0));

        spawnMap.put(801, new SpawnDetails("PowerUp-SpeedUp", 100, 0));

        // Easy: Alien0, frequent power-ups
        for (int i = 800; i < 3000; i += 100) {
            spawnMap.put(i, new SpawnDetails("Alien0", 100 + (i % 400), 0));
        }

        spawnMap.put(1301, new SpawnDetails("PowerUp-SpeedUp", 100, 0));
        spawnMap.put(1653, new SpawnDetails("PowerUp-ShotSizeUp", 500, 0));
        spawnMap.put(1753, new SpawnDetails("PowerUp-GunCountUp", 350, 0));

        // Medium: Alien0 + Alien1, moderate power-ups
        for (int i = 3000; i < 7000; i += 80) {
            spawnMap.put(i, new SpawnDetails("Alien0", 100 + (i % 500), 0));
            if (i % 300 == 0) {
                spawnMap.put(i + 10, new SpawnDetails("RocketMode", 0, 0));
            }
        }
        spawnMap.put(2999, new SpawnDetails("PowerUp-HealthUp", 300, 0));
        spawnMap.put(3251, new SpawnDetails("PowerUp-HealthUp", 300, 0));
        spawnMap.put(3501, new SpawnDetails("PowerUp-DamageUp", 400, 0));
        // spawnMap.put(3853, new SpawnDetails("PowerUp-GunCountUp", 350, 0));

        spawnMap.put(5251, new SpawnDetails("PowerUp-HealthUp", 300, 0));
        spawnMap.put(6251, new SpawnDetails("PowerUp-HealthUp", 300, 0));

        // Harder: More Alien1, some Alien2, less frequent power-ups
        for (int i = 7000; i < 12000; i += 60) {
            spawnMap.put(i, new SpawnDetails("Alien1", 150 + (i % 400), 0));
            if (i % 400 == 0) {
                spawnMap.put(i + 10, new SpawnDetails("RocketMode", 0, 0));
            }
        }

        spawnMap.put(7501, new SpawnDetails("PowerUp-DamageUp", 400, 0));
        spawnMap.put(7653, new SpawnDetails("PowerUp-ShotSizeUp", 500, 0));
        spawnMap.put(8301, new SpawnDetails("PowerUp-SpeedUp", 100, 0));
        spawnMap.put(8251, new SpawnDetails("PowerUp-HealthUp", 300, 0));
        spawnMap.put(9853, new SpawnDetails("PowerUp-GunCountUp", 350, 0));

        // Difficult: Frequent Alien2, rocket mode, rare power-ups
        for (int i = 12000; i < 17000; i += 50) {
            spawnMap.put(i, new SpawnDetails("Alien2", 200 + (i % 500), 0));
        }

        spawnMap.put(12251, new SpawnDetails("PowerUp-HealthUp", 300, 0));
        spawnMap.put(13501, new SpawnDetails("PowerUp-DamageUp", 400, 0));
        spawnMap.put(13653, new SpawnDetails("PowerUp-SpeedUp", 500, 0));
        spawnMap.put(15251, new SpawnDetails("PowerUp-HealthUp", 300, 0));

        // Final challenge: dense Alien2, very rare power-ups
        for (int i = 17000; i < 18000; i += 40) {
            spawnMap.put(i, new SpawnDetails("Alien2", 200 + (i % 400), 0));
        }

        spawnMap.put(15910, new SpawnDetails("PowerUp-HealthUp", BOARD_WIDTH / 2, 0));
        spawnMap.put(18000, new SpawnDetails("Win", 0, 0));
    }

    // private void loadSpawnDetails() {
    //     // TODO load this from a file
    //     spawnMap.put(50, new SpawnDetails("PowerUp-SpeedUp", 100, 0));
    //     spawnMap.put(251, new SpawnDetails("PowerUp-HealthUp", 300, 0));
    //     spawnMap.put(452, new SpawnDetails("PowerUp-DamageUp", 400, 0));
    //     spawnMap.put(653, new SpawnDetails("PowerUp-ShotSizeUp", 500, 0));
    //     spawnMap.put(753, new SpawnDetails("PowerUp-GunCountUp", 350, 0));
    //     spawnMap.put(30, new SpawnDetails("Boss1", 300, -100));
    //     spawnMap.put(20, new SpawnDetails("Alien0", 200, 0));
    //     spawnMap.put(21, new SpawnDetails("Alien1", 250, 0));
    //     spawnMap.put(23, new SpawnDetails("Alien2", 300, 0));
    //     spawnMap.put(400, new SpawnDetails("Alien1", 400, 0));
    //     spawnMap.put(511, new SpawnDetails("Alien1", 450, 0));
    //     spawnMap.put(622, new SpawnDetails("Alien1", 500, 0));
    //     spawnMap.put(733, new SpawnDetails("Alien1", 550, 0));
    //     spawnMap.put(734, new SpawnDetails("Alien2", 300, 0));
    //     spawnMap.put(810, new SpawnDetails("Alien1", 100, 0));
    //     spawnMap.put(921, new SpawnDetails("Alien1", 150, 0));
    //     spawnMap.put(1632, new SpawnDetails("Alien2", 200, 0));
    //     spawnMap.put(1143, new SpawnDetails("Alien1", 350, 0));
    //     spawnMap.put(1203, new SpawnDetails("Alien2", 350, 0));
    //     spawnMap.put(100, new SpawnDetails("Win", 0, 0));
    // }
    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.black);

        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        gameInit();
        initAudio();
    }

    public void stop() {
        timer.stop();
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void gameInit() {

        enemies = new ArrayList<>();
        bosses = new ArrayList<>();
        powerups = new ArrayList<>();
        explosions = new ArrayList<>();
        shots = new ArrayList<>();
        rockets = new ArrayList<>();
        bombs = new ArrayList<>();
        lazers = new ArrayList<>();

        // for (int i = 0; i < 4; i++) {
        // for (int j = 0; j < 6; j++) {
        // var enemy = new Enemy(ALIEN_INIT_X + (ALIEN_WIDTH + ALIEN_GAP) * j,
        // ALIEN_INIT_Y + (ALIEN_HEIGHT + ALIEN_GAP) * i);
        // enemies.add(enemy);
        // }
        // }
        player = new Player();
        // shot = new Shot();
    }

    private void drawMap(Graphics g) {
        // Draw scrolling starfield background

        // Calculate smooth scrolling offset (1 pixel per frame)
        int scrollOffset = (frame) % BLOCKHEIGHT;

        // Calculate which rows to draw based on screen position
        int baseRow = (frame) / BLOCKHEIGHT;
        int rowsNeeded = (BOARD_HEIGHT / BLOCKHEIGHT) + 2; // +2 for smooth scrolling

        // Loop through rows that should be visible on screen
        for (int screenRow = 0; screenRow < rowsNeeded; screenRow++) {
            // Calculate which MAP row to use (with wrapping)
            int mapRow = (baseRow + screenRow) % MAP.length;

            // Calculate Y position for this row
            // int y = (screenRow * BLOCKHEIGHT) - scrollOffset;
            int y = BOARD_HEIGHT - ((screenRow * BLOCKHEIGHT) - scrollOffset);

            // Skip if row is completely off-screen
            if (y > BOARD_HEIGHT || y < -BLOCKHEIGHT) {
                continue;
            }

            // Draw each column in this row
            for (int col = 0; col < MAP[mapRow].length; col++) {
                if (MAP[mapRow][col] == 1) {
                    // Calculate X position
                    int x = col * BLOCKWIDTH;

                    // Draw a cluster of stars
                    drawStarCluster(g, x, y, BLOCKWIDTH, BLOCKHEIGHT);
                }
            }
        }

    }

    private void drawHealthBar(Graphics g) {
        int maxHealth = player.getMaxHeath(); // Set this to your player's max health
        int health = player.getHealth();
        int barWidth = 100;
        int barHeight = 12;
        int x = 20;
        int y = 80;

        // Draw background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        // Draw health
        int healthWidth = (int) ((double) health / maxHealth * barWidth);
        g.setColor(Color.RED);
        g.fillRect(x, y, healthWidth, barHeight);

        // Draw border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, barWidth, barHeight);

        // Draw text
        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        g.drawString("Health: " + health + "/" + maxHealth, x + 5, y + barHeight - 2);
    }

    private void drawPowerBar(Graphics g) {
        int maxPower = player.getMaxPower(); // Set this to your player's max health
        double power = player.getPower();
        int barWidth = 100;
        int barHeight = 12;
        int x = 20;
        int y = 100;

        // Draw background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        // Draw health
        int healthWidth = (int) ((double) power / maxPower * barWidth);
        g.setColor(Color.BLUE);
        g.fillRect(x, y, healthWidth, barHeight);

        // Draw border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, barWidth, barHeight);

        // Draw text
        g.setFont(new Font("Helvetica", Font.BOLD, 12));
        g.drawString("Power: " + String.format("%.1f", power) + "/" + maxPower, x + 5, y + barHeight - 2);
    }

    private void drawBossHealthBar(Graphics g, EnemyBoss boss, boolean isShielded) {

        int maxHealth = boss.getMaxHeath(); // Set this to your player's max health
        double health = boss.getHealth();
        int barWidth = 100;
        int barHeight = 5;
        int x = boss.getX() + 14;
        int y = boss.getY() + 13;

        // Draw background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        // Draw health
        int healthWidth = (int) ((double) health / maxHealth * barWidth);
        g.setColor(Color.RED);
        g.fillRect(x, y, healthWidth, barHeight);

        // Draw border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, barWidth, barHeight);

        if (isShielded) {

            // Draw background
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y - 8, barWidth, barHeight);

            // Draw Shield Health
            int shieldWidth = (int) ((double) boss.getShieldHealth() / boss.getMaxShieldHealth() * barWidth);
            g.setColor(Color.BLUE);
            g.fillRect(x, y - 8, shieldWidth, barHeight);

            // Draw border
            g.setColor(Color.WHITE);
            g.drawRect(x, y - 8, barWidth, barHeight);
        }
    }

    private void drawStarCluster(Graphics g, int x, int y, int width, int height) {
        // Set star color to white
        g.setColor(Color.WHITE);

        // Draw multiple stars in a cluster pattern
        // Main star (larger)
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g.fillOval(centerX - 2, centerY - 2, 4, 4);

        // Smaller surrounding stars
        g.fillOval(centerX - 15, centerY - 10, 2, 2);
        g.fillOval(centerX + 12, centerY - 8, 2, 2);
        g.fillOval(centerX - 8, centerY + 12, 2, 2);
        g.fillOval(centerX + 10, centerY + 15, 2, 2);

        // Tiny stars for more detail
        g.fillOval(centerX - 20, centerY + 5, 1, 1);
        g.fillOval(centerX + 18, centerY - 15, 1, 1);
        g.fillOval(centerX - 5, centerY - 18, 1, 1);
        g.fillOval(centerX + 8, centerY + 20, 1, 1);
    }

    private void drawAliens(Graphics g) {

        for (Enemy enemy : enemies) {

            if (enemy.isVisible()) {

                Graphics2D g2d = (Graphics2D) g;
                Image enemyImg = enemy.getImage();
                int x = enemy.getX();
                int y = enemy.getY();
                int w = enemyImg.getWidth(this);
                int h = enemyImg.getHeight(this);

                // Save the original transform
                AffineTransform old = g2d.getTransform();

                // Flip vertically around the image's top-left corner
                g2d.translate(x, y + h);
                g2d.scale(1, -1);

                // draw Alien Base
                g2d.drawImage(enemyImg, 0, 0, this);
                // g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);

                if (enemy.getIsAlive()) {
                    Image enemyEngine = enemy.getEngine();
                    g2d.drawImage(enemyEngine, 0, 0, this);
                }

                // Restore the original transform
                g2d.setTransform(old);
            }

            if (enemy.isDying()) {

                enemy.die();
            }
        }

        for (EnemyBoss boss : bosses) {

            if (boss.isVisible()) {

                Graphics2D g2d = (Graphics2D) g;
                Image bossImg = boss.getImage();
                int x = boss.getX();
                int y = boss.getY();
                int w = bossImg.getWidth(this);
                int h = bossImg.getHeight(this);

                // Save the original transform
                AffineTransform old = g2d.getTransform();

                // Flip vertically around the image's top-left corner
                g2d.translate(x, y + h);
                g2d.scale(1, -1);

                // draw Alien Base
                g2d.drawImage(bossImg, 0, 0, this);

                if (boss.getisAlive()) {
                    Image bossEngine = boss.getEngine();
                    g2d.drawImage(bossEngine, 0, 0, this);
                }

                if (boss.getShielded()) {
                    Image bossShield = boss.getShield();
                    g2d.drawImage(bossShield, 0, 0, this);
                }

                // Restore the original transform
                g2d.setTransform(old);
                if (boss.getisAlive()) {
                    drawBossHealthBar(g, boss, boss.getShielded());
                }

                if (boss.getClipNoPowerMode() == 11 && boss.powerMode1Right == 1) {
                    boss1Rocket1Right(boss);
                    boss.powerMode1Right--;
                } else if (boss.getClipNoPowerMode() == 13 && boss.powerMode2Right == 1) {
                    boss1Rocket2Right(boss);
                    boss.powerMode2Right--;
                } else if (boss.getClipNoPowerMode() == 26 && boss.powerMode1Left == 1) {
                    boss1Rocket1Left(boss);
                    boss.powerMode1Left--;
                } else if (boss.getClipNoPowerMode() == 28 && boss.powerMode2Left == 1) {
                    boss1Rocket2Left(boss);
                    boss.powerMode2Left--;
                }
            }

            if (boss.isDying()) {

                boss.die();
            }
        }
    }

    private void drawPowreUps(Graphics g) {

        for (PowerUp p : powerups) {
            if (p.isVisible()) {
                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
                // g.drawRect(p.getX(), p.getY(), 20, 20);
            }
            if (p.isDying()) {
                p.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {
            double scale = 1.2; // scale factor
            int scaledWidth = (int) (player.getWidth() * scale);
            int scaledHeight = (int) (player.getHeight() * scale);
            int drawX = player.getX() - (scaledWidth / 2);
            int drawY = player.getY() - scaledHeight;

            g.drawImage(player.getImage(), drawX, drawY, scaledWidth, scaledHeight, this);
            // g.drawRect(player.getX(), player.getY(), -12, -38);
            // g.drawRect(drawX, drawY, scaledWidth, scaledHeight);

            //making lazer
            // g.drawImage(player.getLazerRay(), player.getX(), player.getY(), 30, -700, this);
        }

        if (player.isDying()) {
            player.die();
            inGame = false;
        }

        // if (player.isVisible()) {
        //     g.drawImage(player.getImage(), player.getX()-(player.getWidth()/2), player.getY()-player.getHeight(), player.getWidth(), player.getHeight(), this);
        //     // if (player.getFacing() == Player.DIR_RIGHT) {
        //     //     g.drawImage(player.getImage(), player.getX()-(player.getWidth()/2), player.getY()-player.getHeight(), player.getWidth(), player.getHeight(), this);
        //     // } else {
        //     //     g.drawImage(player.getImage(), player.getX()+player.getWidth()/2, player.getY()-player.getHeight(), -player.getWidth(), player.getHeight(), this);
        //     // }
        //     // g.drawImage(player.getImage(), player.getX(), player.getY()-player.getHeight(), this);
        //     g.drawRect(player.getX(),player.getY(),10,10);
        //     g.drawRect(player.getX()-(player.getWidth()/2),player.getY()-player.getHeight(),player.getWidth(),player.getHeight());
        //     g.drawString("Player Frame# "+player.getFrame(), 300,10);
        //     g.drawString("Player Action# "+player.getAction(), 550,10);
        // }
        // // if (player.isVisible()) {
        // //     g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        // // }
        // if (player.isDying()) {
        //     player.die();
        //     inGame = false;
        // }
    }

    private void drawShot(Graphics g) {

        for (Shot shot : shots) {

            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }

    private void drawLazer(Graphics g) {
        for (LazerRay lazer : lazers) {
            if (lazer.isVisible()) {
                lazer.act(); // Update the lazer ray animation
                g.drawImage(lazer.getImage(), lazer.getX(), lazer.getY(), 30, 700, this);
            }
        }
    }

    private void drawBombing(Graphics g) {

        for (Bomb bomb : bombs) {
            if (bomb.isVisible()) {
                g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
            }
        }
    }

    private void drawRockets(Graphics g) {

        for (Rocket rocket : rockets) {
            if (!rocket.isDestroyed()) {
                // Calculate the center of the rocket image
                // int w = img.getWidth(null);
                // int h = img.getHeight(null);
                // int rocketCenterX = rocketX + w / 2;
                // int rocketCenterY = rocketY + h / 2;
                rocket.act();

                int w = 10;
                int h = 25;
                int rocketCenterX = rocket.getX() + w / 2;
                int rocketCenterY = rocket.getY() + h / 2;

                Graphics2D g2d = (Graphics2D) g;
                AffineTransform old = g2d.getTransform();

                double angleRadians;
                if (rocket.tracking) {
                    // While tracking, point toward the player
                    int playerCenterX = player.getX();
                    int playerCenterY = player.getY();
                    double dx = playerCenterX - rocketCenterX;
                    double dy = playerCenterY - rocketCenterY;
                    angleRadians = Math.atan2(dy, dx) + Math.PI / 2;
                } else {
                    // After tracking, keep the last angle
                    angleRadians = Math.atan2(rocket.lastDy, rocket.lastDx) + Math.PI / 2;
                }

                g2d.translate(rocketCenterX, rocketCenterY);
                g2d.rotate(angleRadians);
                g2d.drawImage(rocket.getImage(), -w / 2, -h / 2, null);

                g2d.setTransform(old);
            }
        }

    }

    private void drawExplosions(Graphics g) {

        List<Explosion> toRemove = new ArrayList<>();

        for (Explosion explosion : explosions) {

            int offsetX = 2;
            int offsetY = 10;
            if (explosion.isBig) {
                explosion.clipNo = 0; // Big explosion
            } else {
                explosion.clipNo = 1; // Small explosion
            }
            if (explosion.isVisible()) {
                g.drawImage(explosion.getImage(), explosion.getX() - offsetX, explosion.getY() + offsetY, this);
                explosion.visibleCountDown();
                if (!explosion.isVisible()) {
                    toRemove.add(explosion);
                }
            }
        }

        explosions.removeAll(toRemove);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        g.setColor(Color.white);

        // Top row: Score and Kill
        g.drawString("Score: " + frame, 20, 25);
        g.drawString("Kill: " + deaths, 160, 25);

        // Second row: Speed, GunMode
        g.drawString("Speed: " + player.getSpeed(), 20, 45);
        g.drawString("GunMode: " + gunCount, 160, 45);

        // Third row: Damage, Shot Intensity
        g.drawString("Damage: " + shotDamage, 20, 65);
        g.drawString("Shot Intensity: " + shotCooldownMillis, 160, 65);

        g.setColor(Color.green);

        if (inGame) {

            drawMap(g);  // Draw background stars first
            drawPowreUps(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawRockets(g);
            drawExplosions(g);
            drawHealthBar(g);
            drawPowerBar(g);
            drawLazer(g);

            if (isIntro) {
                drawIntroScript(g);
            }

            if (isScene1Win) {
                drawScene1Win(g);
            }

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawIntroScript(Graphics g) {
        // Fade-in effect based on frame
        int alpha = Math.min(255, frame * 3); // Fade in over 85 frames (~1.5 sec)
        Color introDanger = new Color(255, 0, 0, alpha); // Red with fade
        Color introColor = new Color(0, 255, 255, alpha); // Cyan with fade

        g.setColor(introDanger);
        g.setFont(new Font("Orbitron", Font.BOLD, 28)); // Use a sci-fi font if available
        g.drawString("EARTH IS UNDER THREAT!", 80, 170);

        g.setColor(introColor);
        g.setFont(new Font("Orbitron", Font.PLAIN, 20));
        g.drawString("A hostile alien boss is protected by waves of spacecraft.", 80, 210);
        g.drawString("Destroy enemy ships to collect power energy.", 80, 290);
        g.drawString("Gather enough energy to activate your powerful laser weapon.", 80, 320);
        g.drawString("Can you take down the alien boss and save Earth?", 80, 350);

        g.setFont(new Font("Orbitron", Font.BOLD, 22));
        g.setColor(new Color(255, 255, 0, alpha)); // Yellow for emphasis
        g.drawString("Good Luck, Pilot!", 80, 400);
    }

    private void gameOver(Graphics g) {
        try {
            audioPlayer.stop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Game.saveScoreToCSV(frame, deaths);
        game.loadGameOver(1);
    }

    private void fireShot() {

        int x = player.getX();
        int y = player.getY();

        long now = System.currentTimeMillis();
        if (now - lastShotTime >= shotCooldownMillis) {
            // Create a new shot and add it to the list
            switch (gunCount) {
                case 1 -> {
                    Shot shot = new Shot(x, y);
                    shots.add(shot);
                    lastShotTime = now;
                }
                case 2 -> {
                    Shot shot = new Shot(x - 8, y + 15);
                    shots.add(shot);
                    shot = new Shot(x + 8, y + 15);
                    shots.add(shot);
                    lastShotTime = now;
                }
                case 3 -> {
                    Shot shot = new Shot(x - 10, y + 15, true, -1);
                    shots.add(shot);
                    shot = new Shot(x, y);
                    shots.add(shot);
                    shot = new Shot(x + 10, y + 15, true, 1);
                    shots.add(shot);
                    lastShotTime = now;
                }
                case 4 -> {
                    Shot shot = new Shot(x - 10, y + 15, true, -1);
                    shots.add(shot);
                    shot = new Shot(x, y);
                    shots.add(shot);
                    shot = new Shot(x + 10, y + 15, true, 1);
                    shots.add(shot);
                    shot = new Shot(x - 8, y + 10, true, -2);
                    shots.add(shot);
                    shot = new Shot(x + 8, y + 10, true, 2);
                    shots.add(shot);
                    lastShotTime = now;
                }
                default -> {
                    Shot shot = new Shot(x, y);
                    shots.add(shot);
                    lastShotTime = now;
                }
            }
            try {
                audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_LAZER_GUN, false);
                audioPlayerEffect.play();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void activeLazers() {
        for (int i = 1; i < 800; i = i + 100) {
            lazers.add(new LazerRay(i, -10));
        }
        lazerActivatedTime = System.currentTimeMillis();
        try {
            audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_PLAYER_LAZER_MODE, false);
            audioPlayerEffect.play();
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void Scene1Win() {
        try {
            // audioPlayer.stop();
            audioPlayer.stop();
            // audioPlayerEffect.stop();
            audioPlayer = new AudioPlayer(SOUND_BG_WIN, false);
            audioPlayer.play();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        isScene1Win = true;
        playerSpeed = player.getSpeed();
        playerHealth = player.getHealth();
        playerPower = player.getPower();
        distance = frame;
        kill = deaths;
    }

    private void drawScene1Win(Graphics g) {
        g.setColor(winColor);
        g.setFont(new Font("Helvetica", Font.BOLD, 24));
        g.drawString("Well Done!", BOARD_WIDTH / 2 - 50, BOARD_HEIGHT / 2 - 20);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString("The Boss is coming to you, be careful...", BOARD_WIDTH / 2 - 80, BOARD_HEIGHT / 2 + 20);
    }

    private void update() {

        if (isScene1Win) {
            winFrame++;
            winColorFrame++;
            for (Enemy enemy : enemies) {
                enemy.setIsAlive(false);
            }
            for (EnemyBoss boss : bosses) {
                boss.setisAlive(false);
            }
            player.setY(player.getY() - 2);

            if (winColorFrame <= 10) {
                winColor = Color.red;
            } else if (winColorFrame > 20) {
                winColor = Color.white;
                winColorFrame = 0; // Reset the frame counter for color change
            }

            if (winFrame > 350) {
                // inGame = false;
                timer.stop();
                game.loadScene2();
            }

        }

        // Check enemy spawn
        SpawnDetails sd = spawnMap.get(frame);
        if (sd != null) {
            // Create a new enemy based on the spawn details
            switch (sd.type) {
                case "Alien0" -> {
                    enemies.add(new Alien0(sd.x, sd.y));
                }
                case "Alien1" -> {
                    enemies.add(new Alien1(sd.x, sd.y));
                }
                case "Alien2" -> {
                    enemies.add(new Alien2(sd.x, sd.y));
                }
                case "Boss1" -> {
                    bosses.add(new Boss1(sd.x, sd.y));
                }
                case "PowerUp-SpeedUp" -> {
                    powerups.add(new SpeedUp(sd.x, sd.y));
                }
                case "PowerUp-HealthUp" -> {
                    powerups.add(new HealthUp(sd.x, sd.y));
                }
                case "PowerUp-DamageUp" -> {
                    powerups.add(new DamageUp(sd.x, sd.y));
                }
                case "PowerUp-ShotSizeUp" -> {
                    powerups.add(new ShotSizeUp(sd.x, sd.y));
                }
                case "PowerUp-GunCountUp" -> {
                    powerups.add(new MultiShotUp(sd.x, sd.y));
                }
                case "RocketMode" -> {
                    for (int i = 1; i < 600; i = i + 100) {
                        rockets.add(new Rocket(i, 0));
                    }
                }
                case "Win" -> {
                    Scene1Win();
                }
                case "IntroEnd" -> {
                    isIntro = false;
                }
                default ->
                    System.out.println("Unknown enemy type: " + sd.type);
            }
        }

        // if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
        //     inGame = false;
        //     timer.stop();
        //     message = "Game won!";
        // }
        // player
        player.act();

        // Power-ups
        for (PowerUp powerup : powerups) {
            if (powerup.isVisible()) {
                powerup.act();
                if (powerup.collidesWithPlayer(player)) {
                    try {
                        audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_PLAYER_LEVELUP, false);
                        audioPlayerEffect.play();
                    } catch (UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                    powerup.upgrade(player);
                }
            }
        }

        // Enemies
        for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
                enemy.act(direction);
            }
        }

        for (EnemyBoss boss : bosses) {
            if (boss.isVisible()) {
                boss.act(direction);
            }
        }

        // shot
        List<Shot> shotsToRemove = new ArrayList<>();
        for (Shot shot : shots) {

            if (shot.isVisible()) {
                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Enemy enemy : enemies) {
                    // Collision detection: shot and enemy
                    int enemyX = enemy.getX();
                    int enemyY = enemy.getY();

                    if (enemy.isVisible() && shot.isVisible()
                            && shotX >= (enemyX + 15)
                            && shotX <= (enemyX + ALIEN_WIDTH + 20)
                            && shotY >= (enemyY + 15)
                            && shotY <= (enemyY + ALIEN_HEIGHT + 20)) {

                        if (enemy.getIsHit()) {
                            continue;
                        }

                        if (shot.getDamage() - enemy.getHealth() > 0) {
                            shot.setDamage(shot.getDamage() - enemy.getHealth());
                        } else {
                            shot.die();
                            shotsToRemove.add(shot);
                        }

                        if (enemy.getHealth() - shot.getDamage() > 0) {
                            enemy.setHealth(enemy.getHealth() - shot.getDamage());
                            explosions.add(new Explosion(enemyX + 25, enemyY + 20, false));
                            try {
                                audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_ARCADE_EXPLOSION, false);
                                audioPlayerEffect.play();
                            } catch (UnsupportedAudioFileException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            } catch (LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            // var ii = new ImageIcon(IMG_EXPLOSION);
                            // var ii = new ImageIcon(IMG_ENEMY);
                            // enemy.setImage(ii.getImage());
                            explosions.add(new Explosion(enemyX + 20, enemyY + 20, true));
                            enemy.setIsAlive(false);
                            enemy.setIsHit(true);
                            // enemy.setDying(true);
                            if (enemy.getLevel() == 0) {
                                if (player.getPower() + 0.3 > player.getMaxPower()) {
                                    player.setPower(player.getMaxPower());
                                } else {
                                    player.setPower(player.getPower() + 0.3);
                                }
                            } else {
                                double powerToAdd = (double) enemy.getLevel() / 2;
                                if (player.getPower() + powerToAdd > player.getMaxPower()) {
                                    player.setPower(player.getMaxPower());
                                } else {
                                    player.setPower(player.getPower() + ((double) enemy.getLevel() / 2));
                                }
                            }
                            try {
                                audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_ENEMY_EXPLOSION, false);
                                audioPlayerEffect.play();
                            } catch (UnsupportedAudioFileException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            } catch (LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }
                            deaths++;
                        }
                    }
                }

                for (EnemyBoss boss : bosses) {
                    // Collision detection: shot and enemy
                    int enemyX = boss.getX();
                    int enemyY = boss.getY();

                    if (boss.isVisible() && shot.isVisible()
                            && shotX >= (enemyX + 25)
                            && shotX <= (enemyX + boss.getWidth() - 25)
                            && shotY >= (enemyY)
                            && shotY <= (enemyY + boss.getHeight())) {

                        shot.die();
                        shotsToRemove.add(shot);

                        if (!boss.getShielded()) {
                            if (boss.getHealth() - shotDamage > 0) {
                                boss.setHealth(boss.getHealth() - shotDamage);
                                if (boss.getHealth() < 10 && !boss.getShielded()) {
                                    boss.setShieldHealth(boss.getMaxShieldHealth());
                                    boss.setShielded(true);
                                }
                                if (boss.getPowerModeCount() > 0) {
                                    boss.setPowerModeCount(boss.getPowerModeCount() - 1);
                                    boss.setisPowerMode(true);
                                    try {
                                        audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_BOSS_POWERMODE, false);
                                        audioPlayerEffect.play();
                                    } catch (UnsupportedAudioFileException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (LineUnavailableException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                explosions.add(new Explosion(enemyX + 50, enemyY + 50, false));
                                try {
                                    audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_BOSS_EXPLOSION_SMALL, false);
                                    audioPlayerEffect.play();
                                } catch (UnsupportedAudioFileException ex) {
                                    throw new RuntimeException(ex);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                } catch (LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                // var ii = new ImageIcon(IMG_EXPLOSION);
                                // var ii = new ImageIcon(IMG_ENEMY);
                                // enemy.setImage(ii.getImage());
                                explosions.add(new Explosion(enemyX + 50, enemyY + 50, true));
                                try {
                                    audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_BOSS_EXPLOSION_BIG, false);
                                    audioPlayerEffect.play();
                                } catch (UnsupportedAudioFileException ex) {
                                    throw new RuntimeException(ex);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                } catch (LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                                // boss.setDying(true);
                                boss.setisAlive(false);
                                if (player.getPower() + 3 > player.getMaxPower()) {
                                    player.setPower(player.getMaxPower());
                                } else {
                                    player.setPower(player.getPower() + 3);
                                }
                                deaths = deaths + 5;
                                shot.die();
                                shotsToRemove.add(shot);
                            }
                        } else {
                            if (boss.getShieldHealth() - shotDamage > 0) {
                                boss.setShieldHealth(boss.getShieldHealth() - shotDamage);
                                try {
                                    audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_LAZER_SHIELD, false);
                                    audioPlayerEffect.play();
                                } catch (UnsupportedAudioFileException ex) {
                                    throw new RuntimeException(ex);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                } catch (LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else {
                                boss.setShielded(false);
                            }
                        }
                    }
                }

                // Function for collision detection between shot and rockets
                List<Rocket> rocketsToRemove = new ArrayList<>();
                for (Rocket rocket : rockets) {
                    // Collision detection: shot and enemy
                    int rocketX = rocket.getX();
                    int rocketY = rocket.getY();

                    if (!rocket.isDestroyed() && shot.isVisible()
                            && shotX >= (rocketX - 5)
                            && shotX <= (rocketX + rocket.getWidth() - 25)
                            && shotY >= (rocketY)
                            && shotY <= (rocketY + rocket.getHeight())) {

                        shot.die();
                        shotsToRemove.add(shot);
                        if (player.getPower() + 0.2 > player.getMaxPower()) {
                            player.setPower(player.getMaxPower());
                        } else {
                            player.setPower(player.getPower() + 0.2);
                        }
                        rocket.setDestroyed(true);
                        rocketsToRemove.add(rocket);
                        explosions.add(new Explosion(rocketX, rocketY, false));
                        try {
                            audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_ARCADE_EXPLOSION, false);
                            audioPlayerEffect.play();
                        } catch (UnsupportedAudioFileException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                rockets.removeAll(rocketsToRemove);

                int y = shot.getY();
                y -= 15;
                // y -= 20;

                if (y < 0) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else {
                    shot.setY(y);
                    if (shot.isSided()) {
                        shot.setX(shot.getX() + shot.isLeft());
                    }
                }
            }
        }
        shots.removeAll(shotsToRemove);

        // Bomb
        List<Bomb> bombsToRemove = new ArrayList<>();

        for (Bomb bomb : bombs) {
            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed() && !player.isInvincible()
                    && bombX >= (playerX - PLAYER_WIDTH)
                    && bombX <= (playerX + PLAYER_WIDTH)
                    && bombY >= (playerY - PLAYER_HEIGHT)
                    && bombY <= (playerY)) {

                bomb.setDestroyed(true);
                bombsToRemove.add(bomb);
                int health = player.getHealth();
                if (health > 0) {
                    health--;
                    player.setHealth(health);
                    player.setInvincible(30);
                    try {
                        audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_PLAYER_HIT1, false);
                        audioPlayerEffect.play();
                    } catch (UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    var ii = new ImageIcon(IMG_EXPLOSION);
                    player.setImage(ii.getImage());
                    // player.setDying(true);
                    try {
                        audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_PLAYER_HIT2, false);
                        audioPlayerEffect.play();
                    } catch (UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                    player.setIsAlive(false);
                }
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 5);
                if (bomb.getY() >= GROUND - BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
        bombs.removeAll(bombsToRemove);


        // Rocket collision detection & movement
        List<Rocket> rocketsToRemove = new ArrayList<>();
        for (Rocket rocket : rockets) {
            if (rocket.isVisible()) {

                int rocketX = rocket.getX();
                int rocketY = rocket.getY();
                int playerX = player.getX();
                int playerY = player.getY();

                if (player.isVisible() && !rocket.isDestroyed() && !player.isInvincible()
                        && rocketX >= (playerX - PLAYER_WIDTH)
                        && rocketX <= (playerX + PLAYER_WIDTH)
                        && rocketY >= (playerY - PLAYER_HEIGHT)
                        && rocketY <= (playerY)) {
                    rocket.setDestroyed(true);
                    rocketsToRemove.add(rocket);
                    int health = player.getHealth();
                    if (health > 0) {
                        health--;
                        player.setHealth(health);
                        player.setInvincible(30);
                        try {
                            audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_PLAYER_HIT1, false);
                            audioPlayerEffect.play();
                        } catch (UnsupportedAudioFileException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        var ii = new ImageIcon(IMG_EXPLOSION);
                        player.setImage(ii.getImage());
                        // player.setDying(true);
                        try {
                            audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_PLAYER_HIT2, false);
                            audioPlayerEffect.play();
                        } catch (UnsupportedAudioFileException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                        player.setIsAlive(false);
                    }
                }

                if (!rocket.tracking && rocket.isSpeeding) {
                    // Move left for straightFrames frames
                    rocketX += (int) Math.round(rocket.lastDx);
                    rocketY += (int) Math.round(rocket.lastDy);
                    rocket.setX(rocketX);
                    rocket.setY(rocketY);
                    rocket.framesMoved++;
                    if (rocket.framesMoved >= rocket.straightFrames) {
                        rocket.tracking = true;
                        rocket.isSpeeding = false;
                    }
                } else if (!rocket.isSpeeding) {
                    // Calculate direction vector
                    double dx = playerX - rocketX;
                    double dy = playerY - rocketY;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    // Set rocket speed (pixels per frame)
                    double speed = rocketSpeed;
                    // Normalize direction and update rocket position
                    if (rocketY > playerY) {
                        rocket.tracking = false;
                    }

                    if (distance != 0 & rocket.tracking) {
                        double moveDx = speed * dx / distance;
                        double moveDy = speed * dy / distance;
                        rocket.lastDx = moveDx;
                        rocket.lastDy = moveDy;
                        rocketX += (int) Math.round(moveDx);
                        rocketY += (int) Math.round(moveDy);
                        rocket.setX(rocketX);
                        rocket.setY(rocketY);
                    } else {
                        // Move in the last direction (straight line)
                        rocketX += (int) Math.round(rocket.lastDx);
                        rocketY += (int) Math.round(rocket.lastDy);
                        rocket.setX(rocketX);
                        rocket.setY(rocketY);
                    }
                }

                // Destroy rocket if it goes out of bounds
                if (rocketY >= BOARD_HEIGHT + 100 || rocketX < 0 || rocketX > BOARD_WIDTH) {
                    rocket.setDestroyed(true);
                    rocketsToRemove.add(rocket);
                }
            }
        }
        rockets.removeAll(rocketsToRemove);

        // enemies
        for (Enemy enemy : enemies) {
            int x = enemy.getX();
            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
            }
            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;
            }
        }
        // Uncomment this if you want to get invation functionality
        // for (Enemy enemy : enemies) {
        //     if (enemy.isVisible()) {
        //         int y = enemy.getY();
        //         if (y > GROUND - ALIEN_HEIGHT) {
        //             inGame = false;
        //             message = "Invasion!";
        //         }
        //         enemy.act(direction);
        //     }
        // }
        // bombs - collision detection
        // Bomb is with enemy, so it loops over enemies

        int offsetX = 0;
        int offsetY = 0;
        int chance = 0;
        int chance2 = 0;
        for (Enemy enemy : enemies) {
            switch (enemy.getLevel()) {
                case 0 -> {
                    continue; // Skip Alien0
                }
                case 1 -> {
                    offsetX = 31;
                    offsetY = 34;
                    chance = 10;
                    chance2 = randomizer.nextInt(20);
                }
                case 2 -> {
                    if (randomizer.nextBoolean()) {
                        offsetX = 19;
                        offsetY = 34;
                    } else {
                        offsetX = 40;
                        offsetY = 34;
                    }
                    chance = 10;
                    chance2 = randomizer.nextInt(15);
                }
                default -> {
                    System.out.println("Unknown enemy level: " + enemy.getLevel());
                }
            }

            Bomb bomb = new Bomb(0, 0);

            if (frame % chance == 0 && chance2 == CHANCE && enemy.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(enemy.getX() + offsetX);
                bomb.setY(enemy.getY() + offsetY);
                bombs.add(bomb);
                try {
                    audioPlayerEffect = new AudioPlayer(SOUND_EFFECT_BOMB, false);
                    audioPlayerEffect.play();
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        // chance = randomizer.nextInt(50);
        // for (EnemyBoss boss : bosses) {
        //     // Rocket
        //     if (chance == CHANCE) {
        //         rockets.add(new Rocket(boss.getX(), boss.getY()));
        //         System.out.println("Rocket!!!!!");
        //     }
        // }
        if (spacePressed) {
            fireShot();
        }

        for (LazerRay lazer : lazers) {
            if (lazer.isVisible()) {
                for (Enemy enemy : enemies) {
                    if (enemy.isVisible()
                            && lazer.getX() >= enemy.getX()
                            && lazer.getX() <= enemy.getX() + enemy.getWidth()
                            && lazer.getY() <= enemy.getY() + enemy.getHeight()) {
                        enemy.setDying(true);
                        explosions.add(new Explosion(enemy.getX(), enemy.getY(), true));
                    }
                }
                for (EnemyBoss boss : bosses) {
                    if (boss.isVisible()
                            && lazer.getX() >= boss.getX()
                            && lazer.getX() <= boss.getX() + boss.getWidth()
                            && lazer.getY() <= boss.getY() + boss.getHeight()) {
                        boss.setisAlive(false);
                        explosions.add(new Explosion(boss.getX(), boss.getY(), true));
                    }
                }
            }
        }

        // Destroy lazer rays after 3 seconds
        if (!lazers.isEmpty() && lazerActivatedTime > 0) {
            if (System.currentTimeMillis() - lazerActivatedTime >= 2000) {
                lazers.clear();
                lazerActivatedTime = 0;
            }
        }
    }

    private void boss1Rocket1Left(EnemyBoss boss) {
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 130, true, -rocketSpeed, 0, 15));
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 132, true, -rocketSpeed, 0, 20));
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 134, true, -rocketSpeed, 0, 25));
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 136, true, -rocketSpeed, 0, 30));
    }

    private void boss1Rocket2Left(EnemyBoss boss) {
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 100, true, -rocketSpeed, 0, 15));
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 102, true, -rocketSpeed, 0, 20));
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 104, true, -rocketSpeed, 0, 25));
        rockets.add(new Rocket(boss.getX() + 40, boss.getY() + 106, true, -rocketSpeed, 0, 30));
    }

    private void boss1Rocket1Right(EnemyBoss boss) {
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 130, true, rocketSpeed, 0, 15));
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 132, true, rocketSpeed, 0, 20));
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 134, true, rocketSpeed, 0, 25));
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 136, true, rocketSpeed, 0, 30));
    }

    private void boss1Rocket2Right(EnemyBoss boss) {
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 100, true, rocketSpeed, 0, 15));
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 102, true, rocketSpeed, 0, 20));
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 104, true, rocketSpeed, 0, 25));
        rockets.add(new Rocket(boss.getX() + 80, boss.getY() + 106, true, rocketSpeed, 0, 30));
    }

    private void doGameCycle() {
        frame++;
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                spacePressed = false;
            }
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
            player.keyPressed(e);

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE && inGame) {
                spacePressed = true;
            }

            if (key == KeyEvent.VK_C) {
                if (player.getPower() == player.getMaxPower()) {
                    player.setPower(0);
                    activeLazers();
                }
            }

        }
    }
}
