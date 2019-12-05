package Challenge;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

/**
 * This class is designed to be used to allows the player to be controlled
 * by the user. It allows the input of arrow keys in order to move the player.
 * @author George Carpenterm, Ioan Mazurca
 * @version 1.0
 */
class Controller {

    /**
     * Tracks inventory change
     */
    private static boolean changeInventory = true;

    /**
     * Saves the players scores after a level is completed
     */
    private final Save save = new Save();

    /**
     * The list of users who have played a level
     */
    private ArrayList<String> leaderboardUsers;

    /**
     * Takes in certain inputs and outputs player actions.
     * @param event The event to be read.
     * @param level The level being played
     * @param game The game to be altered and re-rendered.
     * @param canvas The canvas for rendering the game.
     * @param panes The panes that can be changed between as a result
     */
    void processKeyEvent(KeyEvent event, Level level, Game game, Canvas canvas, BorderPane[] panes) {

        // Get the event code
        KeyCode key = event.getCode();
        ArrayList<Integer> leaderboardScores;

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

                GUI.END_TIME = System.nanoTime();
                GUI.ELAPSED_TIME = GUI.END_TIME - GUI.START_TIME;
                GUI.CONVERTED_TIME = TimeUnit.SECONDS.convert(GUI.ELAPSED_TIME, TimeUnit.NANOSECONDS);
                GUI.scene.setRoot(panes[0]);
                Main.window.setScene(GUI.scene);

                game.getUser().addScore(level, (int) GUI.CONVERTED_TIME);
                this.save.saveProfile(game.getUser());

                leaderboardScores = calculateLeaderboardScores(level);
                ArrayList<Integer> sortedScores = bubbleSort(leaderboardScores);
                //AT THIS POINT SORTEDSCORES 0-2 ARE THE TOP 3 SCORES
                //AT THIS POINT LEADERBOARDUSERS 0-2 ARE THE USERS WITH THOSE SCORES

            }

            if (level.getPlayer() != null && player.getStatus()) {
                // Player should be alive
                game.drawGame(level, canvas);
            } else {
                GUI.LEVEL = new Level(level.getLevelName());
                GUI.scene.setRoot(panes[1]);
                Main.window.setScene(GUI.scene);
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
     * The scores for that level, ordered
     * @param level the level to collect scores for
     * @return the scores, ordered
     */
    private ArrayList<Integer> calculateLeaderboardScores (Level level) {

        ArrayList<Integer> userScores = new ArrayList<>();
        ArrayList<String> userNames = new ArrayList<>();
        File path = new File("Users/");

        File[] files = path.listFiles();
        assert files != null;

        for (File file : files) {
            File scoresFile = new File(file + "/scores.txt");

            userNames.add(file.getPath().substring(6));
            userScores.add(returnLevelScore(level, scoresFile));
        }

        this.leaderboardUsers = userNames;
        return userScores;
    }

    /**
     * Gets level scores
     * @param level the level to fetch scores for
     * @param file the file to get them from
     * @return the scores for that level
     */
    private int returnLevelScore (Level level, File file) {

        int levelNo = parseInt(level.getLevelName().substring(6));

        try {

            Scanner fileRead = new Scanner(file);

            IntStream.range(0, (levelNo - 1)).forEach(i ->
                fileRead.nextLine());

            return parseInt(fileRead.nextLine().split(" - ")[1]);

        } catch (Exception e) {

            Lumberjack.log(1, "return level score" + e);
            return 0;
        }
    }

    /**
     * My Bubbles!
     * @param scoresArray the array of scores to sort
     * @return the sorted array
     */
    private ArrayList<Integer> bubbleSort(ArrayList<Integer> scoresArray) {

        int n = scoresArray.size();

        for (int i = 0 ; i < n - 1 ; i ++ ) {
            for (int j = 0 ; j < n - i - 1 ; j ++ ) {

                if (scoresArray.get(j) > scoresArray.get(j + 1)) {
                    // swap arr[j+1] and arr[i]
                    scoresArray.set(j, scoresArray.get(j + 1));
                    scoresArray.set(j + 1, scoresArray.get(j));

                    this.leaderboardUsers.set(j, this.leaderboardUsers.get(j + 1));
                    this.leaderboardUsers.set(j + 1, this.leaderboardUsers.get(j));
                }

            }
        }

        return scoresArray;
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