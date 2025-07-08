package gdd.sprite;

import static gdd.Global.*;
import gdd.image_clips.player_clip;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Player extends Sprite {

    private static final int START_X = 270;
    private static final int START_Y = 540;
    private int width;
    private int currentSpeed = 2;

    private int frame = 0;

    public static final int DIR_LEFT = 0;
    public static final int DIR_RIGHT = 1;
    private int facing = 0;

    private int clipNo = 3;

    private static final String ACT_STAND = "STAND";
    private static final String ACT_TURN_LEFT = "TURN_LEFT";
    private static final String ACT_TURN_RIGHT = "TURN_RIGHT";
    private static final String ACT_SPEED_UP = "SPEED_UP";
    private static final String ACT_SPEED_DOWN = "SPEED_DOWN";
    private static final String ACT_SPEED_UP_TURN_LEFT = "SPEED_UP_TURN_LEFT";
    private static final String ACT_SPEED_UP_TURN_RIGHT = "SPEED_UP_TURN_RIGHT";
    private static final String ACT_SPEED_DOWN_TURN_LEFT = "SPEED_DOWN_TURN_LEFT";
    private static final String ACT_SPEED_DOWN_TURN_RIGHT = "SPEED_DOWN_TURN_RIGHT";
    private String action = ACT_STAND;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        // var ii = new ImageIcon(IMG_PLAYER);

        // // Scale the image to use the global scaling factor
        // var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
        //         ii.getIconHeight() * SCALE_FACTOR,
        //         java.awt.Image.SCALE_SMOOTH);
        // setImage(scaledImage);
        var ii = new ImageIcon(IMG_PLAYER);
        setImage(ii.getImage());

        setX(START_X);
        setY(START_Y);
    }

    public int getFrame() {
        return frame;
    }

    public int getFacing() {
        return facing;
    }

    @Override
    public int getHeight() {
        return player_clip.clips[clipNo].height;
    }

    @Override
    public int getWidth() {
        return player_clip.clips[clipNo].width;
    }

    @Override
    public Image getImage() {
        Rectangle bound = player_clip.clips[clipNo];
        // TODO this can be cached.
        BufferedImage bImage = toBufferedImage(image);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    public int getSpeed() {
        return currentSpeed;
    }

    public int setSpeed(int speed) {
        if (speed < 1) {
            speed = 1; // Ensure speed is at least 1
        }
        this.currentSpeed = speed;
        return currentSpeed;
    }

    public String getAction() {
        return action;
    }

    @Override
    public void act() {

        frame++;

        x += dx;

        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }

        // animation logic
        switch (action) {
            case ACT_TURN_LEFT -> {
                if (frame < 10) {
                    facing = DIR_LEFT;
                    clipNo = 1; // intro turning left
                } else {
                    clipNo = 2; // turning left
                }
            }

            case ACT_TURN_RIGHT -> {
                if (frame < 10) {
                    facing = DIR_RIGHT;
                    clipNo = 3; // intro turning right
                } else {
                    clipNo = 4; // turning right
                }
            }

            case ACT_SPEED_UP -> {
                currentSpeed++;
                action = ACT_STAND;
            }

            case ACT_SPEED_DOWN -> {
                currentSpeed--;
                if (currentSpeed < 1) {
                    currentSpeed = 1;
                }
                action = ACT_STAND;
            }

            default -> // ACT_STAND
                clipNo = 0; // flying straight
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (action) {
            case ACT_STAND -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = -currentSpeed;
                        frame = 0;
                        action = ACT_TURN_LEFT;
                    }
                    case KeyEvent.VK_RIGHT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_TURN_RIGHT;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN;
                    }
                }
            }
            case ACT_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {

                        action = ACT_TURN_RIGHT;
                    }
                    case KeyEvent.VK_UP -> {

                        action = ACT_SPEED_UP_TURN_LEFT;
                    }
                    case KeyEvent.VK_DOWN -> {

                        action = ACT_SPEED_DOWN_TURN_LEFT;
                    }
                }
            }
            case ACT_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {

                        action = ACT_TURN_LEFT;
                    }
                    case KeyEvent.VK_UP -> {

                        action = ACT_SPEED_UP_TURN_RIGHT;
                    }
                    case KeyEvent.VK_DOWN -> {

                        action = ACT_SPEED_DOWN_TURN_RIGHT;
                    }
                }
            }
            case ACT_SPEED_UP -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_LEFT;
                    }
                    case KeyEvent.VK_RIGHT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_RIGHT;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_RIGHT;
                    }
                }
            }
            case ACT_SPEED_DOWN -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_LEFT;
                    }
                    case KeyEvent.VK_RIGHT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_RIGHT;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_RIGHT;
                    }
                }
            }
            case ACT_SPEED_UP_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_RIGHT;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_LEFT;
                    }
                }
            }
            case ACT_SPEED_UP_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_LEFT;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_RIGHT;
                    }
                }
            }
            case ACT_SPEED_DOWN_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_RIGHT;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_LEFT;
                    }
                }
            }
            case ACT_SPEED_DOWN_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_DOWN_TURN_LEFT;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = -currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP_TURN_RIGHT;
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        switch (action) {
            case ACT_STAND -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                    case KeyEvent.VK_RIGHT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_SPEED_UP -> {
                switch (key) {
                    case KeyEvent.VK_UP -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_SPEED_DOWN -> {
                switch (key) {
                    case KeyEvent.VK_DOWN -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_SPEED_UP_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_UP -> {
                        dy = 0;
                        action = ACT_TURN_LEFT;
                    }
                    case KeyEvent.VK_LEFT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_SPEED_UP;
                    }
                }
            }
            case ACT_SPEED_UP_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_SPEED_UP;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = 0;
                        frame = 0;
                        action = ACT_TURN_RIGHT;
                    }
                }
            }
            case ACT_SPEED_DOWN_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_SPEED_DOWN;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = 0;
                        frame = 0;
                        action = ACT_SPEED_DOWN;
                    }
                }
            }
            case ACT_SPEED_DOWN_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_SPEED_DOWN;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = 0;
                        frame = 0;
                        action = ACT_TURN_RIGHT;
                    }
                }
            }
        }
    }
}
