package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Door extends Obstacle {

    public Door(Image SPRITE, boolean passable) {
        super(SPRITE, passable);
    }

}