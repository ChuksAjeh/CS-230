package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Item extends Entity {

    public Item(EntityType entityType, Image sprite, boolean collectible) {
        super(entityType, sprite, collectible);
    }
}