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
        super(EntityType.PLAYER, false, new Image("images/ENTITY_PLAYER.png"));
        this.inventory = new ArrayList<>();
        this.direction = direction;
    }

    private Entity[][] movePlayerEntity(int x, int y, int newX, int newY, Entity[][] entityGrid) {

        int height = entityGrid.length - 1;
        int width = entityGrid[0].length - 1;

        if (newX < 0 || newY < 0 || newX > width || newY > height) {
            jack.log(1, "Player out of bounds");
            return entityGrid;
        } else {

            Entity entity = entityGrid[newX][newY];

            if (null != entity && entity.isCollectible()) {

                jack.log("FOUND COLLECTIBLE");

                this.addItem((Item) entityGrid[newX][newY]);
                jack.log(this.getInventory().toString());

            } else {

                // Pretty sure this is an Enemy => Death!

            }

            // Move player entity
            entityGrid[newX][newY] = this;
            entityGrid[x][y] = null;

            return entityGrid;
        }

    }

    public Entity[][] move(int direction, Entity[][] entityGrid) {

        int[] currentLoc = this.getLocation(entityGrid);

        int x = currentLoc[0];
        int y = currentLoc[1];

        if (0 == direction) {

            this.direction = direction;
            return movePlayerEntity(x, y, x, y-1, entityGrid);

        } else if (1 == direction) {

            this.direction = direction;
            return movePlayerEntity(x, y, x+1, y, entityGrid);

        } else if (2 == direction) {

            this.direction = direction;
            return movePlayerEntity(x, y, x, y+1, entityGrid);

        } else if (3 == direction) {

            this.direction = direction;
            return movePlayerEntity(x, y, x-1, y, entityGrid);

        }

        return null;
    }

    public int[] getLocation(Entity[][] entityGrid) {

        // find player in entity grid

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                Entity entity = entityGrid[x][y];

                if (entity != null) {

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

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

}