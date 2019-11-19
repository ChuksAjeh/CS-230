package Challenge;

import javafx.scene.image.Image;

import java.util.Random;

//#TODO CHANGE WALL TO EVERY OBSTACLE OBJECT
/**
 * @author Ioan Mazurca
 * @version 1.0
 */
public class WallEnemy extends Enemy {

    private static final EntityType entityType;
    private static final Image sprite;
    private Random random;

    static {
        entityType = EntityType.WALL_ENEMY;
        sprite = new Image("images/ENTITY_WALL_ENEMY.png");
    }

    public WallEnemy(int direction) {
        super(entityType, sprite, direction);
        random = new Random();
    }

    private int nextDirection() {

        // Check for direction : 0-2 (same cases) and 1-3 (same cases)
        // Check if there is wall on each side of the player
        // 1) If there are walls on both sides and also on the current movement then change to opposite direction
        // Otherwise move in the current direction
        // 2) For 0-2 If there is a WALL in LEFT (3) and a GROUND in RIGHT (1) then go RIGHT
        // If there is a WALL in RIGHT (1) and a GROUND in LEFT (3) then go LEFT
        // 3) For 1-3 If there is a WALL in UP (0) and a GROUND in DOWN (2) then go DOWN
        // If there is a WALL in DOWN (2) and a GROUND in UP (0) then go UP
        // 4) For 0-2 IF both LEFT(3) and RIGHT(1) are GROUND then do random between LEFT(3) and RIGHT(1)
        // 5) For 1-3 IF both UP(0) and DOWN(2) are GROUND then do random between UP(0) and DOWN(2)

        int direction = this.getDirection();

        Cell.CellType up = this.getCellGrid()[this.getEnemyX()][this.getEnemyY() -1].getCellType();
        Cell.CellType right = this.getCellGrid()[this.getEnemyX() + 1][this.getEnemyY()].getCellType();
        Cell.CellType down = this.getCellGrid()[this.getEnemyX()][this.getEnemyY() + 1].getCellType();
        Cell.CellType left = this.getCellGrid()[this.getEnemyX() - 1][this.getEnemyY()].getCellType();

        if (0 == direction || 2 == direction) {

            // Up + Down

            if (Cell.CellType.WALL == left && Cell.CellType.WALL == right) {

                if (Cell.CellType.WALL == up) {
                    return 2;
                } else if (Cell.CellType.WALL == down) {
                    return 0;
                } else {
                    return direction;
                }

            } else if (0 == direction && Cell.CellType.WALL == up) {

                if (Cell.CellType.WALL == left) {
                    return 1;
                } else if (Cell.CellType.WALL == right) {
                    return 3;
                }

            } else if (2 == direction && Cell.CellType.WALL == down) {

                if (Cell.CellType.WALL == left) {
                    return 1;
                } else if (Cell.CellType.WALL == right) {
                    return 3;
                }

            } else {
                return direction;
            }

        } else if (1 == direction || 3 == direction) {

            // Left + Right

            if (Cell.CellType.WALL == up && Cell.CellType.WALL == down) {

                if (Cell.CellType.WALL == left) {
                    return 1;
                } else if (Cell.CellType.WALL == right) {
                    return 3;
                } else {
                    return direction;
                }

            } else if (1 == direction && Cell.CellType.WALL == right) {

                if (Cell.CellType.WALL == up) {
                    return 2;
                } else if (Cell.CellType.WALL == down) {
                    return 0;
                }

            } else if (3 == direction && Cell.CellType.WALL == left) {

                if (Cell.CellType.WALL == up) {
                    return 2;
                } else if (Cell.CellType.WALL == down) {
                    return 0;
                }

            } else {
                return direction;
            }

        }

        return 0;

    }
}