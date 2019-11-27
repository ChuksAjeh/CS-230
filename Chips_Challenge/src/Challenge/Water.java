package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
public class Water extends Obstacle {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_WATER.png");
    }

    /**
     * Constructor
     */
    public Water() {
        super(SPRITE, false);
    }

}