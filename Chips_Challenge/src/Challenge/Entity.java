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

    private Position position;

    Entity(Image sprite, Position position) {
        this.SPRITE = sprite;
        this.position = position;
    }

    public Image getSprite() {
        return SPRITE;
    }

    public Position getPosition() {
        return this.position;
    }

}