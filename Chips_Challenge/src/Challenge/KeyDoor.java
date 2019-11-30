package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * @author George Carpenter
 * @version 1.0
 */
class KeyDoor extends Door {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    /**
     * The colour of the door
     */
    private final Color colour;

    static {
        SPRITE = new Image("images/CELL_KEY_DOOR.png");
    }

    /**
     * Constructor
     * @param colour the colour of the door
     */
    KeyDoor(Color colour) {
        super(SPRITE, false);
        this.colour = colour;
    }

    /**
     * Gets the colour of the door
     * @return the colour of the door
     */
    public Color getColour() {
        return this.colour;
    }

}