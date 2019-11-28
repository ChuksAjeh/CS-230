package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
abstract class Door extends Obstacle {

    /**
     * Constructor
     * @param sprite the sprite used for the cell of this type
     * @param passable whether or not the cell will bje passable
     */
    Door(Image sprite, boolean passable) {
        super(sprite, passable);
    }

}