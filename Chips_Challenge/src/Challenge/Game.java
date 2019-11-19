package Challenge;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Game {

    // The size of each cell
    private static int GRID_CELL_WIDTH = 120;
    private static int GRID_CELL_HEIGHT = 120;

    private Cell[][] cellGrid;
    private Entity[][] entityGrid;

    Player player = new Player(0);
    Lumberjack jack = new Lumberjack();

    public void drawGame(Level level, Canvas canvas) {

        // Because it's logical
        assert null != level;

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // No comment.
        this.updateGrids(level);

        // Does this need a comment? method names should infer their purpose
        int[] offset = calculateOffSet(player, canvas);

        // Render grids
        this.renderCellGrid(gc, cellGrid, offset);
        this.renderEntityGrid(gc, entityGrid, offset);

        // Log Stuff - uncomment for spam
        // jack.logPlayerLoc(player, entityGrid);
        // jack.logCellGrid(cellGrid);
        // jack.logEntityGrid(entityGrid);

    }

    private int[] calculateOffSet(Player player, Canvas canvas) {

        // Not 100% sure of this, it may change, please don't try to comment it

        int[] playerLocOffset = player.getLocation(entityGrid);

        int playerXOffset = playerLocOffset[0] * GRID_CELL_WIDTH + (GRID_CELL_WIDTH / 2);
        int playerYOffset = playerLocOffset[1] * GRID_CELL_HEIGHT + (GRID_CELL_HEIGHT / 2);

        int levelXOffset = playerXOffset - (int) canvas.getWidth() / 2;
        int levelYOffset = playerYOffset - (int) canvas.getHeight() / 2;

        return new int[] {levelXOffset, levelYOffset};

    }

    private void updateGrids(Level level) {

        this.cellGrid = level.getCellGrid();
        this.entityGrid = level.getEntityGrid();

    }

    private void renderCellGrid(GraphicsContext gc, Cell[][] cellGrid, int[] offset) {

        int xOffset = offset[0];
        int yOffset = offset[1];

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                Cell cell = cellGrid[x][y];
                Image sprite = resize(cell.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

                gc.drawImage(sprite, (x * GRID_CELL_WIDTH) - xOffset, (y * GRID_CELL_HEIGHT) - yOffset);

            }
        }

    }

    private void renderEntityGrid(GraphicsContext gc, Entity[][] entityGrid, int[] offset) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0; y < entityGrid[x].length; y++ ) {

                if (null != entityGrid[x][y]) {
                    renderEntity(gc, x, y, offset);
                }

            }
        }
    }

    private void renderEntity(GraphicsContext gc, int x, int y, int[] offset) {

        Entity entity = entityGrid[x][y];

        int xOnScreen = x * GRID_CELL_WIDTH;
        int yOnScreen = y * GRID_CELL_HEIGHT;

        int xOffset = offset[0];
        int yOffset = offset[1];

        Image sprite = resize(entity.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

        if (Entity.EntityType.PLAYER == entity.getEntityType()) {
            this.player = (Player) entity;
            gc.drawImage(rotate(sprite, player.getDirection()), xOnScreen - xOffset, yOnScreen - yOffset);
        } else if (entity.getEntityType().toString().contains("ENEMY")) {
            Enemy enemy = (Enemy) entityGrid[x][y];
            gc.drawImage(rotate(sprite, enemy.getDirection()), xOnScreen - xOffset, yOnScreen - yOffset);
        } else {
            gc.drawImage(sprite, xOnScreen - xOffset, yOnScreen - yOffset);
        }

    }

    private Image resize(Image image, int height, int width) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Resize
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        // Capture it? I think
        SnapshotParameters param = new SnapshotParameters();

        param.setFill(Color.TRANSPARENT);

        return imageView.snapshot(param, null);

    }

    private Image rotate(Image image, int direction) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Rotate
        imageView.setRotate(90 * direction);

        // Capture it? I think
        SnapshotParameters param = new SnapshotParameters();

        param.setFill(Color.TRANSPARENT);

        return imageView.snapshot(param, null);
    }

}
