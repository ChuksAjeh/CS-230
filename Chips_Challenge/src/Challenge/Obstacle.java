package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Obstacle extends Cell {

    public Obstacle(CellType cellType, boolean passable, Image image) {
        super(cellType, passable, image);
    }

}