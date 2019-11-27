package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
public class Goal extends Cell {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_GOAL.png");
    }

    /**
     * Constructor
     */
    public Goal() {
        super(SPRITE, true);
    }

}