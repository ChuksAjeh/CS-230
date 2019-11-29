package Challenge;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Controller {

    private final Lumberjack jack = new Lumberjack();


    public void processKeyEvent(KeyEvent event, Level level, Player player, Game game, Canvas canvas, StackPane root) {

        Entity[][] newGrid;


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
        } /*else if (KeyCode.E == event.getCode()) {

        }*/

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

    public Level makeLevel(String levelName) {

        // Build a Level thing
        return new Level(levelName);
    }

}
