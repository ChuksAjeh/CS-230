package Challenge;

import java.util.ArrayList;
/**
 * @author ..
 * @version 1.0
 */
public class Player extends Entity {

    private ArrayList<Item> inventory;

    public Player() {
        super(EntityType.PLAYER);
        this.inventory = new ArrayList<>();
    }

    public Entity[][] move(int direction, Entity[][] entityGrid) {

        int[] currentLoc = this.getLocation(entityGrid);

        int x = currentLoc[0];
        int y = currentLoc[1];

        if (0 == direction) {

            // Move player entity
            entityGrid[x][y-1] = this;
            entityGrid[x][y] = null;

            return entityGrid;

        } else if (1 == direction) {

            // Move player entity
            entityGrid[x+1][y] = this;
            entityGrid[x][y] = null;

            return entityGrid;

        } else if (2 == direction) {

            // Move player entity
            entityGrid[x][y+1] = this;
            entityGrid[x][y] = null;

            return entityGrid;

        } else if (3 == direction) {

            // Move player entity
            entityGrid[x-1][y] = this;
            entityGrid[x][y] = null;

            return entityGrid;

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

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

}