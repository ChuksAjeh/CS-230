package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class TokenDoor extends Door {

    private int requirement;

    public TokenDoor(int requirement) {
        super(CellType.TOKEN_DOOR, new Image("images/CELL_TOKEN_DOOR.png"));
        this.requirement = requirement;
    }

    public int getRequirement() {
        return this.requirement;
    }

}