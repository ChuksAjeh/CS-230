package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
abstract class Entity {

    /**
     * The sprite used to represent objects of this type in the game
     */
    private final Image SPRITE;

    /**
     * Constructor for an Entity
     * @param sprite the sprite passed from the sub class
     */
    Entity(Image sprite) {
        this.SPRITE = sprite;
    }

    /**
     * Returns the sprite to be rendered in the game
     * @return the sprite for rendering
     */
    public Image getSprite() {
        return SPRITE;
    }

}