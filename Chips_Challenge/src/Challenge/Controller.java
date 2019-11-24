package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.FileNotFoundException;

public class Controller {

    public void processKeyEvent(KeyEvent event, Level level, Player player, Game game, Canvas canvas) {

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
            System.out.println("Adios Amigo!");
            System.exit(0);
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

    public Level makeLevel(String levelName) {

        // Build a Level thing
        Level level = null;

        try {
            level = new Level(levelName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return level;

    }

}
