package Challenge;
/**
 * @author ..
 * @version 1.0
 */
public class TokenDoor extends Door {

    private int requirement;

    public TokenDoor(int requirement) {
        super(CellType.TOKEN_DOOR);
        this.requirement = requirement;
    }

    public int getRequirement() {
        return this.requirement;
    }

}