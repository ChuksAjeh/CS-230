package Challenge;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

class Game {

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

        int boundWidth = 0 - GRID_CELL_WIDTH / 2;
        int boundHeight = 0 - GRID_CELL_HEIGHT / 2;

        Image backing = new Image("images/BACKING.png");

        for (int x = boundWidth ; x < canvas.getWidth() - boundWidth ; x += GRID_CELL_WIDTH) {
            for (int y = boundHeight ; y < canvas.getHeight() - boundHeight ; y += GRID_CELL_HEIGHT) {

                gc.drawImage(backing, x, y);

            }
        }

    }

    private void renderCellGrid(GraphicsContext gc, Cell[][] cellGrid, int[] offset) {

        int xOffset = offset[0];
        int yOffset = offset[1];

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                Cell cell = cellGrid[x][y];
                Image sprite = resize(cell.getSprite());

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

        Image sprite = resize(entity.getSprite());

        if (entity.getClass().getSimpleName().equals("Player")) {
            gc.drawImage(rotate(sprite, ((Player) entity).getDirection()), x, y);
        } else if (entity.getClass().getSimpleName().contains("Enemy")) {
            Enemy enemy = (Enemy) entity;
            gc.drawImage(rotate(sprite, enemy.getDirection()), x, y);
        } else {
            gc.drawImage(sprite, x, y);
        }

    }

    private Image resize(Image image) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Resize
        imageView.setFitHeight(Game.GRID_CELL_HEIGHT);
        imageView.setFitWidth(Game.GRID_CELL_WIDTH);

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
