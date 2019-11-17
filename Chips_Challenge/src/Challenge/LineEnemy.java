package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class LineEnemy extends Enemy {

    public LineEnemy(int direction) {
        super(EntityType.LINE_ENEMY, new Image("images/ENTITY_ENEMY.png"), direction);
    }

    private int changeDirection() {

        // Easy shit

        return 0;
    }

}