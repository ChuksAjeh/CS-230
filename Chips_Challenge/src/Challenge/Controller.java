package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Controller {

    void processKeyEvent(KeyEvent event, Level level, Game game, Canvas canvas, StackPane root) {

        Entity[][] newGrid = level.getEntityGrid();
        Player player = level.getPlayer();

        if (KeyCode.UP == event.getCode()) {
            newGrid = player.move(0, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.RIGHT == event.getCode()) {
            newGrid = player.move(1, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.DOWN == event.getCode()) {
            newGrid = player.move(2, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.LEFT == event.getCode()) {
            newGrid = player.move(3, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.ESCAPE == event.getCode()) {
            root.getChildren().get(0).toFront();
        } else if (KeyCode.E == event.getCode()) {
            // Open the inventory, eventually
        }

        ArrayList<Enemy> enemies = level.getEnemies(newGrid);

        for (Enemy e : enemies) {

            if (Arrays.equals(e.getCells(), new boolean[] {false, false, false, false})) {
                return;
            } else {
                e.setCellGrid(level.getCellGrid());
                e.setEntityGrid(newGrid);
                newGrid = e.move(level, newGrid);
            }

        }

        if (event.getCode().isArrowKey()) {
            game.drawGame(level, canvas);

//            if (player.getStatus()) {
//                game.drawGame(level, canvas);
//            } else if (!player.getStatus()) {
//
//                level = makeLevel(level.getLevelName());
//
//                game.drawGame(level, canvas);
//            }

        }

        // Consume the event. This means we mark it as dealt with.
        // This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    Level makeLevel(String levelName) {

        // Build a Level thing
        return new Level(levelName);
    }

}
