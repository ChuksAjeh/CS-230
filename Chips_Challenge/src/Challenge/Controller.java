package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;

/** This class is designed to be used to allows the player to be controlled by the user. It allows the input of
 * arrow keys in order to move the player.
 * @author George Carpenter
 * @version 1.0
 */
class Controller {
    /**
     * Takes in certain inputs and outputs player actions.
     * @param event The event to be read.
     * @param level The level being played
     * @param game The game to be altered and re-rendered.
     * @param canvas The canvas for rendering the game.
     * @param root The StackPane holding the scene
     */
    public void processKeyEvent(KeyEvent event, Level level, Game game, Canvas canvas, StackPane root) {
        // Grab the player and current entity grid
        Entity[][] newGrid = level.getEntityGrid();
        Player player = level.getPlayer();
        // If arrow key is the UP arrow key (Look at ENUM for direction in these statements)..
        if (KeyCode.UP == event.getCode()) {
            newGrid = player.move(0, level);
            // Move to direction 0 (North)
            level.setEntityGrid(newGrid);

        } else if (KeyCode.RIGHT == event.getCode()) {
            // Move to direction 1 (East)
            newGrid = player.move(1, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.DOWN == event.getCode()) {
            // Move to direction 2 (South)
            newGrid = player.move(2, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.LEFT == event.getCode()) {
            ;// Move to direction 3 (West)
            newGrid = player.move(3, level);
            level.setEntityGrid(newGrid);
        } else if (KeyCode.ESCAPE == event.getCode()) {
            root.getChildren().get(0).toFront();
        } else if (KeyCode.E == event.getCode()) {
            // Open the inventory, eventually
        }

        ArrayList<Enemy> enemies = level.getEnemies(newGrid);
        // Move all the enemies after the player has moved.
        for (Enemy e : enemies) {

            e.setCellGrid(level.getCellGrid());
            e.setEntityGrid(newGrid);

            if (Arrays.equals(e.getCells(), new boolean[] {false, false, false, false})) {
                return;
            }

            newGrid = e.move(level, newGrid);

        }
        // Redraw the level with new positions.
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

    /** Method to help reset the level.
     * Makes a new level
     * @param levelName The level's name.
     * @return A new level with the inputted name
     */

    Level makeLevel(String levelName) {

        // Build a Level thing
        return new Level(levelName);
    }

}
