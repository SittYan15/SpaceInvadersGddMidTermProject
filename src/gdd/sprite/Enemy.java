package gdd.sprite;

import static gdd.Global.*;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import gdd.image_clips.enemy_cilp;
import java.awt.image.BufferedImage;

public class Enemy extends Sprite {

    private Bomb bomb;

    public Enemy(int x, int y) {

        initEnemy(x, y);
    }

    private void initEnemy(int x, int y) {

        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);

        // var ii = new ImageIcon(IMG_ENEMY);
        // // Scale the image to use the global scaling factor
        // var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
        //         ii.getIconHeight() * SCALE_FACTOR,
        //         java.awt.Image.SCALE_SMOOTH);
        // setImage(scaledImage);

        var ii = new ImageIcon(IMG_ENEMY);
        setImage(ii.getImage());

        setX(x);
        setY(y);
    }

    @Override
    public Image getImage() {
        Rectangle bound = enemy_cilp.clips[0];
        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    public void act(int direction) {

        this.x += direction;
    }

    public Bomb getBomb() {

        return bomb;
    }

    public class Bomb extends Sprite {

        private boolean destroyed;

        public Bomb(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);

            this.x = x;
            this.y = y;

            var bombImg = "src/images/bomb.png";
            var ii = new ImageIcon(bombImg);
            setImage(ii.getImage());
        }

        public void setDestroyed(boolean destroyed) {

            this.destroyed = destroyed;
        }

        public boolean isDestroyed() {

            return destroyed;
        }
    }
}
