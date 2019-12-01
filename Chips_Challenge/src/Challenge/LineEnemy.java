package Challenge;

import javafx.scene.image.Image;

import java.util.Collections;
import java.util.List;

/**
 * @author George Caprenter, Ioan Mazurca
 * @version 3.0
 */
class LineEnemy extends Enemy {

    /**
     * The sprite to represent the dumb enemy.
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_LINE_ENEMY.png");
    }

    LineEnemy(Position position, int direction) {
        super(SPRITE, position, direction);
    }

    public int nextDirection() {

        // Check for direction, then check whether next position is a wall
        // If it is a wall, change direction, else keep same direction.
        // This should be called every update.

        int dir = getDirection();

        boolean[] passable = getCells();

        if (!passable[dir]) {
            this.setDirection((dir + 2) % 4);
        }

        return this.getDirection();
    }

}