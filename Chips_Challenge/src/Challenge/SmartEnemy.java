package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class SmartEnemy extends Enemy {

    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_SMART_ENEMY.png");
    }

    public SmartEnemy(int direction) {
        super(SPRITE, direction);
    }

    private int nextDirection() {

        // Really fancy shit

        return 0;
    }

}