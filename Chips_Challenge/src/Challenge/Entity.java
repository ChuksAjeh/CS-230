package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Entity {

    private EntityType entityType;
    private Image sprite;
    private boolean collectible;

    public enum EntityType {
        PLAYER, SMART_ENEMY, DUMB_ENEMY, WALL_ENEMY, LINE_ENEMY, KEY, TOKEN, FIRE_BOOTS, FLIPPERS
    }

    public Entity(EntityType entityType, boolean collectible, Image image) {
        this.entityType = entityType;
        this.sprite = image;
        this.collectible = collectible;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public int[] findEntity(Entity entity, Entity[][] entityGrid) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                if (entityGrid[x][y] == entity) {
                    return new int[] {x, y};
                }

            }
        }

        return new int[] {0, 0};

    }

    public Image getSprite() {
        return sprite;
    }
}