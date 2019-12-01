package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Fire extends Obstacle {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_FIRE.png");
    }

    /**
     * Constructor
     */
    Fire() {
        super(SPRITE, false);
    }

}