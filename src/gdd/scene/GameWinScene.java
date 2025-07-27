package gdd.scene;

import gdd.AudioPlayer;
import gdd.Game;
import static gdd.Global.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameWinScene extends JPanel {

    private int frame = 0;
    private Image image;
    private AudioPlayer audioPlayer;
    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private Timer timer;
    private Game game;
    private List<int[]> scores;

    public GameWinScene(Game game) {
        this.game = game;
        // initBoard();
        // initTitle();
        scores = Game.loadScoresFromCSV();
    }

    private void initBoard() {

    }

    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);

        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        initTitle();
        initAudio();
    }

    public void stop() {
        try {
            if (timer != null) {
                timer.stop();
            }

            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void initTitle() {
        var ii = new ImageIcon(IMG_TITLE);
        image = ii.getImage();

    }

    private void initAudio() {
        try {
            String filePath = SOUND_BG_WIN;
            audioPlayer = new AudioPlayer(filePath);
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error with playing sound.");
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        // Background
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);

        // Title image with slight transparency and glow effect
        g.drawImage(image, 0, -80, d.width, d.height, this);

        // Animated "Press SPACE to Start" text (pulsing color and shadow)
        String text = "Press SPACE to Quit";
        g.setFont(g.getFont().deriveFont(38f));
        int stringWidth = g.getFontMetrics().stringWidth(text);
        int x = (d.width - stringWidth) / 2;
        int y = 600;

        // Shadow
        g.setColor(new Color(0, 0, 0, 120));
        g.drawString(text, x + 3, y + 3);

        // Pulsing color
        int pulse = (int)(128 + 127 * Math.sin(frame * 0.12));
        g.setColor(new Color(255, pulse, pulse));
        g.drawString(text, x, y);

        // Game credits
        g.setFont(g.getFont().deriveFont(12f));
        g.setColor(Color.gray);
        g.drawString("Game by Chayapol", 10, 650);

        // Best Scores box with neon border
        g.setColor(new Color(0, 255, 128));
        g.drawRect(5, 10, 220, 50);
        g.setFont(g.getFont().deriveFont(16f));
        g.drawString("Best Scores", 15, 30);
        g.setFont(g.getFont().deriveFont(14f));
        g.setColor(Color.white);
        g.drawString("Distance: " + scores.get(0)[0], 15, 48);
        g.drawString("Kill: " + scores.get(0)[1], 135, 48);

        // Team member box
        g.setColor(new Color(0, 128, 255));
        g.drawRect(5, 60, 220, 50);
        g.setFont(g.getFont().deriveFont(15f));
        g.drawString("Team Member", 15, 80);
        g.setFont(g.getFont().deriveFont(13f));
        g.setColor(Color.yellow);
        g.drawString("Sitt Yan Htun (6722114)", 15, 98);

        // Motivational message
        g.setFont(g.getFont().deriveFont(22f));
        g.setColor(new Color(255, 255, 0));
        g.drawString("Congratulations, Pilot!", x, 520);
        g.setFont(g.getFont().deriveFont(16f));
        g.setColor(new Color(0, 255, 255));
        g.drawString("You saved Earth from the alien threat!", x, 550);

        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
        frame++;
    }

    private void doGameCycle() {
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

        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Title.keyPressed: " + e.getKeyCode());
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                // Load the next scene
                game.loadTitle(1);
            }

        }
    }
}
