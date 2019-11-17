package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Item extends Entity {

    public Item(EntityType entityType, boolean collectible, Image image) {
        super(entityType, collectible, image);
    }
}