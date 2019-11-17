package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Entity {

    private EntityType entityType;
    private Image sprite;

    public enum EntityType {
        PLAYER, SMART_ENEMY, DUMB_ENEMY, WALL_ENEMY, LINE_ENEMY, KEY, TOKEN, FIRE_BOOTS, FLIPPERS
    }

    public Entity(EntityType entityType, Image image) {
        this.entityType = entityType;
        this.sprite = image;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Image getSprite() {
        return sprite;
    }
}