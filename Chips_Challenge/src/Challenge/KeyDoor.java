package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * @author ..
 * @version 1.0
 */
public class KeyDoor extends Door {

    private Color colour;

    public KeyDoor(Color colour) {
        super(CellType.KEY_DOOR, new Image("images/CELL_KEY_DOOR.png"));
        this.colour = colour;
    }

    public Color getColour() {
        return this.colour;
    }

}