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

    public int[] getLocation() {
        return null;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

}