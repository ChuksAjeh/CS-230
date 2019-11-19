package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Token extends Item {

    private static final EntityType entityType;
    private static final Image sprite;
    private static final boolean collectible;

    static {
        entityType = EntityType.TOKEN;
        sprite = new Image("images/ENTITY_TOKEN.png");
        collectible = true;
    }

    public Token() {
        super(entityType, sprite, collectible);
    }

}