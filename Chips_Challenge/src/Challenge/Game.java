package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**Game is used to render Chips Challenge. We render the entire level with this class.
 * @author ..
 * @version
 */
public class Game {

    // The size of each cell
    private static final int GRID_CELL_WIDTH = 120;
    private static final int GRID_CELL_HEIGHT = 120;

    Lumberjack jack = new Lumberjack();
    private final Save save = new Save();

    void drawGame(Level level, Canvas canvas) {

        // Because it's logical
        assert null != level;

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Does this need a comment? method names should infer their purpose
        int[] offset = this.calculateOffSet(level, canvas);

        // Render stuff
        this.renderBackground(gc, canvas);
        this.renderCellGrid(gc, level.getCellGrid(), offset);
        this.renderEntityGrid(gc, level.getEntityGrid(), offset);

        save.saveFile(level);

        // Log Stuff - uncomment for spam
        // jack.logPlayerLoc(player, entityGrid);
        // jack.logGrid(level.getEntityGrid());
        // jack.logGrid(level.getCellGrid());

    }

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

    private void renderBackground(GraphicsContext gc, Canvas canvas) {

        Image backing = new Image("images/BACKING.png");
        backing = SpriteConverter.resize(backing, (int) canvas.getHeight(), (int) canvas.getWidth());
        gc.drawImage(backing, 0, 0);

        // gc.drawImage(SpriteConverter.resize(new Image("images/BACKING.png"), (int) canvas.getHeight(), (int) canvas.getWidth()), 0, 0);

    }

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

    private void renderEntity(GraphicsContext gc, Entity entity, int[] position) {

        int x = position[0];
        int y = position[1];

        Image sprite = SpriteConverter.resize(entity.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

        if (entity.getClass().getSimpleName().equals("Player")) {
            gc.drawImage(SpriteConverter.rotate(sprite, ((Player) entity).getDirection()), x, y);
        } else if (entity.getClass().getSimpleName().contains("Enemy")) {
            Enemy enemy = (Enemy) entity;
            gc.drawImage(SpriteConverter.rotate(sprite, enemy.getDirection()), x, y);
        } else {
            gc.drawImage(sprite, x, y);
        }

    }

}
