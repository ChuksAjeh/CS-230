package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Ground extends Cell {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_GROUND.png");
    }

    public Ground() {
        super(SPRITE, true);
    }

}