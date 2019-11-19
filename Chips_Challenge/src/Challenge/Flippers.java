package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Flippers extends Equipment {

    private static final Image sprite;

    static {
        sprite = new Image("images/ENTITY_FLIPPERS.png");
    }

    public Flippers() {
        super(sprite, true);
    }

}