package Challenge;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * A dumb enemy is an enemy whose movement is determined by the players location. It tries to find the shortest distance
 * to the player and uses that path to determine its next direction. It does not take into account impassable territory
 * hence it is a dumb enemy.
 * @author Samuel Roach, George Carpenter
 * @version 1.0
 */
public class DumbEnemy extends Enemy {

    /**
     * The sprite to represent the dumb enemy.
     */
    private static final Image SPRITE;

    // private Lumberjack jack  = new Lumberjack();

    static {
        // Sets the Sprite to be rendered with this specific image.
        SPRITE = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    /**
     * Constructs a dumb enemy
     * @param position the position of the Enemy
     * @param direction The initial direction of the enemy
     */
    DumbEnemy(Position position, int direction) {
        super(SPRITE, position, direction);
    }

    /**
     * Gets the next direction of the Dumb Enemy.
     * @return The next direction.
     */
    public int nextDirection(Level level) {
        Player player = level.getPlayer();
        return this.nextDirection(player);
    }

    /**
     * Finds the next direction of the dumb enemy where 0-3 represent North, East, South, West respectively.
     * @param player the player object
     * @return The next direction
     */
    private int nextDirection(Player player) {

        Random random = new Random();

        Position playerPos = player.getPosition();
        Position enemyPos = this.getPosition();

        if (playerPos.x > enemyPos.x) {

            // Player is EAST of Enemy
            return playerPos.y > enemyPos.y ?
                    // Player is SOUTH EAST of Enemy
                    random.nextBoolean() ? 2 : 1 :
                    // Player is NORTH EAST of Enemy
                    random.nextBoolean() ? 0 : 1;

        } else if (playerPos.x < enemyPos.x) {

            // Player is WEST of Enemy
            return playerPos.y > enemyPos.y ?
                    // Player is SOUTH WEST of Enemy
                    random.nextBoolean() ? 2 : 3 :
                    // Player is NORTH WEST of Enemy
                    random.nextBoolean() ? 0 : 3;

        } else {

            // Player and Enemy have the same X
            return playerPos.y > enemyPos.y ? 2 : 0;

        }

    }

}