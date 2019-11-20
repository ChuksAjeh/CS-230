package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Fire extends Obstacle {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_FIRE.png");
    }

    public Fire() {
        super(SPRITE, false);
    }

}