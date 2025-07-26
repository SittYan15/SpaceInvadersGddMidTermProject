package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private static final int H_SPACE = -1;
    private static final int V_SPACE = 38;
    private int damage;
    private boolean isSided = false;
    private int isLeft = 0;

    public Shot() {
    }

    public Shot(int x, int y) {

        damage = shotDamage; // Initialize damage with the global shot damage
        initShot(x, y);
    }

    public Shot(int x, int y, boolean isSided, int isLeft) {

        this.isSided = isSided;
        this.isLeft = isLeft;
        damage = shotDamage; // Initialize damage with the global shot damage
        initShot(x, y);
    }

    private void initShot(int x, int y) {

        var ii = new ImageIcon(IMG_SHOT);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance((int) (ii.getIconWidth() * 1.3),
                (int) (ii.getIconHeight() * 1.3), 
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isSided() {
        return isSided;
    }

    public int isLeft() {
        return isLeft;
    }

    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
