package gdd.powerup;

import static gdd.Global.*;
import gdd.sprite.Player;
import javax.swing.ImageIcon;

public class ShotSizeUp extends PowerUp {
    
    public ShotSizeUp(int x, int y) {
        super(x, y);
        // Set image
        ImageIcon ii = new ImageIcon(IMG_POWERUP_SHOTSIZEUP);
        var scaledImage = ii.getImage().getScaledInstance((int) (ii.getIconWidth() * 0.8),
                (int) (ii.getIconHeight() * 0.8),
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act() {
        // ShotSizeUp specific behavior can be added here
        this.y += 2; // Move down by 2 pixel each frame
    }

    @Override
    public void upgrade(Player player) {
        // Upgrade the player's shot size
        if (shotCooldownMillis >= 100)
            shotCooldownMillis = shotCooldownMillis - 100; // Increase player's shot size by 1
        this.die(); // Remove the power-up after use
    }

}
