package Challenge;
//#TODO Link enemy x and y to the actual grid. i.e. enemyX = 1 and enemy Y = 1 corresponds to cellGrid[1][1]

import javafx.scene.image.Image;

/**
 * Enemies are movable hazards designed to end the level upon contact with the player.
 * Each enemy has its own unique way of moving to the player.
 * Again this class shouldn't be instantiated, instead, its sub-classes should be.
 * @author ..
 * @version 1.0
 */
//Enemies can only stand on ground
abstract class Enemy extends Entity {

    /**
     * The direction the enemy will travel
     */
    private int direction;

    /**
     * The cell grid the enemy uses
     */
    private Cell[][] cellGrid;

    /**
     * The entity grid the enemy uses
     */
    private Entity[][] entityGrid;

    private Position position;

    /**
     * Creates an enemy
     * @param sprite the sprite used to represent this Entity
     * @param position the position of the Enemy
     * @param direction the direction the enemy is set upon creation
     */
    Enemy(Image sprite, Position position, int direction) {
        super(sprite);
        this.position = position;
        this.direction = direction;
    }

    /**
     * Moves the enemy based on the inputted direction
     * @param direction The direction 0-3 representing N-S-E-W
     * @return The new coordinates the enemy will be at in the entity grid.
     */
    protected int[] move(int direction) {

//        if (0 == direction) {
//             UP
//            this.enemyY += 1;
//        } else if (1 == direction) {
//             RIGHT
//            this.enemyX += 1;
//        } else if (2 == direction) {
//             DOWN
//            this.enemyY -= 1;
//        } else if (3 == direction) {
//             LEFT
//            this.enemyX -= 1;
//        }

        return null;
    }

    Cell[] getSurroundingCells() {

        int x = this.position.x;
        int y = this.position.y;

        return new Cell[] {
            cellGrid[x][y - 1],
            cellGrid[x + 1][y],
            cellGrid[x][y + 1],
            cellGrid[x - 1][y]
        };

    }

    Entity[] getSurroundingEntitys() {

        int x = this.position.x;
        int y = this.position.y;

        return new Entity[] {
            entityGrid[x][y - 1],
            entityGrid[x + 1][y],
            entityGrid[x][y + 1],
            entityGrid[x - 1][y]
        };

    }

    /**
     * Gets the cell grid.
     * @return The cell grid.
     */
    protected Cell[][] getCellGrid() {
        return this.cellGrid;
    }

    /**
     * Sets the direction of the enemy.
     * @param direction The new direction of the enemy.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Sets the cell grid for the enemy.
     * @param cellGrid The cell grid to be used.
     */
    public void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    /**
     * Sets the entity grid for the enemy.
     * @param entityGrid The cell grid to be used.
     */
    public void setEntityGrid(Entity[][] entityGrid) {
        this.entityGrid = entityGrid;
    }

    /**
     * Gets the direction.
     * @return A number from 0-3 (North South East West).
     */
    int getDirection() {
        return direction;
    }

    public Position getPosition() {
        return this.position;
    }

}
