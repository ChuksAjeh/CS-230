package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Ground extends Cell {

    private static final CellType cellType;
    private static final Image sprite;

    static {
        cellType = CellType.GROUND;
        sprite = new Image("images/CELL_GROUND.png");
    }

    public Ground() {
        super(cellType, sprite, true);
    }
}