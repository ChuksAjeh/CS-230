package Challenge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Controller controller = new Controller();
    Lumberjack jack = new Lumberjack();
    Game game = new Game();
    // TESTING

    public void start(Stage primaryStage) {

        Pane root = mainMenu();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

//        Level level = makeLevel("Test_File");
        level = controller.makeLevel("TEST_NUMERO_DOS");

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.processKeyEvent(event, level, player, game, canvas));

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

}
