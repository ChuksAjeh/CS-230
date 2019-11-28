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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

        VBox menu = new VBox();
        menu.getChildren().addAll(startButton, users, quit);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        startButton.setOnAction(e -> window.setScene(gaming("Level_01")));

        users.setOnAction(e -> window.setScene(profileMenu(window)));

        quit.setOnAction(e -> System.exit(0));


        Scene firstMenu = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);


        return firstMenu;
    }

    private Scene profileMenu(Stage window) {
        BorderPane root = new BorderPane();

        Button selectProfile = new Button("Select Profile");

        EditableButton creatProfile = new EditableButton("Create Profile");

        Button selectLevel = new Button("Select Level");

        Button back = new Button("Back");

        VBox menu = new VBox();
        menu.getChildren().addAll(selectProfile, creatProfile, selectLevel, back);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);


        /*creatProfile.setOnMouseClicked(e -> {

        });*/

        back.setOnAction(e -> window.setScene(mainMenu(window)));

        selectProfile.setOnAction(e -> window.setScene(displayUsers()));

        selectLevel.setOnAction(e -> window.setScene(displayLevel(window)));

        Scene secondMenu = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return secondMenu;
    }

    private Scene displayUsers(){
        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        ArrayList<Button> buttons=new ArrayList<>();

        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users");
        File[] files = path.listFiles();

        for(int i=0; i < files.length;i++){
            buttons.add(new Button(files[i].getName()));
            menu.getChildren().add(buttons.get(i));
        }

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        for(Button button : buttons){
            button.setOnAction(e -> window.setScene(displayLevel(window)));
        }


        Scene displayUsers = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return displayUsers;
    }


    private Scene displayLevel(Stage window) {
        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        ArrayList<Button> buttons=new ArrayList<>();

        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        File[] files = path.listFiles();

        for(int i=0; i < files.length;i++){
            buttons.add(new Button(files[i].getName()));
            menu.getChildren().add(buttons.get(i));
        }

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        for (Button button : buttons) {
            button.setOnAction(e -> {
                String levelName = button.getText();
                levelName = levelName.substring(0, levelName.length()-4);
                window.setScene(gaming(levelName));
            });
        }




        Scene levels = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return levels;
    }


    private Scene gaming(String name) {
        BorderPane root = new BorderPane();

        System.out.println("SUCCESS!");

        root.setBottom(bottomBar());

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

        level = controller.makeLevel(name);

        game.drawGame(level, canvas);

        Scene play = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        play.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.processKeyEvent(event, level, player, game, canvas));

        return play;
    }


    class EditableButton extends Button {
        TextField tf = new TextField();

        public EditableButton(String text) {
            setText(text);
            setOnMouseClicked(e -> {
                //tf.setText(getText());
                setText("");
                setGraphic(tf);
            });

            tf.setOnAction(ae -> {
                File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users\\"+tf.getText());
                path.mkdir();

                window.setScene(displayUsers());
            });
        }
    }

}
