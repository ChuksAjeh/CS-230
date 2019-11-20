package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class TokenDoor extends Door {

    private static final Image SPRITE;
    private final int requirement;

    static {
        SPRITE = new Image("images/CELL_TOKEN_DOOR.png");
    }

    public TokenDoor(int requirement) {
        super(SPRITE, false);
        this.requirement = requirement;
    }

    public int getRequirement() {
        return this.requirement;
    }

}