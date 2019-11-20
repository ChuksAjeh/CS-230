package Challenge;

import javafx.scene.image.Image;
import static java.lang.Math.abs;

/**
 * @author ..
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    private static final Image sprite;
    private Lumberjack jack  = new Lumberjack();

    static {
        sprite = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    public DumbEnemy(int direction) {
        super(sprite, direction);
    }

    private int nextDirection() {
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