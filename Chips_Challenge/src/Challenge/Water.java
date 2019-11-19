package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Water extends Obstacle {

    private static final CellType cellType;
    private static final Image sprite;

    static {
        cellType = CellType.WATER;
        sprite = new Image("images/CELL_WATER.png");
    }

    public Water() {
        super(cellType, sprite, false);
    }
}