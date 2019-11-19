package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class LineEnemy extends Enemy {

    private static final Image sprite;

    static {
        sprite = new Image("images/ENTITY_LINE_ENEMY.png");
    }

    public LineEnemy(int direction) {
        super(sprite, direction);
    }

    private int changeDirection() {

        //Check for direction, then check whether next position is a wall
        //If it is a wall, change direction, else keep same direction.
        //This should be called every update.

        // TODO : extract this as "getSurroundingCells"

        String up = this.getCellGrid()[this.getEnemyX()][this.getEnemyY() -1].getClass().getSimpleName();
        String right = this.getCellGrid()[this.getEnemyX() + 1][this.getEnemyY()].getClass().getSimpleName();
        String down = this.getCellGrid()[this.getEnemyX()][this.getEnemyY() + 1].getClass().getSimpleName();
        String left = this.getCellGrid()[this.getEnemyX() - 1][this.getEnemyY()].getClass().getSimpleName();

        if (0 == this.getDirection()) {
            // UP
            if ("Wall".equals(up)) {
                return 2;
            } else {
                return 0;
            }
        } else if (1 == this.getDirection()) {
            // RIGHT
            if ("Wall".equals(right)) {
                return 3;
            } else {
                return 1;
            }
        } else if (2 == this.getDirection()) {
            // DOWN
            if ("Wall".equals(down)) {
                return 0;
            } else {
                return 2;
            }
        } else if (3 == this.getDirection()) {
            // LEFT
            if ("Wall".equals(left)) {
                return 1;
            } else {
                return 3;
            }
        }

        return 0;
    }

}