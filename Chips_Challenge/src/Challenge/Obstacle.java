package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Obstacle extends Cell {

    public Obstacle(Image sprite, boolean passable) {
        super(sprite, passable);
    }

}