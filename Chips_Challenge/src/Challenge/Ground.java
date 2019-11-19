package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Ground extends Cell {

    private static final Image sprite;

    static {
        sprite = new Image("images/CELL_GROUND.png");
    }

    public Ground() {
        super(sprite, true);
    }
}