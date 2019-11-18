package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Ground extends Cell {

    public Ground() {
        super(CellType.GROUND, true, new Image("images/CELL_GROUND.png"));
    }
}