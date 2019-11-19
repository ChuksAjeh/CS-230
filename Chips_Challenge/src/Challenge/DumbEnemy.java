package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    private static final EntityType entityType;
    private static final Image sprite;

    static {
        entityType = EntityType.DUMB_ENEMY;
        sprite = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    public DumbEnemy(int direction) {
        super(entityType, sprite, direction);
    }

    private int nextDirection() {

        // Fancy shit

        return 0;
    }

}