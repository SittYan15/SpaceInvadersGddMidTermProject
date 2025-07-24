package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class LazerRay extends Sprite {

    private static final int H_SPACE = -1;
    private static final int V_SPACE = 38;

    private int lazerClipNo = 0;
    private int lazerFrame = 0;
    
    private final Rectangle[] lazerClips = new Rectangle[] {
        new Rectangle(0, 0, 20, 38),
        new Rectangle(20, 0, 20, 38),
        new Rectangle(40, 0, 20, 38),
    };

    public LazerRay() {

    }

    public LazerRay(int x, int y) {

        initShot(x, y);
    }

    private void initShot(int x, int y) {

        var iiLazer = new ImageIcon(IMG_LASER_RAY);
        setImage(iiLazer.getImage());

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    @Override
    public Image getImage() {
        Rectangle bound = lazerClips[lazerClipNo];
        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public void act() {

        lazerFrame++;
        if (lazerFrame > 4) {
            lazerClipNo++;
            if (lazerClipNo > 2) lazerClipNo = 0;
            lazerFrame = 0;
        }
    }
}
