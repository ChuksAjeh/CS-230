package Challenge;

/**
 * This is a cell class representing an abstract cell in the game. Not meant to be instantiated, it is used to allow all
 * cells to be able to set and get its type and also indicate whether it is a passable cell or not.
 */
abstract class Cell {

    private Object type;
    protected boolean passable;

    private void setType() {

    }

    private Object getType() {
        return this.type;
    }

    private void setPassable() {

    }

    private boolean getPassable() {
        return this.passable;
    }

}