package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is designed to be used to allows the player to be controlled
 * by the user. It allows the input of arrow keys in order to move the player.
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
    void processKeyEvent(KeyEvent event, Level level, Game game, Canvas canvas, StackPane root) {

        // Grab the player and current entity grid
        Entity[][] newGrid = level.getEntityGrid();
        Player player = level.getPlayer();


        if (KeyCode.UP == event.getCode()) {
            // Move to direction 0 (North)
            newGrid = player.move(0, level);
        } else if (KeyCode.RIGHT == event.getCode()) {
            // Move to direction 1 (East)
            newGrid = player.move(1, level);
        } else if (KeyCode.DOWN == event.getCode()) {
            // Move to direction 2 (South)
            newGrid = player.move(2, level);
        } else if (KeyCode.LEFT == event.getCode()) {
            // Move to direction 3 (West)
            newGrid = player.move(3, level);
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

            // I am aware sequential if blocks are bad, however these  blocks
            // are checking for different 'end conditions' so I'm happy for
            // them to co-exist in their own bubble of code - Gnome

            if (level.getPlayer() == null) {
                // No Player, they be dead
                return;
            }

            if (e instanceof DumbEnemy) {

                if (!e.getCells()[e.nextDirection(level)]) {
                    // DumbEnemy trying to walk onto a wall
                    return;
                }

            } else if (Arrays.equals(e.getCells(), new boolean[] {false, false, false, false})) {
                // Enemy cannot move, they be surrounded
                return;
            }

            newGrid = e.move(level, newGrid);

        }

        // Redraw the level with new positions.
        if (event.getCode().isArrowKey()) {
            level.setEntityGrid(newGrid);

            if (player.getStatus() && level.getPlayer() != null) {
                // Player should be alive
                // System.out.println("ALIVE");
                game.drawGame(level, canvas);
            } else {
                // System.out.println("DEAD");
                Main.level = new Level(level.getLevelName());
                game.drawGame(Main.level, canvas);
            }

        }

        // Nom nom, lovely grub
        event.consume();
    }

    /** Method to help reset the level.
     * Makes a new level
     * @param levelName The level's name.
     * @return A new level with the inputted name
     */
    Level makeLevel(String levelName) {

        return new Level(levelName);

    }

}
