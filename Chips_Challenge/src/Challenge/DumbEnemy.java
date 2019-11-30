package Challenge;

import javafx.scene.image.Image;

import static java.lang.Math.abs;

/**A dumb enemy is an enemy whose movement is determined by the players location. It tries to find the shortest distance
 * to the player and uses that path to determine its next direction. It does not take into account impassable territory
 * hence it is a dumb enemy.
 * @author Samuel
 * @version 1.0
 */
class DumbEnemy extends Enemy {
    /**
     * The sprite to represent the dumb enemy.
     */
    private static final Image SPRITE;
    private Lumberjack jack  = new Lumberjack();

    static {
        SPRITE = new Image("images/ENTITY_DUMB_ENEMY.png");
    }

    /**
     * Constructs a dumb enemy
     * @param position the position of the Enemy
     * @param direction The intial direction of the enemy
     */
    public DumbEnemy(Position position, int direction) {
        super(SPRITE, position, direction);
    }

    /**
     * Finds the next direction of the dumb enemy.
     * @param player the player object
     * @return The next direction
     */
    public int nextDirection(Player player) {

        int xDif = player.getPosition().x - this.getPosition().x;
        int yDif = player.getPosition().y - this.getPosition().y;

        if (abs(xDif) > abs(yDif)) {
            return 0 < xDif ? 1 : 3;
        } else if (abs(xDif) < abs(yDif)) {
            return 0 < yDif ? 2 : 0;
        } else {
            return 0;
        }

    }

}