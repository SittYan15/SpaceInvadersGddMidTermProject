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
import gdd.sprite.Boss1;
import gdd.sprite.Enemy;
import gdd.sprite.EnemyBoss;
import gdd.sprite.Explosion;
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
    private List<PowerUp> powerups;

    private List<Enemy> enemies;
    private List<EnemyBoss> bosses;

    private List<Explosion> explosions;
    private List<Shot> shots;
    private List<Rocket> rockets;
    private Player player;
    // private Shot shot;

    final int BLOCKHEIGHT = 50;
    final int BLOCKWIDTH = 50;

    final int BLOCKS_TO_DRAW = BOARD_HEIGHT / BLOCKHEIGHT;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private final Random randomizer = new Random();

    private Timer timer;
    private final Game game;

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
    private int lastRowToShow;
    private int firstRowToShow;

    public Scene1(Game game) {
        this.game = game;
        // initBoard();
        // gameInit();
        loadSpawnDetails();
    }

    private void initAudio() {
        // try {
        //     String filePath = "src/audio/scene1.wav";
        //     audioPlayer = new AudioPlayer(filePath);
        //     audioPlayer.play();
        // } catch (Exception e) {
        //     System.err.println("Error initializing audio player: " + e.getMessage());
        // }
    }

    private void loadSpawnDetails() {
        // TODO load this from a file
        spawnMap.put(50, new SpawnDetails("PowerUp-SpeedUp", 100, 0));
        spawnMap.put(251, new SpawnDetails("PowerUp-HealthUp", 300, 0));
        spawnMap.put(452, new SpawnDetails("PowerUp-DamageUp", 400, 0));
        spawnMap.put(653, new SpawnDetails("PowerUp-ShotSizeUp", 500, 0));
        spawnMap.put(753, new SpawnDetails("PowerUp-GunCountUp", 350, 0));

        spawnMap.put(30, new SpawnDetails("Boss1", 300, 100));

        spawnMap.put(20, new SpawnDetails("Alien0", 200, 0));
        spawnMap.put(21, new SpawnDetails("Alien1", 250, 0));
        spawnMap.put(23, new SpawnDetails("Alien2", 300, 0));

        spawnMap.put(400, new SpawnDetails("Alien1", 400, 0));
        spawnMap.put(511, new SpawnDetails("Alien1", 450, 0));
        spawnMap.put(622, new SpawnDetails("Alien1", 500, 0));
        spawnMap.put(733, new SpawnDetails("Alien1", 550, 0));

        spawnMap.put(734, new SpawnDetails("Alien2", 300, 0));

        spawnMap.put(810, new SpawnDetails("Alien1", 100, 0));
        spawnMap.put(921, new SpawnDetails("Alien1", 150, 0));
        spawnMap.put(1632, new SpawnDetails("Alien2", 200, 0));
        spawnMap.put(1143, new SpawnDetails("Alien1", 350, 0));
        spawnMap.put(1203, new SpawnDetails("Alien2", 350, 0));
    }

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

                // double scale = 0.8; // scale factor
                // int scaledWidth = (int)(enemy.getWidth() * scale);
                // int scaledHeight = (int)(enemy.getHeight() * scale);
                // int drawX = enemy.getX() - (scaledWidth / 2);
                // int drawY = enemy.getY() - scaledHeight;
                // g.drawImage(enemy.getImage(), drawX, drawY, scaledWidth, scaledHeight, this);
                // g.drawRect(drawX, drawY, scaledWidth, scaledHeight);
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
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
                g2d.drawImage(bossImg, 0, 0, this);

                if (true) {
                    Image bossShield = boss.getShield();
                    g2d.drawImage(bossShield, 0, 0, this);
                }

                // Restore the original transform
                g2d.setTransform(old);
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
            g.drawString("Player Frame# " + player.getFrame(), 300, 10);
            g.drawString("Health Count# " + player.getHealth(), 430, 10);
            g.drawString("Player Action# " + player.getAction(), 550, 10);
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

    private void drawBombing(Graphics g) {

        for (Enemy e : enemies) {
            if (e.getLevel() == 0) {
                continue; // Skip Alien0
            }
            Enemy.Bomb b = e.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
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
        g.drawString("FRAME: " + frame, 10, 10);
        g.drawString("Kill Count# " + deaths, 120, 10);

        g.setColor(Color.green);

        if (inGame) {

            drawMap(g);  // Draw background stars first
            drawExplosions(g);
            drawPowreUps(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawRockets(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    private void update() {

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
                default ->
                    System.out.println("Unknown enemy type: " + sd.type);
            }
        }

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        player.act();

        // Power-ups
        for (PowerUp powerup : powerups) {
            if (powerup.isVisible()) {
                powerup.act();
                if (powerup.collidesWithPlayer(player)) {
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
                            && shotX >= (enemyX)
                            && shotX <= (enemyX + ALIEN_WIDTH)
                            && shotY >= (enemyY)
                            && shotY <= (enemyY + ALIEN_HEIGHT)) {

                        shot.die();
                        shotsToRemove.add(shot);

                        if (enemy.getHealth() - shotDamage > 0) {
                            enemy.setHealth(enemy.getHealth() - shotDamage);
                            explosions.add(new Explosion(enemyX, enemyY, false));
                        } else {
                            // var ii = new ImageIcon(IMG_EXPLOSION);
                            // var ii = new ImageIcon(IMG_ENEMY);
                            // enemy.setImage(ii.getImage());
                            explosions.add(new Explosion(enemyX, enemyY, true));
                            enemy.setDying(true);
                            deaths++;
                            shot.die();
                            shotsToRemove.add(shot);
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

                        if (boss.getHealth() - shotDamage > 0) {
                            boss.setHealth(boss.getHealth() - shotDamage);
                            explosions.add(new Explosion(enemyX, enemyY, false));
                        } else {
                            // var ii = new ImageIcon(IMG_EXPLOSION);
                            // var ii = new ImageIcon(IMG_ENEMY);
                            // enemy.setImage(ii.getImage());
                            explosions.add(new Explosion(enemyX, enemyY, true));
                            boss.setDying(true);
                            deaths = deaths + 5;
                            shot.die();
                            shotsToRemove.add(shot);
                        }
                    }
                }

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
                        rocket.setDestroyed(true);
                        rocketsToRemove.add(rocket);
                        explosions.add(new Explosion(rocketX, rocketY, false));
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
                }
            }
        }
        shots.removeAll(shotsToRemove);

        // Rocket
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
                    } else {
                        var ii = new ImageIcon(IMG_EXPLOSION);
                        player.setImage(ii.getImage());
                        player.setDying(true);
                    }
                }

                // Calculate direction vector
                double dx = playerX - rocketX;
                double dy = playerY - rocketY;
                double distance = Math.sqrt(dx * dx + dy * dy);

                // Set rocket speed (pixels per frame)
                double speed = 2.0;
                // Normalize direction and update rocket position
                if (rocketY > playerY) rocket.tracking = false;

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
                // Destroy rocket if it goes out of bounds
                if (rocketY >= GROUND - BOMB_HEIGHT || rocketX < 0 || rocketX > BOARD_WIDTH) {
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
        for (Enemy enemy : enemies) {
            switch (enemy.getLevel()) {
                case 0 -> {
                    continue; // Skip Alien0
                }
                case 1 -> {
                    offsetX = 6;
                    offsetY = 12;
                    chance = randomizer.nextInt(15);
                }
                case 2 -> {
                    if (randomizer.nextBoolean()) {
                        offsetX = 2;
                        offsetY = 14;
                    } else {
                        offsetX = 13;
                        offsetY = 14;
                    }
                    chance = randomizer.nextInt(10);
                }
                default -> {
                    System.out.println("Unknown enemy level: " + enemy.getLevel());
                }
            }

            Enemy.Bomb bomb = enemy.getBomb();

            if (chance == CHANCE && enemy.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(enemy.getX() + offsetX);
                bomb.setY(enemy.getY() + offsetY);
            }

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
                int health = player.getHealth();
                if (health > 0) {
                    health--;
                    player.setHealth(health);
                    player.setInvincible(30);
                } else {
                    var ii = new ImageIcon(IMG_EXPLOSION);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                }
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 5);
                if (bomb.getY() >= GROUND - BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }

        chance = randomizer.nextInt(50);
        for (EnemyBoss boss : bosses) {

            // Rocket
            if (chance == CHANCE) {
                rockets.add(new Rocket(boss.getX(), boss.getY()));

                System.out.println("Rocket!!!!!");
            }
        }
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
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Scene2.keyPressed: " + e.getKeyCode());

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE && inGame) {
                long now = System.currentTimeMillis();
                System.out.println("Shots: " + shots.size());
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
                            Shot shot = new Shot(x - 10, y + 15);
                            shots.add(shot);
                            shot = new Shot(x, y);
                            shots.add(shot);
                            shot = new Shot(x + 10, y + 15);
                            shots.add(shot);
                            lastShotTime = now;
                        }
                        default -> {
                            Shot shot = new Shot(x, y);
                            shots.add(shot);
                            lastShotTime = now;
                        }
                    }
                }
            }

        }
    }
}
