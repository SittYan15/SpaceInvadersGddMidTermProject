package gdd.sprite;

import javax.swing.ImageIcon;

public class EnemyBoss extends Sprite{

    protected boolean shielded = false;

    public EnemyBoss(int x, int y, int health, String img, String shields, String engines) {

        initEnemyBoss(x, y, health, img, shields, engines);
    }

    private void initEnemyBoss(int x, int y, int health, String img, String shields, String engines) {

        this.health = health;
        this.x = x;
        this.y = y;

        var ii = new ImageIcon(img);
        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth(),
                ii.getIconHeight(),
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        // For Shields
        var iiShield = new ImageIcon(shields);
        setShield(iiShield.getImage());

        // For Engines
        var iiEngine = new ImageIcon(engines);
        setEngine(iiEngine.getImage());

        setX(x);
        setY(y);
    }

    @Override
    public void act() {

    }

    public void act(int direction) {

        this.x += direction;
    }
}
