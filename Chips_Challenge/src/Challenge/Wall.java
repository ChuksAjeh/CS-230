package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Wall extends Impassable {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/CELL_WALL.png");
    }

    public Wall() {
        super(SPRITE, false);
    }

}