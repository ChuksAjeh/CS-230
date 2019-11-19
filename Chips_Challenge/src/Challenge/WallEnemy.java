package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class WallEnemy extends Enemy {

    private static final EntityType entityType;
    private static final Image sprite;

    static {
        entityType = EntityType.WALL_ENEMY;
        sprite = new Image("images/ENTITY_WALL_ENEMY.png");
    }

    public WallEnemy(int direction) {
        super(entityType, sprite, direction);
    }

    private int nextDirection() {

        // Funky shit

        return 0;
    }

}