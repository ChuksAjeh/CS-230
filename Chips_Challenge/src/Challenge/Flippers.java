package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Flippers extends Equipment {

    private static final EntityType entityType;
    private static final Image sprite;

    static {
        entityType = EntityType.FLIPPERS;
        sprite = new Image("images/ENTITY_FLIPPERS.png");
    }

    public Flippers() {
        super(entityType, sprite, true);
    }

}