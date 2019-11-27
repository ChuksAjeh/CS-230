package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
abstract class Entity {

    /**
     *
     */
    private final Image SPRITE;

    public Entity(Image sprite) {
        this.SPRITE = sprite;
    }

    public Image getSprite() {
        return SPRITE;
    }

}