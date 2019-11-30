package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
class FireBoots extends Equipment {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_FIRE_BOOTS.png");
    }

    /**
     * Constructor
     */
    FireBoots() {
        super(SPRITE);
    }

}