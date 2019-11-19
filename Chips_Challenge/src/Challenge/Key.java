package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * @author ..
 * @version 1.0
 */
public class Key extends Item {

    private static final Image sprite;
    private static final boolean collectible;
    private final Color colour;

    static {
        sprite = new Image("images/ENTITY_KEY.png");
        collectible = true;
    }

    public Key(Color colour) {
        super(sprite, collectible);
        this.colour = colour;
    }

    public Color getColour() {
        return this.colour;
    }
}