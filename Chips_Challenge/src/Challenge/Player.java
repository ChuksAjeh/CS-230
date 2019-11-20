package Challenge;

import javafx.scene.image.Image;

import java.util.ArrayList;
/**
 * @author ..
 * @version 1.0
 */
public class Player extends Entity {

    private static final Image SPRITE;
    private ArrayList<Item> inventory;
    private int direction;
    private int tokenCount;

    // TESTING
    final Lumberjack jack = new Lumberjack();

    static  {
        SPRITE = new Image("images/ENTITY_PLAYER.png");
    }

    public Player(int direction) {
        super(SPRITE);
        this.inventory = new ArrayList<>();
        this.direction = direction;
        this.tokenCount = 0;
    }

    private Entity[][] movePlayerEntity(int[] locations, Level level) {

        Cell[][] cellGrid = level.getCellGrid();
        Entity[][] entityGrid = level.getEntityGrid();

        int x = locations[0];
        int y = locations[1];
        int newX = locations[2];
        int newY = locations[3];

        int width = entityGrid.length - 1;
        int height = entityGrid[0].length - 1;

        if (newX < 0 || newY < 0 || newX > width || newY > height) {
            jack.log(1, "Player out of bounds");
            return entityGrid;
        } else {

            Cell cell = cellGrid[newX][newY];
            Entity entity = entityGrid[newX][newY];

            if (null != entity) {

                if (entity instanceof Item) {

                    this.addItem((Item) entityGrid[newX][newY]);

                    jack.log("FOUND ITEM");
                    jack.log(this.getInventory().toString());

                } else if (entity.getClass().getSimpleName().contains("Enemy")) {

                    // DEATH .. also #reset

                    jack.log(1, "Kill me");

                }

            } else if (!cell.isPassable()) {

                jack.log(1, "Oof, you walked into a wall");

                return entityGrid;

            }

            // Move player entity

            // Remove the player from the grid
            entityGrid[x][y] = null;

            // Add the player at new location
            entityGrid[newX][newY] = this;

            return entityGrid;
        }

    }

    public Entity[][] move(int direction, Level level) {

        Entity[][] entityGrid = level.getEntityGrid();

        int[] currentLoc = this.getLocation(entityGrid);

        int x = currentLoc[0];
        int y = currentLoc[1];

        if (0 == direction) {

            this.direction = direction;
            return movePlayerEntity(new int[] {x, y, x, y-1}, level);

        } else if (1 == direction) {

            this.direction = direction;
            return movePlayerEntity(new int[] {x, y, x+1, y}, level);

        } else if (2 == direction) {

            this.direction = direction;
            return movePlayerEntity(new int[] {x, y, x, y+1}, level);

        } else if (3 == direction) {

            this.direction = direction;
            return movePlayerEntity(new int[] {x, y, x-1, y}, level);

        }

        return null;
    }

    public int[] getLocation(Entity[][] entityGrid) {

        // find player in entity grid

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                Entity entity = entityGrid[x][y];

                if (entity != null) {

                    if (entity.getClass().getSimpleName().equals("Player")) {
                        // Player is found
                        return new int[] {x, y};
                    }

                }
            }
        }
        return new int[] {0, 0};
    }

    public int getDirection() {
        return direction;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        if (!(item instanceof Token)) {
            inventory.add(item);
        } else if (checkTokenInInv()) {
            tokenCount++;
            jack.log(1, "Current tokens: " + tokenCount);
        } else {
            inventory.add(item);
            tokenCount++;
            jack.log(1, "Current tokens: " + tokenCount);
        }
    }

    private boolean checkTokenInInv() {
        for (Item item : inventory){
            if (item instanceof Token) {
                return true;
            }
        }
        return false;
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

}