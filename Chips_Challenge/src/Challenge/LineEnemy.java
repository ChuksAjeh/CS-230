package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class LineEnemy extends Enemy {

    /**
     * The sprite to represent the dumb enemy.
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_LINE_ENEMY.png");
    }

    public LineEnemy(int direction) {
        super(SPRITE, direction);
    }

    private int changeDirection() {

        // Check for direction, then check whether next position is a wall
        // If it is a wall, change direction, else keep same direction.
        // This should be called every update.

        String[] surroundingCells = getSurroundingCells();

        if (0 == this.getDirection()) { // UP
            return "Wall".equals(surroundingCells[0]) ? 2 : 0;
        } else if (1 == this.getDirection()) { // RIGHT
            return "Wall".equals(surroundingCells[1]) ? 3 : 1;
        } else if (2 == this.getDirection()) { // DOWN
            return "Wall".equals(surroundingCells[2]) ? 0 : 2;
        } else if (3 == this.getDirection()) { // LEFT
            return "Wall".equals(surroundingCells[3]) ? 1 : 3;
        }

        return 0;
    }

}