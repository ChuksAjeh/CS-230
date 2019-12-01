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
        } else if (KeyCode.RIGHT == event.getCode()) {
            newGrid = player.move(1, level);
        } else if (KeyCode.DOWN == event.getCode()) {
            newGrid = player.move(2, level);
        } else if (KeyCode.LEFT == event.getCode()) {
            newGrid = player.move(3, level);
        } else if (KeyCode.ESCAPE == event.getCode()) {
            root.getChildren().get(0).toFront();
        } else if (KeyCode.E == event.getCode()) {
            // Open the inventory, eventually
        }

        ArrayList<Enemy> enemies = level.getEnemies(newGrid);

        for (Enemy e : enemies) {

            e.setCellGrid(level.getCellGrid());
            e.setEntityGrid(newGrid);

            if (level.getPlayer() == null) {
                // No Player, they be dead
                return;
            }

            // I am aware sequential if blocks are bad, however these two
            // blocks are checking for different 'end conditions' so I'm
            // happy for them to co-exist in their own bubble of code - Gnome

            if (Arrays.equals(e.getCells(), new boolean[] {false, false, false, false})) {
                // Enemy cannot move, they be surrounded
                return;
            }

            newGrid = e.move(level, newGrid);

        }

        if (event.getCode().isArrowKey()) {
            level.setEntityGrid(newGrid);

            if (player.getStatus() && level.getPlayer() != null) {
                // Player should be alive
                System.out.println("ALIVE");
                game.drawGame(level, canvas);
            } else {
                System.out.println("DEAD");
                Main.level = new Level(level.getLevelName());
                game.drawGame(Main.level, canvas);
            }

        }

        // Nom nom, lovely grub
        event.consume();
    }

    Level makeLevel(String levelName) {

        // Build a Level thing
        return new Level(levelName);
    }

}
