package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class SmartEnemy extends Enemy {

    private static final EntityType entityType;
    private static final Image sprite;

    static {
        entityType = EntityType.SMART_ENEMY;
        sprite = new Image("images/ENTITY_SMART_ENEMY.png");
    }

    public SmartEnemy(int direction) {
        super(entityType, sprite, direction);
    }

    private int nextDirection() {

        // Really fancy shit

        return 0;
    }

}