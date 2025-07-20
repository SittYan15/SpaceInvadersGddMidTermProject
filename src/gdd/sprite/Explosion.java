package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Explosion extends Sprite {

    public final Rectangle[] clips = new Rectangle[]{

        new Rectangle(26, 140, 20, 17), // 0 -> big explosion
        new Rectangle(26, 125, 20, 17), // 1 -> small explosion

    };
    public int clipNo = 0;
    public boolean isBig = false;
    
    private int frame = 0;

    public Explosion(int x, int y, boolean isBig) {

        this.isBig = isBig;
        initExplosion(x, y);
    }

    private void initExplosion(int x, int y) {

        this.x = x;
        this.y = y;

        // var ii = new ImageIcon(IMG_EXPLOSION);

        // Scale the image to use the global scaling factor
        // var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
        //         ii.getIconHeight() * SCALE_FACTOR,
        //         java.awt.Image.SCALE_SMOOTH);
        // setImage(scaledImage);

        var ii = new ImageIcon(IMG_PLAYER);
        setImage(ii.getImage());

        setX(x);
        setY(y);
    }

    @Override
    public int getHeight() {
        return clips[clipNo].height;
    }

    @Override
    public int getWidth() {
        return clips[clipNo].width;
    }

    @Override
    public Image getImage() {
        Rectangle bound = clips[clipNo];

        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    public void act(int direction) {
        this.x += direction;
    }

    @Override
    public void act() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'act'");
    }
}
