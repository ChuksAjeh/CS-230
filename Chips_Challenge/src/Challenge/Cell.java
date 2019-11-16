package Challenge;
/**
 * This is a cell class representing an abstract cell in the game. Not meant to be instantiated, it is used to allow all
 * cells to be able to set and get its type and also indicate whether it is a passable cell or not.
 * @author ..
 * @version 1.0
 *
 */
abstract class Cell {

    private Type type;
    protected boolean passable;

    public enum Type {
        GROUND, WALL
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    private void setPassable() {

    }

    private boolean getPassable() {
        return this.passable;
    }

}