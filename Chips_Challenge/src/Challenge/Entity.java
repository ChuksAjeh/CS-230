package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
abstract class Entity {

    private final Image SPRITE;

    public Entity(Image sprite) {
        this.SPRITE = sprite;
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
        return SPRITE;
    }

}