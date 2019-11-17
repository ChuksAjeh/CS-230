package Challenge;
/**
 * @author ..
 * @version 1.0
 */
abstract class Entity {

    private EntityType entityType;

    public enum EntityType {
        PLAYER, SMART_ENEMY, DUMB_ENEMY, WALL_ENEMY, LINE_ENEMY, KEY, TOKEN, FIRE_BOOTS, FLIPPERS
    }

    public Entity(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }
}