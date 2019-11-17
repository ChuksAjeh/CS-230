package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Item extends Entity {

    public Item(EntityType entityType, Image image) {
        super(entityType, image);
    }
}