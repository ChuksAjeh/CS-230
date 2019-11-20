package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Water extends Obstacle {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_WATER.png");
    }

    public Water() {
        super(SPRITE, false);
    }

}