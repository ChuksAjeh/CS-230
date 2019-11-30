package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
class TokenDoor extends Door {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    /**
     * How many keys are required to open the door
     */
    private final int requirement;

    static {
        SPRITE = new Image("images/CELL_TOKEN_DOOR.png");
    }

    /**
     * Constructor
     * @param requirement number of tokens required to open the door
     */
    TokenDoor(int requirement) {
        super(SPRITE, false);
        this.requirement = requirement;
    }

    /**
     * Returns the requirement for the door
     * @return the requirement for the door
     */
    int getRequirement() {
        return this.requirement;
    }

}