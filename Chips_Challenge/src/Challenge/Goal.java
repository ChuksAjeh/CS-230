package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Goal extends Cell {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_GOAL.png");
    }

//
//            ___---___
//         .--         --.
//       ./   ()      .-. \.
//      /   o    .   (   )  \
//     / .            '-'    \
//    | ()    .  O         .  |
//   |                         |
//   |    o           ()       |
//   |       .--.          O   |
//    | .   |    |            |
//     \    `.__.'    o   .  /
//      \                   /
//       `\  o    ()      /'
//         `--___   ___--'
//               ---
//
//  Figure IV - The real goal
//  (it is also made of cheese)
//

    /**
     * Constructor
     */
    Goal() {
        super(SPRITE, true);
    }

}