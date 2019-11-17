package Challenge;
/**
 * @author ..
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    public DumbEnemy(int direction) {
        super(EntityType.DUMB_ENEMY, direction);
        //This is a dumbenemy
    }

    private int nextDirection() {

        // Fancy shit

        return 0;
    }

}