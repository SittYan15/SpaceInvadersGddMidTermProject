package gdd.sprite;

import static gdd.Global.IMG_BOSS1;
import static gdd.Global.IMG_BOSS1_ENGINES;
import static gdd.Global.IMG_BOSS1_SHIELDS;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss1 extends EnemyBoss {

    private int frame = 0;

    public int clipNoShield = 15;
    public int clipNoEngine = 0;

    private final Rectangle[] clips_shield = new Rectangle[]{

        new Rectangle(0,0,110,80), // 0 
        new Rectangle(128,0,110,80), // 1 
        new Rectangle(256,0,110,80), // 2 
        new Rectangle(384,0,110,80), // 3 
        new Rectangle(512,0,110,80), // 4 
        new Rectangle(640,0,110,80), // 5 
        new Rectangle(768,0,110,80), // 6 
        new Rectangle(896,0,110,80), // 7 
        new Rectangle(1024,0,110,80), // 8 
        new Rectangle(1152,0,110,80), // 9 
        new Rectangle(1280,0,110,80), // 10 
        new Rectangle(1408,0,110,80), // 11
        new Rectangle(1536,0,110,80), // 12
        new Rectangle(1664,0,110,80), // 13
        new Rectangle(1792,0,110,80), // 14
        new Rectangle(1920,0,110,80), // 15
    };

    public final Rectangle[] clips_engine = new Rectangle[]{

        new Rectangle(10, 3, 18, 25), //
    };

    public Boss1(int x, int y) {
        super(x, y, 20, IMG_BOSS1, IMG_BOSS1_SHIELDS, IMG_BOSS1_ENGINES);
    }

    @Override
    public void act(int direction) {

        // for shield animation
        if (frame > 5) {
            clipNoShield++;
            if (clipNoShield > 15) clipNoShield = 0;
            frame = 0;
        }
        frame++;

        // this.y++;
    }

    @Override
    public int getLevel() {
        return 10;
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
}
