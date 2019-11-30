package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
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
    private final Color colour;

    static {
        SPRITE = new Image("images/ENTITY_KEY.png");
    }

    /**
     * Constructor
     * @param colour the colour of the key
     */
    public Key(Color colour) {
        super(SPRITE);
        this.colour = colour;
    }

    /**
     * Gets the colour of the key
     * @return the colour of the key
     */
    public Color getColour() {
        return this.colour;
    }

}