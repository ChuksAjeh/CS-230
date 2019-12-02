package Challenge;

import javafx.scene.image.Image;

/**
 * This class represents a Door in the game and can be unlocked by a
 * player carrying the correct Key, when it is replaced by a Ground cell
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
    private final Colour colour;

    /**
     * This is used for the colour of the KeyDoor
     */
    public enum Colour {
        RED, GREEN, BLUE, WHITE, BLACK
    }

    static {
        SPRITE = new Image("images/CELL_KEY_DOOR.png");
    }

    /**
     * Constructor
     * @param colour the colour of the door
     */
    KeyDoor(String colour) {
        super(SPRITE, false);
        this.colour = setColour(colour);
    }

    /**
     * Gets the colour of the door
     * @return the colour of the door
     */
    public Colour getColour() {
        return this.colour;
    }

    /**
     * Sets the colour of the KeyDoor for checking
     * @param colour the colour to set
     * @return the colour Enum to set
     */
    private Colour setColour(String colour) {

        if ("RED".equals(colour)) {
            return Colour.RED;
        } else if ("GREEN".equals(colour)) {
            return Colour.GREEN;
        } else if ("BLUE".equals(colour)) {
            return Colour.BLUE;
        } else if ("WHITE".equals(colour)) {
            return Colour.WHITE;
        } else if ("BLACK".equals(colour)) {
            return Colour.BLACK;
        }

        return null;
    }

}