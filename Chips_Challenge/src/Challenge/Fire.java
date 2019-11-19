package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Fire extends Obstacle {

    public Fire() {
        super(CellType.FIRE, false, new Image("images/CELL_FIRE.png"));
    }
}