package Challenge;

import javafx.scene.image.Image;

import java.util.ArrayList;
/**
 * @author ..
 * @version 1.0
 */
public class Player extends Entity {

    // TESTING
    Lumberjack jack = new Lumberjack();

    private ArrayList<Item> inventory;

    private int direction;

    public Player(int direction) {
        super(EntityType.PLAYER, new Image("images/ENTITY_PLAYER.png"));
        this.inventory = new ArrayList<>();
        this.direction = direction;
    }

    public Entity[][] move(int direction, Entity[][] entityGrid) {

        int height = entityGrid.length - 1;
        int width = entityGrid[0].length - 1;

        // TODO : Error checking on moving out of the grid

        int[] currentLoc = this.getLocation(entityGrid);

        int x = currentLoc[0];
        int y = currentLoc[1];

        int newX = x;
        int newY = y;

        if (0 == direction) {

            newY = y - 1;

            if (0 > newY) {
                jack.log(1, "Player out of bounds");
                return entityGrid;
            } else {

                this.direction = direction;

                // Move player entity
                entityGrid[x][newY] = this;
                entityGrid[x][y] = null;

                return entityGrid;
            }

        } else if (1 == direction) {

            newX = x + 1;

            if (width < newX) {
                jack.log(1, "Player out of bounds");
                return entityGrid;
            } else {

                this.direction = direction;

                // Move player entity
                entityGrid[newX][y] = this;
                entityGrid[x][y] = null;

                return entityGrid;
            }

        } else if (2 == direction) {

            newY = y + 1;

            if (height < newY) {
                jack.log(1, "Player out of bounds");
                return entityGrid;
            } else {

                this.direction = direction;

                // Move player entity
                entityGrid[x][newY] = this;
                entityGrid[x][y] = null;

                return entityGrid;
            }

        } else if (3 == direction) {

            newX = x - 1;

            if (0 > newX) {
                jack.log(1, "Player out of bounds");
                return entityGrid;
            } else {

                this.direction = direction;

                // Move player entity
                entityGrid[newX][y] = this;
                entityGrid[x][y] = null;

                return entityGrid;
            }

        }

        return null;
    }

    public int[] getLocation(Entity[][] entityGrid) {

        // find player in entity grid

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                Entity entity = entityGrid[x][y];

                if (entity != null) {

//                    System.out.println(entity);

                    if (entity.getEntityType() == EntityType.PLAYER) {
                        // Player is found
                        return new int[] {x, y};
                    }
                }
            }
        }
        return new int[] {0, 0};
    }

    public int getDirection() {
        return direction;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

}