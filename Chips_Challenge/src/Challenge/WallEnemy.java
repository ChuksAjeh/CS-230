package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class WallEnemy extends Enemy {

    public WallEnemy(int direction) {
        super(EntityType.WALL_ENEMY, new Image("images/ENTITY_WALL_ENEMY.png"), direction);
    }

    private int nextDirection() {

        // Funky shit

        return 0;
    }

}