package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Wall extends Impassable {

    public Wall() {
        super(CellType.WALL, false, new Image("images/CELL_WALL.png"));
    }
}