package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Goal extends Cell {

    private static final Image sprite;

    static {
        sprite = new Image("images/CELL_GOAL.png");
    }

    public Goal() {
        super(sprite, true);
    }
}