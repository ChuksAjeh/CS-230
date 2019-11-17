package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Equipment extends Item {

    public Equipment(EntityType entityType, Image image) {
        super(entityType, image);
    }
}