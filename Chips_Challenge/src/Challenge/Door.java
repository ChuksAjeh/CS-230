package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Door extends Obstacle {

    public Door(CellType cellType, boolean passable, Image image) {
        super(cellType, passable, image);
    }

}