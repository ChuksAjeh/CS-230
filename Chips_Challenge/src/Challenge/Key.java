package Challenge;

import javafx.scene.paint.Color;
/**
 * @author ..
 * @version 1.0
 */
public class Key extends Item {

    private Color colour;

    public Key(Color colour) {
        super(EntityType.KEY);

        // TODO : This thing

    }

    public Color getColour() {
        return this.colour;
    }
}