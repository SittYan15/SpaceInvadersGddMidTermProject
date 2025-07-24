package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss1 extends EnemyBoss {

    private int frame = 0;

    public int clipNoShield = 0;
    public int clipNoEngine = 0;
    public int clipNoDestroy = 0;
    public int clipNoPowerMode = 0;
    
    private final Rectangle[] clips_shield = new Rectangle[]{
        new Rectangle(0, 0, 110, 80), // 0 
        new Rectangle(128, 0, 110, 80), // 1 
        new Rectangle(256, 0, 110, 80), // 2 
        new Rectangle(384, 0, 110, 80), // 3 
        new Rectangle(512, 0, 110, 80), // 4 
        new Rectangle(640, 0, 110, 80), // 5 
        new Rectangle(768, 0, 110, 80), // 6 
        new Rectangle(896, 0, 110, 80), // 7 
        new Rectangle(1024, 0, 110, 80), // 8 
        new Rectangle(1152, 0, 110, 80), // 9 
        new Rectangle(1280, 0, 110, 80), // 10 
        new Rectangle(1408, 0, 110, 80), // 11
        new Rectangle(1536, 0, 110, 80), // 12
        new Rectangle(1664, 0, 110, 80), // 13
        new Rectangle(1792, 0, 110, 80), // 14
        new Rectangle(1920, 0, 110, 80), // 15
    };

    public final Rectangle[] clips_engine = new Rectangle[]{
        new Rectangle(0, 0, 110, 128), // 0 
        new Rectangle(128, 0, 110, 128), // 1 
        new Rectangle(256, 0, 110, 128), // 2 
        new Rectangle(384, 0, 110, 128), // 3 
        new Rectangle(512, 0, 110, 128), // 4 
        new Rectangle(640, 0, 110, 128), // 5 
        new Rectangle(768, 0, 110, 128), // 6 
        new Rectangle(896, 0, 110, 128), // 7 
        new Rectangle(1024, 0, 110, 128), // 8 
        new Rectangle(1152, 0, 110, 128), // 9 
        new Rectangle(1280, 0, 110, 128), // 10 
        new Rectangle(1408, 0, 110, 128), // 11
    };

    public final Rectangle[] clips_powerMode = new Rectangle[] {
        new Rectangle(0, 0, 110, 128),    // 0 
        new Rectangle(128, 0, 110, 128),  // 1 
        new Rectangle(256, 0, 110, 128),  // 2 
        new Rectangle(384, 0, 110, 128),  // 3 
        new Rectangle(512, 0, 110, 128),  // 4 
        new Rectangle(640, 0, 110, 128),  // 5 
        new Rectangle(768, 0, 110, 128),  // 6 
        new Rectangle(896, 0, 110, 128),  // 7 
        new Rectangle(1024, 0, 110, 128), // 8 
        new Rectangle(1152, 0, 110, 128), // 9 
        new Rectangle(1280, 0, 110, 128), // 10 
        new Rectangle(1408, 0, 110, 128), // 11
        new Rectangle(1536, 0, 110, 128), // 12
        new Rectangle(1664, 0, 110, 128), // 13
        new Rectangle(1792, 0, 110, 128), // 14
        new Rectangle(1920, 0, 110, 128), // 15
        new Rectangle(2048, 0, 110, 128), // 16
        new Rectangle(2176, 0, 110, 128), // 17
        new Rectangle(2304, 0, 110, 128), // 18
        new Rectangle(2432, 0, 110, 128), // 19
        new Rectangle(2560, 0, 110, 128), // 20
        new Rectangle(2688, 0, 110, 128), // 21
        new Rectangle(2816, 0, 110, 128), // 22
        new Rectangle(2944, 0, 110, 128), // 23
        new Rectangle(3072, 0, 110, 128), // 24
        new Rectangle(3200, 0, 110, 128), // 25
        new Rectangle(3328, 0, 110, 128), // 26
        new Rectangle(3456, 0, 110, 128), // 27
        new Rectangle(3584, 0, 110, 128), // 28
        new Rectangle(3712, 0, 110, 128), // 29
    };

    public final Rectangle[] clips_destroy = new Rectangle[]{
        new Rectangle(0, 0, 110, 128), // 0 
        new Rectangle(128, 0, 110, 128), // 1 
        new Rectangle(256, 0, 110, 128), // 2 
        new Rectangle(384, 0, 110, 128), // 3 
        new Rectangle(512, 0, 110, 128), // 4 
        new Rectangle(640, 0, 110, 128), // 5 
        new Rectangle(768, 0, 110, 128), // 6 
        new Rectangle(896, 0, 110, 128), // 7 
        new Rectangle(1024, 0, 110, 128), // 8 
        new Rectangle(1152, 0, 110, 128), // 9 
        new Rectangle(1280, 0, 110, 128), // 10 
        new Rectangle(1408, 0, 110, 128), // 11
        new Rectangle(1536, 0, 110, 128),// 12
        new Rectangle(1664, 0, 110, 128),// 13
    };

    public Boss1(int x, int y) {
        super(x, y, 20, IMG_BOSS1, IMG_BOSS1_SHIELDS, IMG_BOSS1_ENGINES, IMG_BOSS1_WEAPON, IMG_BOSS1_DESTRUCTION, 2, IMG_BOSS1_POWERMODE, 1);
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

        if (!(this.y > 130)) {
            this.y = this.y + 2;
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
