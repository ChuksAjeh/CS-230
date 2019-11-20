package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Token extends Item {

    private static final Image sprite;

    static {
        sprite = new Image("images/ENTITY_TOKEN.png");
    }

    public Token() {
        super(sprite);
    }

}