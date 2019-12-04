package Challenge;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Arrays;
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

        // Get the event code
        KeyCode key = event.getCode();

        if (key.isArrowKey()) {

            // Grab the player, current entity grid and the enemy array
            Entity[][] newGrid;
            Player player = level.getPlayer();

            ArrayList<KeyCode> codes = new ArrayList<>(Arrays.asList(
                KeyCode.UP, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.LEFT
            ));

            newGrid = player.move(codes.indexOf(key), level);
            newGrid = level.moveEnemys(level, newGrid);

            // Update the Level object for drawing
            level.setEntityGrid(newGrid);

            if (player.getGameStatus()) {
                player.setGameStatus();
                Main.END_TIME = System.nanoTime();
                Main.ELAPSED_TIME = Main.END_TIME - Main.START_TIME;
                Main.CONVERTED_TIME = TimeUnit.SECONDS.convert(Main.ELAPSED_TIME, TimeUnit.NANOSECONDS);
                Main.WINDOW.setScene(scenes[0]);
            }

            if (level.getPlayer() != null && player.getStatus()) {
                // Player should be alive
                game.drawGame(level, canvas);
            } else {
                Main.LEVEL = new Level(level.getLevelName());
                Main.WINDOW.setScene(scenes[1]);
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
        mini.drawMap(level, miniMapCanvas);
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