package Challenge;

import java.util.ArrayList;
/**
 * @author ..
 * @version 1.0
 */
public class Player extends Entity {

    private ArrayList<Item> inventory;

    public Player() {
        this.inventory = new ArrayList<>();
    }

    public int[] move() {
        return null;
    }

    public int[] getLocation(Entity[][] entityGrid) {

        // find player in entity grid

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                if (entityGrid[x][y].getType() == Type.PLAYER) {

                    // Player is found
                    return new int[] {x, y};

                }

            }
        }

        return null;

    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

}