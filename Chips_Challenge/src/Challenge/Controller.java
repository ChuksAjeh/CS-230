package Challenge;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * This class is designed to be used to allows the player to be controlled
 * by the user. It allows the input of arrow keys in order to move the player.
 * @author George Carpenterm, Ioan Mazurca
 * @version 1.0
 */
class Controller {

    /**
     * Tracks scene change
     */
    private static boolean changeMenu = true;

    /**
     * Tracks inventory change
     */
    private static boolean changeInventory = true;

    /**
     * Takes in certain inputs and outputs player actions.
     * @param event The event to be read.
     * @param level The level being played
     * @param game The game to be altered and re-rendered.
     * @param canvas The canvas for rendering the game.
     * @param scenes The scenes to manage with this handler
     */
    void processKeyEvent(KeyEvent event, Level level, Game game, Canvas canvas, Scene[] scenes) {

        // Retrieve Scenes
        Scene success = scenes[0];
        Scene die = scenes[1];

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

            if (level.getPlayer() != null && player.getStatus()) {
                // Player isn't dead

                // Update Grids
                e.setCellGrid(level.getCellGrid());
                e.setEntityGrid(newGrid);

            }

            newGrid = e.move(level, newGrid);

        }

        // Redraw the level with new positions.
        if (event.getCode().isArrowKey()) {

            level.setEntityGrid(newGrid);

            if(player.getGameStatus()) {
                player.setGameStatus();
                Main.end = System.nanoTime();
                Main.elapsedTime = Main.end - Main.start;
                Main.convert = TimeUnit.SECONDS.convert(Main.elapsedTime, TimeUnit.NANOSECONDS);
                System.out.println(Main.convert);
                Main.window.setScene(success);
            }

            if (player.getStatus() && level.getPlayer() != null) {
                // Player should be alive
                game.drawGame(level, canvas);
            } else {
                Main.level = new Level(level.getLevelName());
                Main.window.setScene(die);
            }

        }

        event.consume();
    }

    /**
     * Handles Menu events
     * @param event the events to respond to
     * @param root the stackPane to replace / remove
     */
    void processMenuEvent(KeyEvent event, StackPane root) {

        if (KeyCode.ESCAPE == event.getCode()) {
            root.getChildren().get(0).toFront();

        } else if (KeyCode.E == event.getCode()) {

             // Open the inventory, eventually
             // root.lookup("#Inventory").toBack();

             // THIS LINE SHOULD UPDATE THE INVENTORY DYNAMICALLY
             // root.getChildren().set(0, main.INVENTORY(level));

            if (changeInventory) {
                 root.lookup("#Inventory").toFront();
                 root.lookup("#pauseMenu").toBack();
                 changeInventory = false;
            } else {
                root.lookup("#game").toFront();
                root.lookup("#Inventory").toBack();
                changeInventory = true;
            }

        }
    }

    /**
     * Processes the Mini map
     * @param event What makes it appear
     * @param level the level to render
     * @param mini the mini map object
     * @param miniMapCanvas the canvas to render it to
     */
    void processMiniMap(KeyEvent event, Level level, MiniMap mini, Canvas miniMapCanvas) {
        mini.drawGame(level, miniMapCanvas);
        event.consume();
    }

    /**
     * Method to help reset the level and make a new level
     * @param levelName The level's name.
     * @return A new level with the inputted name
     */
    Level makeLevel(String levelName) {
        return new Level(levelName);
    }

}