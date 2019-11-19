package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Impassable extends Cell {

    public Impassable(CellType cellType, boolean passable, Image image) {
        super(cellType, passable, image);
    }
}