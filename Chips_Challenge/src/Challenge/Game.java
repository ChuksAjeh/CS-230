package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Game is used to render Chips Challenge.
 * We render the entire level with this class.
 * @author Everyone basically
 * @version 9001
 */
class Game {

    /**
     * The cell width.
     */
    private static final int GRID_CELL_WIDTH = 120;
    /**
     * The cell height
     */
    private static final int GRID_CELL_HEIGHT = 120;
    //Debugger
    Lumberjack jack = new Lumberjack();
    /**
     * The save file for this game.
     */
    private final Save save = new Save();

    /**
     * The current user for this game
     */
    User user;

    public Game(String userName) {
        this.user = new User(userName);
        user.setScores(save.loadPlayerScores(userName));
    }

    /**
     *  Draws the game with a given level and canvas.
     * @param level The game's level
     * @param canvas The canvas to draw the game.
     */
    public void drawGame(Level level, Canvas canvas) {

        // Because it's logical
        assert null != level;

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Calculate the offset for use in rendering
        int[] offset = this.calculateOffSet(level, canvas);

        // Render stuff
        this.renderBackground(gc, canvas);
        this.renderCellGrid(gc, level.getCellGrid(), offset);
        this.renderEntityGrid(gc, level.getEntityGrid(), offset);

        //jack.log(2,"game" + this.user.getUserName());
        save.saveFile(level, this.user);

        // Log Stuff - uncomment for spam
        // jack.logPlayerLoc(player, entityGrid);
        // jack.logGrid(level.getEntityGrid());
        // jack.logGrid(level.getCellGrid());

    }

    /**
     * Calculates the offset for when you draw the game.
     * @param level The current level.
     * @param canvas The canvas used for the game.
     * @return The offset for the x coordinate and y coordinate as a pair represented by a int[].
     */
    private int[] calculateOffSet(Level level, Canvas canvas) {

        // Not 100% sure of this, it may change, please don't try to comment it

        Player player = level.getPlayer();
        Position playerPosition = player.getPosition();

        int playerXOffset = playerPosition.x * GRID_CELL_WIDTH + (GRID_CELL_WIDTH / 2);
        int playerYOffset = playerPosition.y * GRID_CELL_HEIGHT + (GRID_CELL_HEIGHT / 2);

        int levelXOffset = playerXOffset - (int) canvas.getWidth() / 2;
        int levelYOffset = playerYOffset - (int) canvas.getHeight() / 2;

//        return new int[] {0, 0};
        return new int[] {levelXOffset, levelYOffset};

    }

    /**
     * Renders the background for the game.
     * @param gc The graphics context for rendering.
     * @param canvas The canvas for the game.
     */
    private void renderBackground(GraphicsContext gc, Canvas canvas) {

        Image backing = new Image("images/BACKING.png");
        backing = SpriteConverter.resize(backing, (int) canvas.getHeight(), (int) canvas.getWidth());
        gc.drawImage(backing, 0, 0);

        // gc.drawImage(SpriteConverter.resize(new Image("images/BACKING.png"), (int) canvas.getHeight(), (int) canvas.getWidth()), 0, 0);

    }

    /**
     * Renders the cell grid.
     * @param gc The graphics context for rendering.
     * @param cellGrid The cell grid for the game to be rendered.
     * @param offset The offset needed to be taken into account.
     */
    private void renderCellGrid(GraphicsContext gc, Cell[][] cellGrid, int[] offset) {

        int xOffset = offset[0];
        int yOffset = offset[1];

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                Cell cell = cellGrid[x][y];
                Image sprite = SpriteConverter.resize(cell.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

                gc.drawImage(sprite, (x * GRID_CELL_WIDTH) - xOffset, (y * GRID_CELL_HEIGHT) - yOffset);

            }
        }

    }

    /**
     * Renders the entities in the game.
     * @param gc The graphics context for the game.
     * @param entityGrid The entities to be rendered.
     * @param offset The offset for the entities.
     */
    private void renderEntityGrid(GraphicsContext gc, Entity[][] entityGrid, int[] offset) {

        int xOnScreen;
        int yOnScreen;

        int[] position;

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0; y < entityGrid[x].length; y++ ) {

                if (null != entityGrid[x][y]) {

                    xOnScreen = x * GRID_CELL_WIDTH;
                    yOnScreen = y * GRID_CELL_HEIGHT;

                    position = new int[] {xOnScreen - offset[0], yOnScreen - offset[1]};

                    renderEntity(gc, entityGrid[x][y], position);
                }

            }
        }
    }

    /**
     * Auxillary method to help render the enemies
     * @param gc The graphics context to be used in the game.
     * @param entity The entity to be rendered.
     * @param position The position of the enemy.
     */
    private void renderEntity(GraphicsContext gc, Entity entity, int[] position) {

        int x = position[0];
        int y = position[1];

        Image sprite = SpriteConverter.resize(entity.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

        if (entity.getClass().getSimpleName().equals("Player")) {

//            if (((Player) entity).getDirection() == 3) {
//                gc.drawImage(sprite, x + GRID_CELL_WIDTH, y, 0 - GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
//            } else {
//                gc.drawImage(sprite, x, y);
//            }

            gc.drawImage(SpriteConverter.rotate(sprite, ((Player) entity).getDirection()), x, y);

        } else if (entity.getClass().getSimpleName().contains("Enemy")) {
            Enemy enemy = (Enemy) entity;
            gc.drawImage(SpriteConverter.rotate(sprite, enemy.getDirection()), x, y);
        } else {
            gc.drawImage(sprite, x, y);
        }

    }

    public User getUser() {
        return user;
    }

}
