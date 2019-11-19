package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * @author ..
 * @version 1.0
 */
public class KeyDoor extends Door {

    private static final Image sprite;
    private final Color colour;

    static {
        sprite = new Image("images/CELL_KEY_DOOR.png");
    }

    public KeyDoor(Color colour) {
        super(sprite, false);
        this.colour = colour;
    }

    public Color getColour() {
        return this.colour;
    }

}