package Challenge;

import javafx.scene.image.Image;

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
    private final Colour colour;

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