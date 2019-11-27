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

        Cell[] sc = getSurroundingCells();
        int dir = this.getDirection();

        return sc[dir] instanceof Wall ? (dir + 2) % 4 : dir;

    }

}