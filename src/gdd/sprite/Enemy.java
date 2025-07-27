package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Enemy extends Sprite {

    protected boolean isAlive = true;
    protected boolean isHit = false;

    public Enemy(int x, int y, int health, String image, String engine, String destruction) {

        initEnemy(x, y, health, image, engine, destruction);
    }

    public Enemy(int x, int y, int health) {

        initEnemy(x, y, health);
    }

    private void initEnemy(int x, int y, int health, String image, String engine, String destruction) {

        this.health = health;
        this.x = x;
        this.y = y;

        var ii = new ImageIcon(image);
        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance((int) (ii.getIconWidth() * 1),
                (int) (ii.getIconHeight() * 1),
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        // For Engines
        var iiEngine = new ImageIcon(engine);
        setEngine(iiEngine.getImage());

        // For Destroy
        var iiDestroy = new ImageIcon(destruction);
        setDestroy(iiDestroy.getImage());

        setX(x);
        setY(y);
    }

    private void initEnemy(int x, int y, int health) {

        this.health = health;
        this.x = x;
        this.y = y;

        // var ii = new ImageIcon(IMG_ENEMY);
        // // Scale the image to use the global scaling factor
        // var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
        //         ii.getIconHeight() * SCALE_FACTOR,
        //         java.awt.Image.SCALE_SMOOTH);
        // setImage(scaledImage);

        var ii = new ImageIcon(IMG_ENEMY);
        setImage(ii.getImage());

        setX(x);
        setY(y);
    }

    public void act(int direction) {

        this.x += direction;
    }

    public void setIsAlive(boolean isAlive) {

        this.isAlive = isAlive;
    }

    public boolean getIsAlive() {

        return isAlive;
    }

    public void setIsHit(boolean isHit) {

        this.isHit = isHit;
    }

    public boolean getIsHit() {
        return isHit;
    }

    @Override
    public void act() {

    }
}
