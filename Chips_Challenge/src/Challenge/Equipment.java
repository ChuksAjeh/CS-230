package Challenge;
import javafx.scene.image.Image;

/**
 * An equipment is a items that is permanently in a players inventory,
 * allowing the player to traverse through hazards like fire.
 * @author Angelo
 * @version 1.0
 */
abstract class Equipment extends Item {

    /**
     * Constructs a equipment object.
     * @param SPRITE The sprite for that specific equipment.
     */
    public Equipment(Image sprite) {
        super(sprite);
        // Unlike standard items, equipment cannot be consumed upon use.
        this.consumable = false;
    }

}