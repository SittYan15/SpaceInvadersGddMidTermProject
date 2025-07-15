package gdd.sprite;

import gdd.image_clips.enemy_cilp;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Alien2 extends Enemy {

    public Alien2(int x, int y) {
        super(x, y, 5);
    }

    @Override
    public void act(int direction) {
        if (this.y < 200) {
            this.y++;
        } else {
            this.x += direction;
        }
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public Image getImage() {
        Rectangle bound = enemy_cilp.clips[4];
        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }
}
