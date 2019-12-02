package Challenge;

import javafx.scene.image.Image;

/**
 * This class represents a door. Doors can be opened with tokens or keys
 * which change it from passable to impassable based on the player's inventory.
 * This is meant to restrict movement and allow for more complex game play.
 * @author George Carpenter
 * @version 1.0
 */
abstract class Door extends Obstacle {

    /**
     * Constructor
     * @param sprite the sprite used for the cell of this type
     * @param passable whether or not the cell will be passable
     */
    Door(Image sprite, boolean passable) {
        super(sprite, passable);
    }

}