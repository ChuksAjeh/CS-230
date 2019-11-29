package Challenge;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Player extends Entity {

    /**
     * The Sprite used for the Player object, it will be rotated based on direction
     */
    private static final Image SPRITE;

    /**
     * An array list used as the Player Inventory, items will be added and removed during gameplay
     */
    private ArrayList<Item> inventory;

    /**
     * The direction we should be displaying the player spite in
     */
    private int direction;

    /**
     * How many Tokens the Player is currently carrying
     */
    private int tokenCount;

    /**
     * Tracks whether or not the Player has been killed
     */
    private boolean alive;

    // TESTING
    private final Lumberjack jack = new Lumberjack();

    static  {
        SPRITE = new Image("images/ENTITY_PLAYER.png");
    }

    /**
     * Constructs a Player object
     * @param direction the direction the player is facing
     */
    public Player(int direction) {
        super(SPRITE);
        this.inventory = new ArrayList<>();
        this.direction = direction;
        this.tokenCount = 0;
        this.alive = true;
    }

    private Stage window;
    private static Main main;

    /**
     * Used to move the player object in the Entity grid
     * @param locations the current and potential new X and Y location of the player
     * @param level the current Level object, this contains both the Cell and Entity grids
     * @return the updated Entity grid for displaying to the screen
     */
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
                    jack.log(this.inventory.toString());

                } else if (entity.getClass().getSimpleName().contains("Enemy")) {

                    jack.log(1, "Kill me");

                    killPlayer();
                    return entityGrid;

                }

            } else if (cell instanceof KeyDoor) {

                Color doorColour = ((KeyDoor) cell).getColour();

                jack.log(1, "Walking into a KeyDoor");

                if (null != findKey(doorColour)) {
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

                Teleporter pair = ((Teleporter) cell).getPair();

                jack.log(1, "Walking into a Teleporter. ");

                int[] pairLocation = level.getLocation(cellGrid, pair);

                newX = pairLocation[0];
                newY = pairLocation[1];

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

    /**
     * Used to move the Player in the Entity grid
     * @param direction the direction the Player wishes to move
     * @param level the Level object in which they are moving
     * @return the updated Entity grid for displaying to the screen
     */
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

    /**
     * Used to find the Players current location in the Entity grid
     * @param entityGrid the Entity grid to search
     * @return the X and Y index of the Player object
     */
    public int[] getLocation(Entity[][] entityGrid) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                Entity entity = entityGrid[x][y];

                if (entity instanceof Player) {
                    // Player is found
                    return new int[] {x, y};
                }

            }
        }

        return new int[] {0,0};
    }

    /**
     * Used to track the current direction
     * @return the Players direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Used to add items to the Players inventory
     * This Method can also update some cells to passable
     * @param item the item to add to the players inventory
     * @param level the Level object, if it needs updating as a result
     */
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

    /**
     * Method to 'open' a key door and replace it with ground
     * @param level the level object
     * @param doorColour the colour of door
     * @param newX the X location of the door
     * @param newY the Y location of the door
     */
    private void openKeyDoor(Level level, Color doorColour, int newX, int newY) {

        Cell[][] cellGrid = level.getCellGrid();

        jack.log(1, "Player has the correct key");

        this.inventory.remove(findKey(doorColour));

        cellGrid[newX][newY] = new Ground();

        level.setCellGrid(cellGrid);

    }

    /**
     * Method to 'open' a token door and replace it with ground
     * @param level the level object
     * @param door the door object
     * @param newX the X location of the door
     * @param newY the Y location of the door
     */
    private void openTokenDoor(Level level, TokenDoor door, int newX, int newY) {

        Cell[][] cellGrid = level.getCellGrid();

        jack.log(1,"Opening token door");

        cellGrid[newX][newY] = new Ground();

        level.setCellGrid(cellGrid);

        this.removeTokens(door.getRequirement());

    }

    /**
     * Method to set cells as passable on item pickup
     * @param level the level object
     * @param item the item collected, this dictates what is made passable
     */
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

    /**
     * Method to find a key of a set colour
     * @param colour the colour to search for
     * @return the key, if present
     */
    private Item findKey(Color colour) {

        for (Item item : this.inventory) {
            if (item instanceof Key) {

                Key currentKey = (Key) item;

                if (colour.equals(currentKey.getColour())) {
                    return item;
                }
            }
        }

        return null;
    }

    /**
     * Checks if a Player is already carrying a token
     * @return true if a token exists
     */
    private boolean checkTokenInInv() {

        for (Item item : inventory) {

            if (item instanceof Token) {
                return true;
            }

        }

        return false;
    }

    /**
     * Checks if a Player has enough tokens
     * @param amount the amount of tokens
     */
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

    /**
     * Removes tokens
     */
    private void removeTokenFromInv(){
        Item currentItem = null;
        for (Item item : this.inventory) {
            if (item instanceof Token) {
                currentItem = item;
            }
        }

        removeItem(currentItem);
    }

    /**
     * Used to remove an item from the players inventory
     * @param item the item to remove
     */
    private void removeItem(Item item) {
        this.inventory.remove(item);
    }

    /**
     * Holds the Players alive status
     * @return true if they are alive
     */
    public boolean getStatus() {
        return this.alive;
    }

    /**
     * Kills the Player
     */
    private void killPlayer() {
        this.alive = false;
    }

}