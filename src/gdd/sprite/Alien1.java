package gdd.sprite;

import gdd.image_clips.enemy_cilp;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Alien1 extends Enemy {

    public int clipNo = 2;

    public Alien1(int x, int y) {
        super(x, y, 2);
    }

    @Override
    public void act(int direction) {
        this.y++;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public Image getImage() {
        Rectangle bound = enemy_cilp.clips[clipNo];
        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }
}
