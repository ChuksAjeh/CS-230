package Challenge;

import javafx.scene.image.Image;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

/**A dumb enemy is an enemy whose movement is determined by the players location. It tries to find the shortest distance
 * to the player and uses that path to determine its next direction. It does not take into account impassable territory
 * hence it is a dumb enemy.
 * @author Samuel
 * @version 1.0
 */
public class DumbEnemy extends Enemy {
    /**
     * The sprite to represent the dumb enemy.
     */
    private static final Image sprite;

    static {
        //--Change sprite here--
        sprite = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    /**
     * Constructs a dumb enemy
     * @param direction The intial direction of the enemy
     */
    public DumbEnemy(int direction) {
        super(sprite, direction);
    }

    /**
     * Finds the next direction of the dumb enemy.
     * @return The next direction
     */
    public int nextDirection() {
        int xDif = getPlayerPosition()[0] - getEnemyX();
        int yDif = getPlayerPosition()[1] - getEnemyY();

        if (abs(xDif) > abs(yDif)) {
            if (xDif > 0) {
                return 1;
            } else {
                return 3;
            }
        } else if (abs(xDif) < abs(yDif)) {
            if (yDif > 0) {
                return 2;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

}