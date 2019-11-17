package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    public DumbEnemy(int direction) {
        super(EntityType.DUMB_ENEMY, new Image("images/ENTITY_ENEMY.png"), direction);
        //This is a dumbenemy
    }

    private int nextDirection() {

        // Fancy shit

        return 0;
    }

}