package Challenge;

import javafx.scene.image.Image;

/**
 * Enemies are movable hazards designed to end the level upon contact with the player.
 * Each enemy has its own unique way of moving to the player.
 * Again this class shouldn't be instantiated, instead, its sub-classes should be.
 * @author George Carpenter, Angelo Balistoy
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

    /**
     * The entity grid the enemy uses
     */
    private Entity[][] entityGrid;

    /**
     * The position of the Enemy
     */
    private Position position;

    /**
     * Creates an enemy
     * @param sprite the sprite used to represent this Entity
     * @param position the position of the Enemy
     * @param direction the direction the enemy is set upon creation
     * */
    public Enemy(Image sprite, Position position, int direction) {
        super(sprite);
        this.position = position;
        this.direction = direction;
    }

    /**
     * Gets the direction.
     * @return A number from 0-3 (North South East West).
     */
    int getDirection() {
        return direction;
    }

    /**
     * Gets the Position of this Enemy
     * @return the Enemy Position Object
     */
    public Position getPosition() {
        return this.position;
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
    void setDirection(int direction) {
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
     * Gets the next direction of enemies.
     * @param level The level being used.
     * @return The next direction from 0-3 representing North, East, South, West respectively.
     */
    public abstract int nextDirection(Level level);

    /**
     * Moves the enemy based on a given direction.
     * @param level The current level.
     * @param entityGrid the entityGrid in which to move the Enemy Object
     * @return The new entity grid to be used in the next turn.
     */
    public Entity[][] move(Level level, Entity[][] entityGrid) {

        Position position = this.getPosition();
        int direction;

        int x = position.x;
        int y = position.y;

        // TODO : Re-write this - Gnome

        // For all instances of enemy, use its specific next direction.
        direction = this.nextDirection(level);

        if (0 == direction) {
            this.position = new Position(x, y - 1);
            entityGrid[x][y - 1] = this;
        } else if (1 == direction) {
            this.position = new Position(x + 1, y);
            entityGrid[x + 1][y] = this;
        } else if (2 == direction) {
            this.position = new Position(x, y + 1);
            entityGrid[x][y + 1] = this;
        } else if (3 == direction) {
            this.position = new Position(x - 1, y);
            entityGrid[x - 1][y] = this;
        }

        entityGrid[position.x][position.y] = null;

        return entityGrid;

    }

//
//                                  __
//                         /\    .-" /
//                        /  ; .'  .'
//                       :   :/  .'
//                        \  ;-.'
//           .--""""--..__/     `.
//         .'           .'    `o  \
//        /                    `   ;
//       :                  \      :
//     .-;        -.         `.__.-'
//    :  ;          \     ,   ;
//    '._:           ;   :   (
//        \/  .__    ;    \   `-.
//         ;     "-,/_..--"`-..__)
//         '""--.._:
//
//  Figure I, the Killer Rabbit of Caerbannog
//

    /**
     * Gets a list of possible moves for the Enemy
     * @return a list of possible moves, not all are available
     */
    boolean[] getCells() {

        int x = this.position.x;
        int y = this.position.y;

        boolean[] passable = new boolean[4];

        Cell[] sc = new Cell[] {
            this.cellGrid[x][y - 1],
            this.cellGrid[x + 1][y],
            this.cellGrid[x][y + 1],
            this.cellGrid[x - 1][y]
        };

        Entity[] se = new Entity[] {
            this.entityGrid[x][y - 1],
            this.entityGrid[x + 1][y],
            this.entityGrid[x][y + 1],
            this.entityGrid[x - 1][y]
        };

        for (int i = 0; i < passable.length; i++) {
            passable[i] = sc[i] instanceof Ground && (se[i] == null || se[i] instanceof Player);
        }

        return passable;

    }

}
