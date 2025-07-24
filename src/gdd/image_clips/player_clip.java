package gdd.image_clips;

import java.awt.Rectangle;

public class player_clip {

    public static final Rectangle[] clips = new Rectangle[]{

        new Rectangle(245, 35, 20, 38), // 0: Idle
        
        new Rectangle(23, 35, 20, 38), // 1: intro Turing Left
        new Rectangle(3, 35, 20, 38), // 2: Turing Left

        new Rectangle(66, 35, 20, 38), // 3: intro Turing Right
        new Rectangle(85, 35, 20, 38), // 4: Turing Right

        new Rectangle(45, 35, 20, 38), // 5: Speed Up...

        new Rectangle(306, 35, 20, 38), // 6: Speed Down...

        new Rectangle(285, 35, 20, 38), // 7: Speed up, intro Turing Left
        new Rectangle(264, 35, 20, 38), // 8: Speed up, Turing Left
        new Rectangle(327, 35, 20, 38), // 9: Speed up, intro Turing Right
        new Rectangle(346, 35, 20, 38), // 10: Speed up, Turing Right

        new Rectangle(284, 73, 20, 38), // 11: Speed down, intro Turing Left
        new Rectangle(264, 73, 20, 38), // 12: Speed down, Turing Left
        new Rectangle(328, 73, 20, 38), // 13: Speed down, intro Turing Right
        new Rectangle(346, 73, 20, 38), // 14: Speed down, Turing Right

        new Rectangle(121, 35, 20, 38), // 15: Player Invincible

    };

    public static final Rectangle[] lazerClips = new Rectangle[] {
        new Rectangle(0, 0, 20, 38),
        new Rectangle(20, 0, 20, 38),
        new Rectangle(40, 0, 20, 38),
    };

}
