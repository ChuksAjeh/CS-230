package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * @author ..
 * @version 1.0
 */
public class Key extends Item {

    private static final Image sprite;
    private final Color colour;

    static {
        sprite = new Image("images/ENTITY_KEY.png");
    }

    public Key(Color colour) {
        super(sprite);
        this.colour = colour;
    }

    public Color getColour() {
        return this.colour;
    }
}