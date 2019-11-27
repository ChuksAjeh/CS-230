package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
abstract class Obstacle extends Cell {

    /**
     * Constructor
     * @param sprite the sprite used to represent an object of this type
     * @param passable whether or not this cell will be passable
     */
    public Obstacle(Image sprite, boolean passable) {
        super(sprite, passable);
    }

}