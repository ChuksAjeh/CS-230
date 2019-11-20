package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Flippers extends Equipment {
    /**
     * The sprite used for the flipper
     */
    private static final Image sprite;

    static {
        sprite = new Image("images/ENTITY_FLIPPERS.png");
    }

    /**
     * Constructs a flipper object.
     */
    public Flippers() {
        super(sprite);
    }

}