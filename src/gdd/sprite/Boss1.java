package gdd.sprite;

import static gdd.Global.*;
import gdd.image_clips.ReadCSV;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss1 extends EnemyBoss {

    private int frame = 0;
    private boolean intro = true;
    private double angle = 0;           // Current angle for circular motion
    private final int radiusX = 200;    // Horizontal radius of the circle
    private final int radiusY = 50;     // Vertical radius for y oscillation
    private final int centerY = 130;    // Center vertical position
    private final int centerX = BOARD_WIDTH / 2; // Center horizontally


    public int clipNoShield = 0;
    public int clipNoEngine = 0;
    public int clipNoDestroy = 0;
    public int clipNoPowerMode = 0;

    private final Rectangle[] clips_shield = ReadCSV.loadClipsFromCSV("src/gdd/db/boss1_shield_clips.csv");
    private final Rectangle[] clips_engine = ReadCSV.loadClipsFromCSV("src/gdd/db/boss1_engine_clips.csv");
    private final Rectangle[] clips_powerMode = ReadCSV.loadClipsFromCSV("src/gdd/db/boss1_powerMode_clips.csv");
    private final Rectangle[] clips_destroy = ReadCSV.loadClipsFromCSV("src/gdd/db/boss1_destory_clips.csv");

    public Boss1(int x, int y) {
        super(x, y, 1000, IMG_BOSS1, IMG_BOSS1_SHIELDS, IMG_BOSS1_ENGINES, IMG_BOSS1_WEAPON, IMG_BOSS1_DESTRUCTION, 10, IMG_BOSS1_POWERMODE, 100);
    }

    @Override
    public void act(int direction) {

        // for shield animation
        if (frame > 5) {
            clipNoShield++;
            clipNoEngine++;
            if (!isAlive) clipNoDestroy++;
            if (isPowerMode) clipNoPowerMode++;
            if (clipNoShield > 15) clipNoShield = 0;
            if (clipNoEngine > 11) clipNoEngine = 0;

            frame = 0;

            if (clipNoDestroy > 13) {
                clipNoDestroy = 13;
                setDying(true);
            }

            if (clipNoPowerMode > 29) {
                clipNoPowerMode = 0;
                isPowerMode = false; // Reset power mode after full cycle
                powerMode1Left = 1;
                powerMode1Right = 1;
                powerMode2Left = 1;
                powerMode2Right = 1;
            }
        }
        frame++;

        if (intro) {
            this.y += 2;
            if (this.y > centerY) {
                intro = false;
                // Calculate initial angle based on current x position for smooth transition
                angle = Math.acos((this.x - centerX) / (double)radiusX);
                if (this.x < centerX) angle = 2 * Math.PI - angle; // handle left side
            }
        } else if (isAlive) {
            // Elliptical movement: x in [-200, +200] from center, y in [-50, +50] from centerY
            angle += 0.008; // Speed of rotation (adjust as needed)
            if (angle > 2 * Math.PI) angle -= 2 * Math.PI;

            this.x = centerX + (int)(radiusX * Math.cos(angle));
            this.y = centerY + (int)(radiusY * Math.sin(angle)); // y oscillates from centerY-50 to centerY+50
        }
    }

    @Override
    public Image getImage() {

        if (!isAlive) {
            return this.getDeath();
        } else if (isPowerMode) {
            return this.getImgPowerMode();
        }
        else {
            return super.getImage();
        }
    }
    

    @Override
    public int getLevel() {
        return 10;
    }

    @Override
    public int getClipNoPowerMode() {
        return clipNoPowerMode;
    }

    public Image getImgPowerMode() {
        Rectangle bound = clips_powerMode[clipNoPowerMode];
        BufferedImage bImage = toBufferedImage(powerMode);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Image getShield() {
        Rectangle bound = clips_shield[clipNoShield];
        BufferedImage bImage = toBufferedImage(shield);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Image getEngine() {
        Rectangle bound = clips_engine[clipNoEngine];
        BufferedImage bImage = toBufferedImage(engine);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    public Image getDeath() {
        Rectangle bound = clips_destroy[clipNoDestroy];
        BufferedImage bImage = toBufferedImage(destroy);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }
}
