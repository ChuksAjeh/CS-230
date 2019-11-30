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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;

public class Main extends Application {

    // The dimensions of the window
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 960;
    private static final int CANVAS_HEIGHT = 684;

    private Canvas canvas;

    private static Level level;
    private final Player player = new Player(new Position(0, 0), 0);
    private final Controller controller = new Controller();
    Lumberjack jack = new Lumberjack();
    private final Game game = new Game();
    private Stage window;
    private static MediaPlayer mediaPlayer;

    public static void main(String[] args) { launch(args);}

    public void start(Stage primaryStage){
        window = primaryStage;
        Scene intro = begin(window);

        window.setTitle("Jungle Hunt");
        window.setScene(intro);
        window.show();

        try {
            Media media = new Media(Paths.get("music/background_music1.mp3").toUri().toString());
            this.mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(10);
            mediaPlayer.setVolume(0.2);
            mediaPlayer.play();
        } catch (Exception E) {
            jack.log(1,E.toString());
        }
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

    private Scene begin(Stage window) {
        BorderPane root = new BorderPane();


        Button startButton = new Button("START");
        startButton.setPrefSize(150,100);

        Label title = new Label("JUNGLE HUNT");


        root.setCenter(startButton);
        root.setAlignment(root.getCenter(), Pos.CENTER);

        root.setTop(title);
        root.setAlignment(root.getTop(), Pos.TOP_CENTER);

        root.setMargin(root.getCenter(), new Insets(0,0,150,0));

        root.setBottom(bottomBar());

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));


        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC,40));
        title.setEffect(dropShadow);


        startButton.setOnAction(e -> window.setScene(userSelection(window)));

        root.setStyle("-fx-background-color: linear-gradient(to bottom, #00cc00 0%, #003300 100%)");

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }


    private Scene userSelection(Stage window) {

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        EditableButton newUser = new EditableButton("Create user profile");

        ComboBox<String> loadUser = new ComboBox<>();

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users");
        File path = new File("Users/");


        File[] files = path.listFiles();

        for (int i = 0 ; i < files.length ; i++ ) {
            loadUser.getItems().add(files[i].getName());
        }


        loadUser.setPromptText("Select user profile");
        //Button loadUser = new Button("Load user profiles");


        loadUser.setOnAction(e -> window.setScene(loadGame(window)));

        Button quit = new Button("Quit");

        menu.getChildren().addAll(newUser, loadUser, quit);
        menu.setAlignment(Pos.CENTER);

        root.setCenter(menu);
        root.setBottom(bottomBar());

        quit.setOnAction(e -> System.exit(0));

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }


    private Scene newGame(Stage window) {
        BorderPane root = new BorderPane();

        VBox vBox = new VBox();

        Button startButton = new Button("New Game");

        vBox.getChildren().add(startButton);
        vBox.setAlignment(Pos.CENTER);

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        File path = new File("Level_Files/");
        File[] files = path.listFiles();

        assert files!=null;

        startButton.setOnAction(e -> {
            String levelName = files[0].getName();
            levelName = levelName.substring(0,levelName.length()-4);
            window.setScene(gaming(levelName));
        });

        root.setCenter(vBox);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }

    private Scene loadGame(Stage window) {
        BorderPane root = new BorderPane();

        VBox vBox = new VBox();


        Button startButton = new Button("New game");

        Button loadButton = new Button("Load game");

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        File path = new File("Level_files/");
        File[] files = path.listFiles();

        assert files!=null;

        startButton.setOnAction(e -> {
            String levelName = files[0].getName();
            levelName = levelName.substring(0,levelName.length()-4);
            window.setScene(gaming(levelName));
        });


        vBox.getChildren().addAll(startButton,loadButton);
        vBox.setAlignment(Pos.CENTER);

        root.setCenter(vBox);

        loadButton.setOnAction(e -> window.setScene(displayLevel(window)));

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return scene;
    }

    // TODO I'll delete this method, I just keep it for now for testing
    private Scene mainMenu(Stage window) {

        BorderPane root = new BorderPane();

        Button startButton = new Button("Start!");
        Button users = new Button ("Profiles");
        Button quit = new Button ("Exit");

        Button test = new Button ("Test");

        VBox menu = new VBox();
        menu.getChildren().addAll(startButton, users, quit, test);

        menu.setSpacing(50);


        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);

        startButton.setOnAction(e -> window.setScene(gaming("Level_01")));

        users.setOnAction(e -> window.setScene(profileMenu(window)));

        quit.setOnAction(e -> System.exit(0));

        test.setOnAction(e -> window.setScene(new Scene(pauseMenu(), WINDOW_WIDTH, WINDOW_HEIGHT)));


        //scene.getStylesheets().add("file:layout.css");

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // TODO I'll delete this method, I just keep it for now for testing
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

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    //TODO I'll delete this method soon, users display goes into comboBox
    private Scene displayUsers(){

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users");
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

    private Scene displayLevel(Stage window) {

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
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

        goBack.setOnAction(e -> window.setScene(userSelection(window)));

        exitGame.setOnAction(e -> System.exit(0));

        //vBox.setStyle("-fx-background-color: linear-gradient(to bottom, #006600 0%, #99ffcc 100%");

        //middleMenu.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        //middleMenu.getStyleClass().add("middleMenu");

        //middleMenu.getStylesheets().add("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\src\\Challenge\\layout.css");
        //middleMenu.getStyleClass().add("middleMenu");

        middleMenu.setStyle("-fx-background-color: linear-gradient(to top, #003300 9%, #006600 100%)");

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

                //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users\\"+tf.getText());
                File path = new File("Users/" + tf.getText());

                path.mkdir();

                window.setScene(newGame(window));

            });
        }

    }

}
