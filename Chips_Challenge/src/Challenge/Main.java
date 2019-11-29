package Challenge;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
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
    private final Player player = new Player(0);
    private final Controller controller = new Controller();
    Lumberjack jack = new Lumberjack();
    private final Game game = new Game();
    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        window = primaryStage;

        Scene intro = mainMenu(window);

        window.setTitle("Jungle Hunt");
        window.setScene(intro);
        window.show();
    }

    private Label messageOfTheDay() {

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


    private HBox bottomBar(){

        HBox bottomBar = new HBox();

        bottomBar.setPrefHeight(36);
        bottomBar.setPadding(new Insets(10, 10, 10, 10));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.getChildren().add(messageOfTheDay());
        bottomBar.setStyle("-fx-background-color: #222222");
        bottomBar.setMinHeight(36);

        return bottomBar;
    }

    private Scene mainMenu(Stage window) {

        BorderPane root = new BorderPane();

        Button startButton = new Button("Start!");
        Button users = new Button ("Profiles");
        Button quit = new Button ("Exit");

        Button test = new Button ("Test");

        VBox menu = new VBox();
        menu.getChildren().addAll(startButton, users, quit, test);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        startButton.setOnAction(e -> window.setScene(gaming("Level_01")));

        users.setOnAction(e -> window.setScene(profileMenu(window)));

        quit.setOnAction(e -> System.exit(0));

        test.setOnAction(e -> window.setScene(new Scene(pauseMenu(), WINDOW_WIDTH, WINDOW_HEIGHT)));

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private Scene profileMenu(Stage window) {

        BorderPane root = new BorderPane();

        Button selectProfile = new Button("Select Profile");
        EditableButton createProfile = new EditableButton("Create Profile");
        Button selectLevel = new Button("Select Level");
        Button back = new Button("Back");

        VBox menu = new VBox();
        menu.getChildren().addAll(selectProfile, createProfile, selectLevel, back);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        /*creatProfile.setOnMouseClicked(e -> {

        });*/

        back.setOnAction(e -> window.setScene(mainMenu(window)));
        selectProfile.setOnAction(e -> window.setScene(displayUsers()));
        selectLevel.setOnAction(e -> window.setScene(displayLevel(window)));

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private Scene displayUsers(){

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

//        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users");
        File path = new File("Users/");

        File[] files = path.listFiles();

        ArrayList<Button> buttons = makeButtons(Objects.requireNonNull(files), menu);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        for(Button button : buttons) {
            button.setOnAction(e -> window.setScene(displayLevel(window)));
        }

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private ArrayList<Button> makeButtons(File[] files, VBox menu) {

        ArrayList<Button> buttons = new ArrayList<>();

        for (int i = 0 ; i < files.length ; i++ ) {

            buttons.add(new Button(files[i].getName()));
            menu.getChildren().add(buttons.get(i));

        }

        return buttons;

    }

    public Scene displayLevel(Stage window) {

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

//        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        File path = new File("Level_Files/");

        File[] files = path.listFiles();

        ArrayList<Button> buttons = makeButtons(Objects.requireNonNull(files), menu);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        for (Button button : buttons) {
            button.setOnAction(e -> {
                String levelName = button.getText();
                levelName = levelName.substring(0, levelName.length() - 4);
                window.setScene(gaming(levelName));
            });
        }

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    }


    private BorderPane pauseMenu(){
        VBox vBox = new VBox();

        BorderPane middleMenu = new BorderPane();

        middleMenu.setPrefSize(200,200);

        Button save = new Button("Save");
        save.setPrefSize(200,50);
        Button goBack = new Button("Return to main menu");
        goBack.setPrefSize(200,50);
        Button exitGame = new Button("Exit");
        exitGame.setPrefSize(200,50);

        vBox.getChildren().addAll(save, goBack, exitGame);

        //vBox.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        vBox.setAlignment(Pos.CENTER);
        middleMenu.setCenter(vBox);

        goBack.setOnAction(e -> window.setScene(mainMenu(window)));

        exitGame.setOnAction(e -> System.exit(0));

        //vBox.setStyle("-fx-background-color: linear-gradient(to bottom, #006600 0%, #99ffcc 100%");

        //middleMenu.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        //middleMenu.getStyleClass().add("middleMenu");

        return middleMenu;
    }

    private Scene gaming(String name) {

        BorderPane root = new BorderPane();
        root.setPrefSize(960,670);

        BorderPane drawing = new BorderPane();
        drawing.setPrefSize(960,670);


        StackPane stack = new StackPane();
        //stack.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        System.out.println("SUCCESS!");


        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        drawing.setCenter(canvas);


        level = controller.makeLevel(name);

        game.drawGame(level, canvas);

        stack.getChildren().add(pauseMenu());
        stack.getChildren().add(drawing);

        root.setBottom(bottomBar());
        root.setCenter(stack);


        Scene play = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        play.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.processKeyEvent(event, level, player, game, canvas, stack));

        return play;

    }


    class EditableButton extends Button {

        final TextField tf = new TextField();

        EditableButton(String text) {

            setText(text);

            setOnMouseClicked(e -> {
                //tf.setText(getText());
                setText("");
                setGraphic(tf);
            });

            tf.setOnAction(ae -> {

//                File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users\\"+tf.getText());
                File path = new File("Users/" + tf.getText());

                path.mkdir();

                window.setScene(displayUsers());

            });
        }

    }

}
