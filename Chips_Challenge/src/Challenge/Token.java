package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Token extends Item {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_TOKEN.png");
    }

    /**
     * Constructor
     */
    Token() {
        super(SPRITE);
    }

}