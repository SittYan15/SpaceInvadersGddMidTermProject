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
    private Image lazerRay;
    private int lazerClipNo = 0;

    private int frame = 0;
    private int lazerFrame = 0;

    public static final int DIR_LEFT = 0;
    public static final int DIR_RIGHT = 1;
    private int facing = 0;

    private int clipNo = 3;

    private static final int ACT_STAND = 0; // STAND
    private static final int ACT_TURN_LEFT = 1; // TURN_LEFT
    private static final int ACT_TURN_RIGHT = 2; // TURN_RIGHT
    private static final int ACT_SPEED_UP = 3; // SPEED_UP
    private static final int ACT_SPEED_DOWN = 4; // SPEED_DOWN
    private static final int ACT_SPEED_UP_TURN_LEFT = 5; // SPEED_UP_TURN_LEFT
    private static final int ACT_SPEED_UP_TURN_RIGHT = 6; // SPEED_UP_TURN_RIGHT
    private static final int ACT_SPEED_DOWN_TURN_LEFT = 7; // SPEED_DOWN_TURN_LEFT
    private static final int ACT_SPEED_DOWN_TURN_RIGHT = 8; // SPEED_DOWN_TURN_RIGHT
    private static final int ACT_INVINCIBLE = 9; // INVINCIBLE
    private int action = ACT_STAND;

    public Player() {
        initPlayer();
    }

    private void initPlayer() {

        setHealth(10);
        setMaxHealth(10);
        setPower(0);
        setMaxPower(10);

        var ii = new ImageIcon(IMG_PLAYER);
        setImage(ii.getImage());

        var iiLazer = new ImageIcon(IMG_LASER_RAY);
        setLazerRay(iiLazer.getImage());

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

    public void setLazerRay(Image lazerRay) {
        this.lazerRay = lazerRay;
    }

    public Image getLazerRay() {
        Rectangle bound = player_clip.lazerClips[lazerClipNo];
        BufferedImage bImage = toBufferedImage(lazerRay);
        return bImage.getSubimage(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Image getImage() {
        Rectangle bound = player_clip.clips[clipNo];
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

    public void setInvincible(int frames) {
        this.invincible = true;
        this.invincibleFrames = frames;
    }

    public String getAction() {
        switch (action) {
            case ACT_STAND -> {
                return "Standing";
            }
            case ACT_TURN_LEFT -> {
                return "Turning Left";
            }
            case ACT_TURN_RIGHT -> {
                return "Turning Right";
            }
            case ACT_SPEED_UP -> {
                return "Speeding Up";
            }
            case ACT_SPEED_DOWN -> {
                return "Slowing Down";
            }
            case ACT_SPEED_UP_TURN_LEFT -> {
                return "Speeding Up and Turning Left";
            }
            case ACT_SPEED_UP_TURN_RIGHT -> {
                return "Speeding Up and Turning Right";
            }
            case ACT_SPEED_DOWN_TURN_LEFT -> {
                return "Slowing Down and Turning Left";
            }
            case ACT_SPEED_DOWN_TURN_RIGHT -> {
                return "Slowing Down and Turning Right";
            }
            default -> {
                return "Unknown Action";
            }
        }
    }

    @Override
    public void act() {

        frame++;
        lazerFrame++;

        if (lazerFrame > 4) {
            lazerClipNo++;
            if (lazerClipNo > 2) {
                lazerClipNo = 0;
            }
            lazerFrame = 0;
        }

        x += dx;
        y += dy;

        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }

        if (y <= 50) {
            y = 50;
        }

        if (y >= BOARD_HEIGHT - 20) {
            y = BOARD_HEIGHT - 20;
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
                clipNo = 5;
            }

            case ACT_SPEED_DOWN -> {
                clipNo = 6;
            }

            case ACT_SPEED_UP_TURN_LEFT -> {
                if (frame < 10) {
                    facing = DIR_LEFT;
                    clipNo = 7; // intro turning left
                } else {
                    clipNo = 8; // turning left
                }
            }

            case ACT_SPEED_DOWN_TURN_LEFT -> {
                if (frame < 10) {
                    facing = DIR_LEFT;
                    clipNo = 11; // intro turning left
                } else {
                    clipNo = 12; // turning left
                }
            }

            case ACT_SPEED_UP_TURN_RIGHT -> {
                if (frame < 10) {
                    facing = DIR_RIGHT;
                    clipNo = 9; // intro turning right
                } else {
                    clipNo = 10; // turning right
                }
            }
            
            case ACT_SPEED_DOWN_TURN_RIGHT -> {
                if (frame < 10) {
                    facing = DIR_RIGHT;
                    clipNo = 13; // intro turning right
                } else {
                    clipNo = 14; // turning right
                }
            }

            case ACT_INVINCIBLE -> {
                if (frame < 10) {
                    clipNo = 15; // Invincible state
                } else if (frame < 20) {
                    clipNo = 0;
                } else {
                    frame = 0;
                }
            }

            default -> // ACT_STAND
                clipNo = 0; // flying straight
        }

        if (invincible) {
            action = ACT_INVINCIBLE;
            this.dx = 0; // Stop movement during invincibility
            this.dy = 0; // Stop movement during invincibility
            invincibleFrames--;
            if (invincibleFrames <= 0) {
                invincible = false;
                action = ACT_STAND; // Reset to standing after invincibility ends
            }
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
                        action = ACT_SPEED_UP;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
                        action = ACT_SPEED_DOWN;
                    }
                }
            }
            case ACT_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_RIGHT -> {
                        dx = currentSpeed;
                        frame = 0;
                        action = ACT_TURN_RIGHT;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = -currentSpeed;
                        action = ACT_SPEED_UP_TURN_LEFT;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
                        action = ACT_SPEED_DOWN_TURN_LEFT;
                    }
                }
            }
            case ACT_TURN_RIGHT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = -currentSpeed;
                        frame = 0;
                        action = ACT_TURN_LEFT;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = -currentSpeed;
                        action = ACT_SPEED_UP_TURN_RIGHT;
                    }
                    case KeyEvent.VK_DOWN -> {
                        dy = currentSpeed;
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
                        action = ACT_SPEED_DOWN;
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
                        dy = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_SPEED_DOWN -> {
                switch (key) {
                    case KeyEvent.VK_DOWN -> {
                        dy = 0;
                        action = ACT_STAND;
                    }
                }
            }
            case ACT_SPEED_UP_TURN_LEFT -> {
                switch (key) {
                    case KeyEvent.VK_LEFT -> {
                        dx = 0;
                        frame = 0;
                        action = ACT_SPEED_UP;
                    }
                    case KeyEvent.VK_UP -> {
                        dy = 0;
                        action = ACT_TURN_LEFT;
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
                        action = ACT_TURN_LEFT;
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
                        action = ACT_TURN_RIGHT;
                    }
                }
            }
        }
    }
}
