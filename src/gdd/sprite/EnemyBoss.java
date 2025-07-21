package gdd.sprite;

import javax.swing.ImageIcon;

public class EnemyBoss extends Sprite{

    protected boolean shielded = false;
    protected int maxShieldHealth;
    protected int shieldHealth;
    protected int shieldCount;
    protected boolean deathShowned = false;
    protected boolean deathShownedFinished = false;
    protected boolean isAlive = true;

    public EnemyBoss(int x, int y, int health, String img, String shields, String engines, String weapon, String destroy, int shieldCount) {

        initEnemyBoss(x, y, health, img, shields, engines, weapon, destroy, shieldCount);
    }

    private void initEnemyBoss(int x, int y, int health, String img, String shields, String engines, String weapon, String destroy, int shieldCount) {

        this.health = health;
        this.maxHealth = health;
        this.x = x;
        this.y = y;
        this.shieldCount = shieldCount;
        this.shieldHealth = this.maxShieldHealth = 15;

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

        // For Weapons
        var iiWeapon = new ImageIcon(weapon);
        setWeapon(iiWeapon.getImage());

        // For Destroy
        var iiDestroy = new ImageIcon(destroy);
        setDestroy(iiDestroy.getImage());

        setX(x);
        setY(y);
    }

    public void setShielded(boolean shielded) {
        if (shieldCount > 0 && shielded) {
            this.shielded = shielded;
            this.shieldCount--;
            this.setHealth(this.getHealth() + 5);
        } else {
            this.shielded = false;
        }
    }

    public boolean getShielded() {
        return shielded;
    }

    public void setShieldHealth(int shieldHealth) {
        this.shieldHealth = shieldHealth;
    }

    public int getShieldHealth() {
        return shieldHealth;
    }

    public int getMaxShieldHealth() {
        return maxShieldHealth;
    }

    public void setShieldCount(int shieldCount) {
        this.shieldCount = shieldCount;
    }

    public void setdeathShowed(boolean deathShowned) {
        this.deathShowned = deathShowned;
    }

    public boolean getdeathShowed() {
        return deathShowned;
    }

    public int getShieldCount() {
        return shieldCount;
    }

    public void setisAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean getisAlive() {
        return isAlive;
    }

    @Override
    public void act() {
    
    }

    public void act(int direction) {

        this.x += direction;
    }
}
