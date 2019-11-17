package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class SmartEnemy extends Enemy {

    public SmartEnemy(int direction) {
        super(EntityType.SMART_ENEMY, new Image("images/ENTITY_ENEMY.png"), direction);
    }

    private int nextDirection() {

        // Really fancy shit

        return 0;
    }

}