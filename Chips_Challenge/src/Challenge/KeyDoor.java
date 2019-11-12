package Challenge;

import javafx.scene.paint.Color;

public class KeyDoor extends Door {

    private Color colour;

    public KeyDoor(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return this.colour;
    }

}