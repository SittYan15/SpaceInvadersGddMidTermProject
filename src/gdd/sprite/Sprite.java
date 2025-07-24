package gdd.sprite;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

abstract public class Sprite {

    protected boolean visible;
    
    protected Image image;
    protected Image shield;
    protected Image engine;
    protected Image weapon;
    protected Image destroy;
    protected Image powerMode;

    protected int health;
    protected int maxHealth;
    protected double power;
    protected int maxPower;
    protected boolean dying;
    protected boolean invincible;
    protected int invincibleFrames;
    protected int visibleFrames = 10;

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;

    protected int level;

    public Sprite() {
        visible = true;
    }

    abstract public void act();

    public boolean collidesWith(Sprite other) {
        if (other == null || !this.isVisible() || !other.isVisible()) {
            return false;
        }
        return this.getX() < other.getX() + other.getImage().getWidth(null)
                && this.getX() + this.getImage().getWidth(null) > other.getX()
                && this.getY() < other.getY() + other.getImage().getHeight(null)
                && this.getY() + this.getImage().getHeight(null) > other.getY();
    }

    public boolean collidesWithPlayer(Sprite player) {
        if (player == null || !this.isVisible() || !player.isVisible()) {
            return false;
        }
        return this.getX() < player.getX() + 8
                && this.getX() + 20 > player.getX() - 8
                && this.getY() < player.getY()
                && this.getY() + 20 > player.getY() - 40;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void visibleCountDown() {
        if (visibleFrames > 0) {
            visibleFrames--;
        } else {
            visible = false;
        }
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setShield(Image shield) {
        this.shield = shield;
    }

    public Image getShield() {
        return shield;
    }

    public void setEngine(Image engine) {
        this.engine = engine;
    }

    public Image getEngine() {
        return engine;
    }

    public void setPowerMode(Image powerMode) {
        this.powerMode = powerMode;
    }

    public Image getPowerMode() {
        return powerMode;
    }

    public void setWeapon(Image weapon) {
        this.weapon = weapon;
    }

    public Image getWeapon() {
        return weapon;
    }

    public void setDestroy(Image destroy) {
        this.destroy = destroy;
    }

    public Image getDestroy() {
        return destroy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getLevel() {
        return level;
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHeath() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getPower() {
        return power;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public boolean isDying() {
        return this.dying;
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage bImage) {
            return bImage;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
