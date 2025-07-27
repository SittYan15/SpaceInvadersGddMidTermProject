package gdd.sprite;

import static gdd.Global.GROUND;
import static gdd.Global.IMG_ALIEN0;
import static gdd.Global.IMG_ALIEN0_DESTRUCTION;
import static gdd.Global.IMG_ALIEN0_ENGINES;
import gdd.image_clips.ReadCSV;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Alien0 extends Enemy {

    public int clipNo = 0;

    private int frame = 0;

    private int clipNoEngine = 0;
    private int clipNoDestroy = 0;

    private final Rectangle[] clips = ReadCSV.loadClipsFromCSV("src/gdd/db/enemy0_clips.csv");

    public Alien0(int x, int y) {
        super(x, y, 1, IMG_ALIEN0, IMG_ALIEN0_ENGINES, IMG_ALIEN0_DESTRUCTION);
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

        if (this.y > GROUND) {
            this.y = GROUND;
            setDying(true);
        }
    }

    @Override
    public int getLevel() {
        return 0;
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
        Rectangle bound = clips[clipNoDestroy];
        BufferedImage bImage = toBufferedImage(destroy);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Image getEngine() {
        Rectangle bound = clips[clipNoEngine];
        BufferedImage bImage = toBufferedImage(engine);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }
}
