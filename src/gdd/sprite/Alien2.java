package gdd.sprite;

import static gdd.Global.GROUND;
import static gdd.Global.IMG_ALIEN2;
import static gdd.Global.IMG_ALIEN2_DESTRUCTION;
import static gdd.Global.IMG_ALIEN2_ENGINES;
import gdd.image_clips.ReadCSV;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Alien2 extends Enemy {

    public int clipNo = 4;

    private final Random randomizer = new Random();

    private int frame = 0;

    private int clipNoEngine = 0;
    private int clipNoDestroy = 0;

    private double orbitAngle = 0; // Angle for orbiting
    private final int orbitRadius = 100; // Distance from boss

    private final Rectangle[] clips = ReadCSV.loadClipsFromCSV("src/gdd/db/enemy0_clips.csv");
    
    public Alien2(int x, int y) {
        super(x, y, 5, IMG_ALIEN2, IMG_ALIEN2_ENGINES, IMG_ALIEN2_DESTRUCTION);
    }

    @Override
    public void act(int direction) {
        if (this.y < 200) {
            this.y++;
        } else {
            this.x += direction;
        }

        frame++;
        if (frame >= 5) {
            frame = 0;
            clipNoEngine++;
            if (!isAlive) clipNoDestroy++;
        }

        if (clipNoEngine > 9) clipNoEngine = 0;
        if (clipNoDestroy > 8) {
            clipNoDestroy = 8;
            setDying(true);
        }
    }

    public void act(int direction, int bossX, int bossY) {
        // Orbit around the boss
        orbitAngle += 0.02; // Adjust for orbit speed
        if (orbitAngle > 2 * Math.PI) orbitAngle -= 2 * Math.PI;

        this.x = bossX + (int)(orbitRadius * Math.cos(orbitAngle));
        this.y = bossY + (int)(orbitRadius * Math.sin(orbitAngle));

        frame++;
        if (frame >= 5) {
            frame = 0;
            clipNoEngine++;
            if (!isAlive) clipNoDestroy++;
        }

        if (clipNoEngine > 9) clipNoEngine = 0;
        if (clipNoDestroy > 8) {
            clipNoDestroy = 8;
            setDying(true);
        }

        if (this.y > GROUND) {
            this.y = GROUND;
            setDying(true);
        }
    }

    @Override
    public int getLevel() {
        return 2;
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
