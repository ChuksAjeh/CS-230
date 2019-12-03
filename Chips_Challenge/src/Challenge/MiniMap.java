package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

class MiniMap {

    private static int GRID_CELL_WIDTH ;
    private static int GRID_CELL_HEIGHT;

    void drawGame(Level level, Canvas canvas) {

        // Because it's logical
        assert null != level;

        int xMap = level.getCellGrid().length - 1;
        int yMap = level.getCellGrid()[xMap].length - 1;

        GRID_CELL_WIDTH = (int)(canvas.getWidth() / xMap);
        GRID_CELL_HEIGHT = (int)(canvas.getHeight() / yMap);

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Does this need a comment? method names should infer their purpose
        //int[] offset = this.calculateOffSet(level, canvas);

        // Render stuff
        this.renderBackground(gc, canvas);
        this.renderCellGrid(gc, level.getCellGrid());
        this.renderEntityGrid(gc, level.getEntityGrid());

        //save.saveFile(level);

        // Log Stuff - uncomment for spam
        // jack.logPlayerLoc(player, entityGrid);
        // jack.logGrid(level.getEntityGrid());
        // jack.logGrid(level.getCellGrid());

    }

    private void renderBackground(GraphicsContext gc, Canvas canvas) {

        Image backing = new Image("images/BACKING.png");
        backing = SpriteConverter.resize(backing, (int) canvas.getHeight(), (int) canvas.getWidth());
        gc.drawImage(backing, 0, 0);

        // gc.drawImage(SpriteConverter.resize(new Image("images/BACKING.png"), (int) canvas.getHeight(), (int) canvas.getWidth()), 0, 0);

    }

    private void renderCellGrid(GraphicsContext gc, Cell[][] cellGrid) {

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                Cell cell = cellGrid[x][y];
                Image sprite = SpriteConverter.resize(cell.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

                gc.drawImage(sprite, (x * GRID_CELL_WIDTH), (y * GRID_CELL_HEIGHT) );

            }
        }

    }

    private void renderEntityGrid(GraphicsContext gc, Entity[][] entityGrid) {

        int xOnScreen;
        int yOnScreen;

        int[] position;

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0; y < entityGrid[x].length; y++ ) {

                if (null != entityGrid[x][y]) {

                    xOnScreen = x * GRID_CELL_WIDTH;
                    yOnScreen = y * GRID_CELL_HEIGHT;

                    position = new int[] {xOnScreen, yOnScreen};

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

//            if (((Player) entity).getDirection() == 3) {
//                gc.drawImage(sprite, x + GRID_CELL_WIDTH, y, 0 - GRID_CELL_WIDTH, GRID_CELL_HEIGHT);
//            } else {
//                gc.drawImage(sprite, x, y);
//            }

            gc.drawImage(SpriteConverter.rotate(sprite, ((Player) entity).getDirection()), x, y);

        }/* else if (entity.getClass().getSimpleName().contains("Enemy")) {
            Enemy enemy = (Enemy) entity;
            gc.drawImage(SpriteConverter.rotate(sprite, enemy.getDirection()), x, y);
        } else {
            gc.drawImage(sprite, x, y);
        }
        */
    }
}
