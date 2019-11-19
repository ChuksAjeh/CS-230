package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    private static final Image sprite;

    static {
        sprite = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    public DumbEnemy(int direction) {
        super(sprite, direction);
    }

    private int nextDirection() {

        // Fancy shit

        return 0;
    }

}