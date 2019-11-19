package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Obstacle extends Cell {

    public Obstacle(CellType cellType, Image sprite, boolean passable) {
        super(cellType, sprite, passable);
    }

}