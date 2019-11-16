package Challenge;

/**
 * Enemies are movable hazards designed to end the level upon contact with the player. Each enemy has its own unique
 * way of moving to the player. Again this class shouldn't be instantiated, instead, its sub-classes should be.
 * @author ..
 * @version 1.0
 */
abstract class Enemy extends Entity {

    /**
     * The direction the enemy will travel
     */
    private int direction;

    /**
     * The cell grid the enemy uses
     */
    private Cell[][] cellGrid;

    public Enemy(int direction) {
        this.direction = direction;
    }

    public int[] move(int direction) {
        return null;
    }

    private Cell[][] getCellGrid() {
        return null;
    }

    public int[] getPlayerPosition() {
        return null;
    }

}