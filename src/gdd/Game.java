package gdd;

import static gdd.Global.gunCount;
import static gdd.Global.shotCooldownMillis;
import static gdd.Global.shotDamage;
import gdd.scene.GameOverScene;
import gdd.scene.Scene1;
import gdd.scene.Scene2;
import gdd.scene.TitleScene;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;


public class Game extends JFrame  {

    TitleScene titleScene;
    GameOverScene gameOverScene;
    Scene1 scene1;
    Scene2 scene2;

    public Game() {
        titleScene = new TitleScene(this);
        gameOverScene = new GameOverScene(this);
        scene1 = new Scene1(this);
        scene2 = new Scene2(this);
        initUI();
        loadTitle();
        // loadScene2();
    }

    private void initUI() {

        setTitle("Space Invaders");
        setSize(Global.BOARD_WIDTH, Global.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

    }

    public void loadTitle() {
        getContentPane().removeAll();
        // add(new Title(this));
        add(titleScene);
        titleScene.start();
        revalidate();
        repaint();
    }

    public void loadGameOver(int state) {
        getContentPane().removeAll();
        // add(new Title(this));
        add(gameOverScene);

        if (state == 1) scene1.stop();
        else if (state == 2) scene2.stop();

        gameOverScene.start();
        revalidate();
        repaint();
    }

    // Game Scene 1
    public void loadScene1(int state) {
        getContentPane().removeAll();

        if (state == 0) titleScene.stop();
        else if (state == 1) {
            gameOverScene.stop();
            scene1 = new Scene1(this);
            shotDamage = 1;
            gunCount = 1;
            shotCooldownMillis = 500;
        }

        add(scene1);
        scene1.start();
        scene1.setFocusable(true);
        scene1.requestFocusInWindow();
        revalidate();
        repaint();
    }

    // Game Scene 2
    public void loadScene2() {
        getContentPane().removeAll();
        add(scene2);
        scene1.stop();
        scene2.start();
        revalidate();
        repaint();
    }

    public static void saveScoreToCSV(int score, int killedEnemies) {
        String filePath = "src/gdd/db/player_scores.csv";
        int maxDeaths = 0;

        // Read current max deaths from file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int deaths = Integer.parseInt(parts[1].trim());
                    if (deaths > maxDeaths) {
                        maxDeaths = deaths;
                    }
                }
            }
        } catch (IOException e) {
            // File might not exist yet, that's fine
        }

        // Only write if killedEnemies is greater than maxDeaths
        if (killedEnemies > maxDeaths) {
            try (FileWriter writer = new FileWriter(filePath, false)) { // false for overwrite mode
                writer.append(String.valueOf(score))
                    .append(',')
                    .append(String.valueOf(killedEnemies))
                    .append('\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<int[]> loadScoresFromCSV() {
        String filePath = "src/gdd/db/player_scores.csv";
        List<int[]> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        int score = Integer.parseInt(parts[0].trim());
                        int killedEnemies = Integer.parseInt(parts[1].trim());
                        scores.add(new int[]{score, killedEnemies});
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}