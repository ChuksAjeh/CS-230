package Challenge;

import javafx.scene.image.Image;

/**
 * An item is used to help a player navigate throughout the level.
 * It can be collected, has its own sprite and is either consumable
 * or is a permanent addition to the player's inventory.
 * @author Angelo
 * @version %I%
 */
abstract class Item extends Entity {

    /**
     * Whether an item is consumable or not. All items are consumable unless it is an Equipment
     */
    protected boolean consumable = true;
    private final static boolean COLLECTIBLE;

    static {
        //All items are collectible.
        COLLECTIBLE = true;
    }

    /**
     * Constructs an item with its designated sprite.
     * @param sprite The sprite used to render the item.
     */
    public Item(Image sprite) {
        super(sprite);
    }

}