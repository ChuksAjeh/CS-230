package Challenge;

public class TokenDoor extends Door {

    private int requirement;

    public TokenDoor(int requirement) {
        this.requirement = requirement;
    }

    public int getRequirement() {
        return this.requirement;
    }

}