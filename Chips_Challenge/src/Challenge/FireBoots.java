package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class FireBoots extends Equipment {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_FIRE_BOOTS.png");
    }

    public FireBoots() {
        super(SPRITE);
    }

}