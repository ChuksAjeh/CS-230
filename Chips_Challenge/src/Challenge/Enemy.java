package Challenge;

import javafx.scene.image.Image;

/**
 * Enemies are movable hazards designed to end the level upon contact with the player.
 * Each enemy has its own unique way of moving to the player.
 * Again this class shouldn't be instantiated, instead, its sub-classes should be.
 * @author George Carpenter, Angelo Balistoy
 * @version 1.0
 */
//Enemies can only stand on ground
abstract class Enemy extends Entity {

    /**
     * The direction the enemy will travel
     */
    int direction;

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
     */
    Enemy(Image sprite, Position position, int direction) {
        super(sprite);
        this.position = position;
        this.direction = direction;
    }

    Entity[][] move(Level level, Entity[][] entityGrid) {

        Position position = this.getPosition();
        int direction = 0;

        int x = position.x;
        int y = position.y;

        if (this instanceof SmartEnemy) {
            direction = ((SmartEnemy) this).nextDirection(level, level.getPlayer());
        } else if (this instanceof DumbEnemy) {
            direction = ((DumbEnemy) this).nextDirection(level.getPlayer());
        } else if (this instanceof LineEnemy) {
            direction = ((LineEnemy) this).nextDirection();
        } else if (this instanceof WallEnemy) {
            direction = ((WallEnemy) this).nextDirection();
        }

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
        } else if (42 == direction) {
            // Enemy cannot move, send halp!
            // Owait never mind we can just noobmaster69 out of it!
            return entityGrid;
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
            passable[i] = sc[i] instanceof Ground && se[i] == null;
        }

        return passable;

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
