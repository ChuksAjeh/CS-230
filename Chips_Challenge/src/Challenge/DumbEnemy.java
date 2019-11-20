package Challenge;

import javafx.scene.image.Image;
import static java.lang.Math.abs;

/**
 * @author ..
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    private static final Image SPRITE;
    private Lumberjack jack  = new Lumberjack();

    static {
        SPRITE = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    public DumbEnemy(int direction) {
        super(SPRITE, direction);
    }

    private int nextDirection() {

        int xDif = getPlayerPosition()[0] - getEnemyX();
        int yDif = getPlayerPosition()[1] - getEnemyY();

        if (abs(xDif) > abs(yDif)) {
            return 0 < xDif ? 1 : 3;
        } else if (abs(xDif) < abs(yDif)) {
            return 0 < yDif ? 2 : 0;
        } else {
            return 0;
        }

    }

}