package Challenge;
//#TODO Link enemy x and y to the actual grid. i.e. enemyX = 1 and enemy Y = 1 corresponds to cellGrid[1][1]

import javafx.scene.image.Image;

/**
 * Enemies are movable hazards designed to end the level upon contact with the player. Each enemy has its own unique
 * way of moving to the player. Again this class shouldn't be instantiated, instead, its sub-classes should be.
 * @author ..
 * @version 1.0
 */
abstract class Enemy extends Entity {

    /**
     * The direction the enemy will travel
     */
    private int direction;

    /**
     * Creates an enemy
     *
     * @param direction the direction the enemy is set upon creation
     */
    public Enemy(EntityType entityType, Image image, int direction) {
        super(entityType, image);
        this.direction = direction;
    }

    /**
     * #TODO How does the move method work for entities.
     *
     * @param direction
     * @return
     */
    protected int[] move(int direction) throws Exception {
//
//        if (0 == direction) {
//            // UP
//            this.enemyY += 1;
//        } else if (1 == direction) {
//            // RIGHT
//            this.enemyX += 1;
//        } else if (2 == direction) {
//            // DOWN
//            this.enemyY -= 1;
//        } else if (3 == direction) {
//            // LEFT
//            this.enemyX -= 1;
//
//        } else {
//            //throw exception (custom one like OutOfDirectionRange?
//            throw new Exception("Direction out of range!");
//        }
//
        return null;
    }

    public int[] getLocation(Entity[][] entityGrid) {

        // find player in entity grid

        for (int x = 0; x < entityGrid.length; x++) {
            for (int y = 0; y < entityGrid[x].length; y++) {

                Entity entity = entityGrid[x][y];

                if (entity != null) {

//                    System.out.println(entity);

                    if (entity.getEntityType() == EntityType.LINE_ENEMY) {
                        // Player is found
                        return new int[]{x, y};
                    }
                }
            }
        }
        return new int[]{0, 0};
    }


    /**
     * Gets the player position.
     *
     * @return player position.
     */
    public int[] getPlayerPosition() {
        return null;
    }

    /**
     * Sets the direction of the enemy.
     *
     * @param direction The new direction of the enemy.
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Gets the direction.
     *
     * @return A number from 0-3 (North South East West).
     */
    protected int getDirection() {
        return direction;
    }
}

