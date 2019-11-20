package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class LineEnemy extends Enemy {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_LINE_ENEMY.png");
    }

    public LineEnemy(int direction) {
        super(SPRITE, direction);
    }

    private int changeDirection() {

        //Check for direction, then check whether next position is a wall
        //If it is a wall, change direction, else keep same direction.
        //This should be called every update.

        String[] surroundingCells = getSurroundingCells();

        if (0 == this.getDirection()) {
            // UP
            if ("Wall".equals(surroundingCells[0])) {
                return 2;
            } else {
                return 0;
            }
        } else if (1 == this.getDirection()) {
            // RIGHT
            if ("Wall".equals(surroundingCells[1])) {
                return 3;
            } else {
                return 1;
            }
        } else if (2 == this.getDirection()) {
            // DOWN
            if ("Wall".equals(surroundingCells[2])) {
                return 0;
            } else {
                return 2;
            }
        } else if (3 == this.getDirection()) {
            // LEFT
            if ("Wall".equals(surroundingCells[3])) {
                return 1;
            } else {
                return 3;
            }
        }

        return 0;
    }

    private String[] getSurroundingCells() {

        int x = this.getEnemyX();
        int y = this.getEnemyY();

        return new String[] {
            getCellName(x, y - 1),
            getCellName(x + 1, y),
            getCellName(x, y + 1),
            getCellName(x - 1, y)
        };
    }

    private String getCellName(int x, int y) {
        return this.getCellGrid()[x][y].getClass().getSimpleName();
    }

}