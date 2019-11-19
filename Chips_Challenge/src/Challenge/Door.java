package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Door extends Obstacle {

    public Door(CellType cellType, Image sprite, boolean passable) {
        super(cellType, sprite, passable);
    }

}