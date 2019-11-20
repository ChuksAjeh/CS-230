package Challenge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Menu extends Application {

    // The dimensions of the window
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 960;
    private static final int CANVAS_HEIGHT = 720;

    // Level
    private static Level level;

    // Make a canvas
    private Canvas canvas;

    // TESTING
    private Player player = new Player(0);
    Lumberjack jack = new Lumberjack();
    Game game = new Game();
    // TESTING

    public void start(Stage primaryStage) {

        Pane root = mainMenu();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

//        Level level = makeLevel("Test_File");
        level = makeLevel("TEST_NUMERO_DOS");

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event, level, player));

        primaryStage.setTitle("Thing?");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private BorderPane mainMenu() {

        BorderPane root = new BorderPane();

        HBox bottomBar = new HBox();

        Button startButton = new Button("Start!");

//        bottomBar.setSpacing(10);
//        bottomBar.setPadding(new Insets(10, 10, 10, 10));

        bottomBar.getChildren().add(startButton);

        root.setCenter(bottomBar);

        startButton.setOnAction(e -> {
            System.out.println("SUCCESS!");

            canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
            root.setCenter(canvas);

            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            game.drawGame(level, canvas);

        });

        return root;

    }

    private void processKeyEvent(KeyEvent event, Level level, Player player) {

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
        }

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
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
