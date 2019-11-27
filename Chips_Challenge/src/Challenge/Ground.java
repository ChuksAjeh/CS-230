package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
public class Ground extends Cell {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_GROUND.png");
    }

    /**
     * Constructor
     */
    public Ground() {
        super(SPRITE, true);
    }

}