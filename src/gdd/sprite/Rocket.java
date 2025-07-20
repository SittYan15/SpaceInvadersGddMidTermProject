package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Rocket extends Sprite{
    private static final int H_SPACE = -1;
    private static final int V_SPACE = 38;

    private boolean destroyed;
    private int frame = 0;

    public boolean tracking = true;
    public double lastDx = 0, lastDy = 0;

    public int clipsNoRocket = 2;
    public final Rectangle[] clips_rocket = new Rectangle[]{

        new Rectangle(0, 0, 10, 25), // 0
        new Rectangle(10, 0, 10, 25), // 1
        new Rectangle(20, 0, 10, 25), // 2
    };

    public Rocket() {
    }

    public Rocket(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        var ii = new ImageIcon(IMG_ROCKET1);
        setImage(ii.getImage());

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    @Override
    public Image getImage() {
       Rectangle bound = clips_rocket[clipsNoRocket];
       BufferedImage bImage = toBufferedImage(image);
       return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
   }

    @Override
    public void act() {
        // for shield animation
        if (frame > 5) {
            clipsNoRocket++;
            if (clipsNoRocket > 2) clipsNoRocket = 0;
            frame = 0;
        }
        frame++;
    }
}
