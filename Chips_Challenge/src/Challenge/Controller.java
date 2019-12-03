package Challenge;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is designed to be used to allows the player to be controlled
 * by the user. It allows the input of arrow keys in order to move the player.
 * @author George Carpenter
 * @version 1.0
 */
class Controller {

    private static boolean changeMenu = true;
    private static boolean changeInventory = true;

    /**
     * Takes in certain inputs and outputs player actions.
     * @param event The event to be read.
     * @param level The level being played
     * @param game The game to be altered and re-rendered.
     * @param canvas The canvas for rendering the game.
     */
    void processKeyEvent(KeyEvent event, Level level, Game game, Canvas canvas, Scene after, Scene die) {

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
        }

        ArrayList<Enemy> enemies = level.getEnemies(newGrid);

        // Move all the enemies after the player has moved.
        for (Enemy e : enemies) {

//            for (Cell[] row : level.getCellGrid()) {
//                for (Cell c : row) {
//                    System.out.println(c.getClass().getSimpleName());
//                }
//            }

            // I am aware sequential if blocks are bad, however these  blocks
            // are checking for different 'end conditions' so I'm happy for
            // them to co-exist in their own bubble of code - Gnome

            if (level.getPlayer() != null) {
                // No Player, they be dead

                // Update Grids
                e.setCellGrid(level.getCellGrid());
                e.setEntityGrid(newGrid);
            }

            if (e instanceof DumbEnemy) {

                // TODO : Fix this shit - Gnome

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

            if(player.getGameStatus()) {
                player.setGameStatus();
                Main.window.setScene(after);
            }

            if (player.getStatus() && level.getPlayer() != null) {
                // Player should be alive
                // System.out.println("ALIVE");
                game.drawGame(level, canvas);
            } else {
                // System.out.println("DEAD");
                Main.level = new Level(level.getLevelName());
                Main.window.setScene(die);
            }

        }

        // Nom nom, lovely grub
        event.consume();
    }

    void processMenuEvent(KeyEvent event, StackPane root) {
         if (KeyCode.ESCAPE == event.getCode()) {
            if(changeMenu) {
                root.lookup("#pauseMenu").toFront();
                root.lookup("#Inventory").toBack();
                changeMenu = false;
            }
            else {
                root.lookup("#game").toFront();
                root.lookup("#pauseMenu").toBack();
                changeMenu = true;
            }

        } else if (KeyCode.E == event.getCode()) {
            // Open the inventory, eventually
            //root.lookup("#Inventory").toBack();

            // THIS LINE SHOULD UPDATE THE INVENTORY DYNAMICALLY
            //root.getChildren().set(0, main.INVENTORY(level));

            if(changeInventory) {
                root.lookup("#Inventory").toFront();
                root.lookup("#pauseMenu").toBack();
                changeInventory = false;
            }
            else {
                root.lookup("#game").toFront();
                root.lookup("#Inventory").toBack();
                changeInventory = true;
            }
        }
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