package gdd.powerup;

import static gdd.Global.*;
import gdd.sprite.Player;
import javax.swing.ImageIcon;

public class MultiShotUp extends PowerUp {

    public MultiShotUp(int x, int y) {
        super(x, y);
        // Set image
        ImageIcon ii = new ImageIcon(IMG_POWERUP_MULTISHOTUP);
        var scaledImage = ii.getImage().getScaledInstance((int) (ii.getIconWidth() * SCALE_DOWN_FACTOR),
                (int) (ii.getIconHeight() * SCALE_DOWN_FACTOR),
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        // HealthUp specific behavior can be added here
        // For now, it just moves down the screen
        this.y += 2; // Move down by 2 pixel each frame
    }

    @Override
    public void upgrade(Player player) {
        gunCount++;
        this.die(); // Remove the power-up after use
    }

}
