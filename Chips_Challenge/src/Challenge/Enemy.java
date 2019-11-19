package Challenge;
//#TODO Link enemy x and y to the actual grid. i.e. enemyX = 1 and enemy Y = 1 corresponds to cellGrid[1][1]

import javafx.scene.image.Image;

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
     * The enemy X's position relative to top left cell == 0
     */
    private int enemyX;

    /**
     * The enemy Y's position relative to top left cell == 0
     */
    private int enemyY;

    /**
     * The cell grid the enemy uses
     */
    private Cell[][] cellGrid;

    /**
     * Creates an enemy
     * @param direction the direction the enemy is set upon creation
     */
    public Enemy(Image sprite, int direction) {
        super(sprite, false);
        this.direction = direction;
    }

    /**
     * #TODO How does the move method work for entities.
     * @param direction
     * @return
     */
    protected int[] move(int direction) throws Exception {

        if (0 == direction) {
            // UP
            this.enemyY += 1;
        } else if (1 == direction) {
            // RIGHT
            this.enemyX += 1;
        } else if (2 == direction) {
            // DOWN
            this.enemyY -= 1;
        } else if (3 == direction) {
            // LEFT
            this.enemyX -= 1;

        } else {
            //throw exception (custom one like OutOfDirectionRange?
            throw new Exception("Direction out of range!");
        }

        return null;
    }

    /**
     * Gets the cell grid.
     * @return The cell grid.
     */
    protected Cell[][] getCellGrid() {
        return this.cellGrid;
    }

    /**
     * Gets the player position.
     * @return player position.
     */
    public int[] getPlayerPosition() {
        return null;
    }

    /**
     * Sets the direction of the enemy.
     * @param direction The new direction of the enemy.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Sets the enemy's X coordinate.
     * @param enemyX The new X coordinate.
     */
    public void setEnemyX(int enemyX) {
        this.enemyX = enemyX;
    }

    /**
     * Sets the enemy's Y coordinate.
     * @param enemyY The new Y coordinate.
     */
    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY;
    }

    /**
     * Sets the cell grid for the enemy.
     * @param cellGrid The cell grid to be used.
     */
    public void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    /**
     * Gets the direction.
     * @return A number from 0-3 (North South East West).
     */
    protected int getDirection() {
        return direction;
    }

    /**
     * Gets the enemy's X coordinate.
     * @return The X coordinate.
     */
    protected int getEnemyX() {
        return enemyX;
    }

    /**
     * Gets the enemy's Y coordinate.
     * @return  The Y coordinate.
     */
    protected int getEnemyY() {
        return enemyY;
    }

}