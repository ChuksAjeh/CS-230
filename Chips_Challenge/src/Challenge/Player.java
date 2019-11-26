package Challenge;

import javafx.scene.image.Image;

import javafx.scene.paint.Color;
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
    private boolean alive;

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
        this.alive = true;
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

                    this.addItem((Item) entityGrid[newX][newY], level);

                    jack.log("FOUND ITEM");
                    jack.log(getInventory().toString());

                } else if (entity.getClass().getSimpleName().contains("Enemy")) {

                    // DEATH .. also #reset

                    jack.log(1, "Kill me");

                    killPlayer();

                    return entityGrid;

                }
            } else if (cell instanceof KeyDoor) {

                Color doorColour = ((KeyDoor) cell).getColour();

                jack.log(1, "Walking into a KeyDoor");

                if (checkKeyColourInInv(doorColour)) {
                    openKeyDoor(level, doorColour, newX, newY);
                } else {
                    return entityGrid;
                }

            } else if (cell instanceof TokenDoor) {

                TokenDoor currentDoor = (TokenDoor) cell;

                jack.log(1, "Walking into a TokenDoor - Requirement: " + currentDoor.getRequirement());
                jack.log(1, "Current Tokens " + this.tokenCount);

                if (this.tokenCount >= currentDoor.getRequirement()) {
                    openTokenDoor(level, currentDoor, newX, newY);
                } else {
                    return entityGrid;
                }

            } else if (cell instanceof Teleporter) {

                jack.log(1, "Walking into a Teleporter. ");

                Teleporter pair = ((Teleporter) cell).getPair();

                int[] pairLocation = findTeleporterPair(cellGrid, pair);

                newX = pairLocation[0];
                newY = pairLocation[1];

                // Not yet implemented

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

        int[] locations = new int[] {x, y, x, y};

        if (0 == direction) {
            locations = new int[] {x, y, x, y-1};
        } else if (1 == direction) {
            locations = new int[] {x, y, x+1, y};
        } else if (2 == direction) {
            locations = new int[] {x, y, x, y+1};
        } else if (3 == direction) {
            locations = new int[] {x, y, x-1, y};
        }

        this.direction = direction;

        return movePlayerEntity(locations, level);
    }

    public int[] getLocation(Entity[][] entityGrid) {

        // find player in entity grid

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                Entity entity = entityGrid[x][y];

                if (entity instanceof Player) {
                    // Player is found
                    return new int[] {x, y};
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

    private void addItem(Item item, Level level) {

        if (item instanceof Token) {

            this.tokenCount += 1;

            jack.log(1, "Current tokens: " + tokenCount);

            if (checkTokenInInv()) {
                return;
            }

        } else if (item instanceof FireBoots || item instanceof Flippers) {

            setCellsPassable(level, item);

        }

        inventory.add(item);
    }

    private void openKeyDoor(Level level, Color doorColour, int newX, int newY) {

        Cell[][] cellGrid = level.getCellGrid();

        jack.log(1, "Player has the correct key");

        this.inventory.remove(findKeyColour(doorColour));

        cellGrid[newX][newY] = new Ground();

        level.setCellGrid(cellGrid);

    }

    private void openTokenDoor(Level level, TokenDoor door, int newX, int newY) {

        Cell[][] cellGrid = level.getCellGrid();

        jack.log(1,"Opening token door");

        cellGrid[newX][newY] = new Ground();

        level.setCellGrid(cellGrid);

        this.removeTokens(door.getRequirement());

    }

    private void setCellsPassable(Level level, Item item) {

        Cell[][] cellGrid = level.getCellGrid();

        String cellType = item instanceof FireBoots ? "Fire" : "Water";

        for (Cell[] row : cellGrid) {
            for (Cell c : row) {
                if (cellType.equals(c.getClass().getSimpleName())) {
                    c.setPassable(true);
                }
            }
        }

        jack.log(1, cellType + " set to passable");

    }

    private boolean checkKeyColourInInv(Color color){

        for (Item item : this.inventory) {
            if (item instanceof Key) {

                Key currentKey = (Key) item;

                if (color.equals(currentKey.getColour())) {
                    return true;
                }

            }
        }

        return false;
    }

    private Item findKeyColour(Color color) {

        Item returnItem = null;

        for (Item item : this.inventory) {
            if (item instanceof Key) {

                Key currentKey = (Key) item;

                if (color.equals(currentKey.getColour())) {
                    returnItem = item;
                }
            }
        }

        return returnItem;
    }

    private boolean checkTokenInInv() {

        for (Item item : inventory) {

            if (item instanceof Token) {
                return true;
            }

        }

        return false;
    }

    private void removeTokens(int amount) {
        if (checkTokenInInv()) {
            jack.log(1, "Can remove tokens");
            int newTokenCount = this.tokenCount - amount;

            if (newTokenCount < 0) {
                jack.log(1, "Don't have enough tokens");
            } else if (0 == newTokenCount) {
                this.tokenCount = newTokenCount;
                removeTokenFromInv();
            } else {
                this.tokenCount = newTokenCount;
            }

        } else {
            jack.log(1, "Can't remove tokens!");
        }
    }

    private void removeTokenFromInv(){
        Item currentItem = null;
        for (Item item : this.inventory) {
            if (item instanceof Token) {
                currentItem = item;
            }
        }

        removeItem(currentItem);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

    public int[] findTeleporterPair(Cell[][] cellGrid, Teleporter pair) {

        // find teleporter pair in cell grid

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                if (pair == cellGrid[x][y]) {
                    // Pair is found
                    return new int[] {x, y};
                }

            }
        }

        return new int[] {0, 0};

    }

    public boolean getStatus() {
        return this.alive;
    }

    private void killPlayer() {
        this.alive = false;
    }

}