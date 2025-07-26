package gdd.sprite;

import static gdd.Global.IMG_ALIEN1;
import static gdd.Global.IMG_ALIEN1_DESTRUCTION;
import static gdd.Global.IMG_ALIEN1_ENGINES;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Alien1 extends Enemy {

    public int clipNo = 2;

    private int frame = 0;

    private int clipNoEngine = 0;
    private int clipNoDestroy = 0;

    private final Rectangle[] clips_engine = new Rectangle[]{
        new Rectangle(0, 0, 64, 64), // 0 
        new Rectangle(64, 0, 64, 64), // 1 
        new Rectangle(128, 0, 64, 64), // 2 
        new Rectangle(192, 0, 64, 64), // 3 
        new Rectangle(256, 0, 64, 64), // 4 
        new Rectangle(320, 0, 64, 64), // 5 
        new Rectangle(384, 0, 64, 64), // 6 
        new Rectangle(448, 0, 64, 64), // 7 
        new Rectangle(512, 0, 64, 64), // 8
        new Rectangle(576, 0, 64, 64), // 9 
    };

    private final Rectangle[] clips_destroy = new Rectangle[] {
        new Rectangle(0, 0, 64, 64), // 0 
        new Rectangle(64, 0, 64, 64), // 1 
        new Rectangle(128, 0, 64, 64), // 2 
        new Rectangle(192, 0, 64, 64), // 3 
        new Rectangle(256, 0, 64, 64), // 4 
        new Rectangle(320, 0, 64, 64), // 5 
        new Rectangle(384, 0, 64, 64), // 6 
        new Rectangle(448, 0, 64, 64), // 7 
        new Rectangle(512, 0, 64, 64), // 8
        new Rectangle(576, 0, 64, 64), // 9 
    };


    public Alien1(int x, int y) {
        super(x, y, 2, IMG_ALIEN1, IMG_ALIEN1_ENGINES, IMG_ALIEN1_DESTRUCTION);
    }

    @Override
    public void act(int direction) {
        this.y++;

        frame++;
        if (frame >= 5) {
            frame = 0;
            clipNoEngine++;
            if (!isAlive) clipNoDestroy++;
        }

        if (clipNoEngine > 9) clipNoEngine = 0;
        if (clipNoDestroy > 9) {
            clipNoDestroy = 9;
            setDying(true);
        }
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public Image getImage() {

        if (isAlive) {
            return super.getImage();
        }
        else {
            return this.getDeath();
        }
    }

    public Image getDeath() {
        Rectangle bound = clips_destroy[clipNoDestroy];
        BufferedImage bImage = toBufferedImage(destroy);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Image getEngine() {
        Rectangle bound = clips_engine[clipNoEngine];
        BufferedImage bImage = toBufferedImage(engine);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }
}
