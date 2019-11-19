package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class FireBoots extends Equipment {

    private static final EntityType entityType;
    private static final Image sprite;

    static {
        entityType = EntityType.FIRE_BOOTS;
        sprite = new Image("images/ENTITY_FIRE_BOOTS.png");
    }

    public FireBoots() {
        super(entityType, sprite, true);
    }

}