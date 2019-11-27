package Challenge;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

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


    Stage window;
    Scene scene1, scene2, scene3;


    public void start(Stage primaryStage) {

        window = primaryStage;


        //FIRST MENU

        Button startButton = new Button("Start!");
        Button users = new Button ("Profiles");
        Button quit = new Button ("Exit");
        BorderPane test = new BorderPane();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(startButton, users, quit);
        scene1 = new Scene(vbox, WINDOW_WIDTH, WINDOW_HEIGHT);



        startButton.setOnAction(e -> window.setScene(scene3));
        users.setOnAction(e -> window.setScene(scene2));
        quit.setOnAction(e -> System.exit(0));

        //SECOND MENU
        Button createProfile = new Button("Create Profile");
        Button selectProfile = new Button("Select Profile");
        Button back = new Button("Back");
        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(createProfile, selectProfile, back);
        scene2 = new Scene(vbox2, WINDOW_WIDTH, WINDOW_HEIGHT);

        back.setOnAction(e -> window.setScene(scene1));


        // GAME
        level = controller.makeLevel("Level_01");
        scene3 = new Scene(gaming(), WINDOW_WIDTH, WINDOW_HEIGHT);
        scene3.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.processKeyEvent(event, level, player, game, canvas));


        window.setScene(scene1);
        window.setTitle("game");
        window.show();
    }

/*
    private BorderPane mainMenu() {
        BorderPane firstMenu = new BorderPane();
        Button startButton = new Button("Start!");
        Button users = new Button ("Profiles");
        Button quit = new Button ("Exit");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(startButton, users, quit);

        firstMenu.setCenter(vbox);


    }
*/

    private BorderPane gaming() {
        BorderPane root = new BorderPane();

        System.out.println("SUCCESS!");

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        try {
            game.drawGame(level, canvas);
        } catch (IOException E) {
            jack.log(1,"MENU - IOException");
        }

        return root;
    }

}
