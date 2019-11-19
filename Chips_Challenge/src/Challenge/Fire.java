package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Fire extends Obstacle {

    private static final CellType cellType;
    private static final Image sprite;

    static {
        cellType = CellType.FIRE;
        sprite = new Image("images/CELL_FIRE.png");
    }

    public Fire() {
        super(cellType, sprite, false);
    }
}