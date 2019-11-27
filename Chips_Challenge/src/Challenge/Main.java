package Challenge;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    // The dimensions of the window
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 960;
    private static final int CANVAS_HEIGHT = 684;

    private Canvas canvas;

    private static Level level;
    private Player player = new Player(0);
    private Controller controller = new Controller();
    Lumberjack jack = new Lumberjack();
    Game game = new Game();
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Pane root = mainMenu();
        window = primaryStage;

        level = controller.makeLevel("Level_01");
        Scene scene = new Scene(gaming(), WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.processKeyEvent(event, level, player, game, canvas));

        primaryStage.setTitle("Thing?");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Label messageOfTheDay(){
        Label message = new Label();
        AtomicReference<String> stuff = new AtomicReference<>(new Ping().getPing());
        message.textProperty().set(stuff.get());
        message.setTextFill(Color.rgb(200, 200, 200));

        Timeline timeline = new Timeline(
                new KeyFrame(new Duration(5000), e -> {
                    stuff.set(new Ping().getPing());
                    message.textProperty().set(stuff.get());
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        return message;
    }


    private BorderPane mainMenu() {

        BorderPane root = new BorderPane();

        Label message = new Label();

        Button startButton = new Button("Start!");
        Button users = new Button ("Profiles");
        Button quit = new Button ("Exit");
        BorderPane test = new BorderPane();
        VBox menu = new VBox();
        menu.getChildren().addAll(startButton, users, quit);


        /*startButton.setOnAction(e -> {

            canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
            root.setCenter(canvas);


            try {
                game.drawGame(level, canvas);
            } catch (IOException E) {
                jack.log(1,"MENU - IOException");
            }
        });
*/

        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);
        BorderPane.setAlignment(menu, Pos.CENTER);

        return root;

    }

    private HBox bottomBar(){
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10, 10, 10, 10));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.getChildren().add(messageOfTheDay());
        bottomBar.setStyle("-fx-background-color: #222222");
        bottomBar.setMinHeight(36);

        return bottomBar;
    }


    private BorderPane gaming() {
        BorderPane root = new BorderPane();

        System.out.println("SUCCESS!");

        root.setBottom(bottomBar());

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        game.drawGame(level, canvas);

        return root;
    }


}
