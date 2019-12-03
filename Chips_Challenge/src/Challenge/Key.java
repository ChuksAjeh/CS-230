package Challenge;

import javafx.scene.image.Image;

/**
 * This class represents a Key used to unlock a door in the game,
 * it can be collected by the player and is consumed on use.
 * @author George Carpenter
 * @version 1.0
 */
class Key extends Item {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    /**
     * The colour of the key
     */
    private final Colour colour;

    /**
     * This is used for the colour of the Key
     */
    enum Colour {
        RED, GREEN, BLUE, WHITE, BLACK
    }

    static {
        SPRITE = new Image("images/ENTITY_KEY.png");
    }

//
//     .---.
//    /    |\________________
//   ( ()  | ________   _   _)
//    \    |/        | | | |
//     `---'         "-" |_|
//
//  Figure III - A key
//

    /**
     * Constructor
     * @param colour the colour of the key
     */
    Key(String colour) {
        super(SPRITE);
        this.colour = setColour(colour);
    }

    /**
     * Gets the colour of the key
     * @return the colour of the key
     */
    Colour getColour() {
        return this.colour;
    }

    /**
     * Sets the colour of the Key for checking
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