package gdd.sprite;

import gdd.image_clips.enemy_cilp;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Alien0 extends Enemy {

    public Alien0(int x, int y) {
        super(x, y, 1);
    }

    public void act(int direction) {
        this.y++;
    }

    public int getLevel() {
        return 0;
    }

    @Override
    public Image getImage() {
        Rectangle bound = enemy_cilp.clips[0];
        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }
}
