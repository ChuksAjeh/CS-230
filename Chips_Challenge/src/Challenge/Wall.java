package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
public class Wall extends Impassable {

    /**
     * The sprite used to represent a wall in the game
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_WALL.png");
    }

    /**
     * Constructor
     */
    public Wall() {
        super(SPRITE, false);
    }

}