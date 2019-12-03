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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.stage.Stage;

import javafx.util.Duration;

import java.nio.file.Paths;

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

    static Level level;
    private Game game = new Game();
    private final Controller controller = new Controller();
    private final Lumberjack jack = new Lumberjack();
    static Stage window;

    // Mediaplayer
    private static MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        window = primaryStage;
        Scene intro = begin();

        window.setTitle("Jungle Hunt");
        window.setScene(intro);
        window.show();

        try {
            //Media media = new Media(Paths.get("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\music\\background_music1.mp3").toUri().toString());
            Media media = new Media(Paths.get("music/background_music1.mp3").toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.2);
            mediaPlayer.play();
        } catch (Exception e) {
            // e.printStackTrace();
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

    private Scene begin() {
        BorderPane root = new BorderPane();

        Button startButton = new Button("START");
        startButton.setPrefSize(150,100);

        Label title = new Label("JUNGLE HUNT");

        root.setCenter(startButton);
        BorderPane.setAlignment(root.getCenter(), Pos.CENTER);

        root.setTop(title);
        BorderPane.setAlignment(root.getTop(), Pos.TOP_CENTER);

        BorderPane.setMargin(root.getCenter(), new Insets(0,0,150,0));

        root.setBottom(bottomBar());

        style(title);

        startButton.setOnAction(e -> window.setScene(userSelection()));

        root.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }


    public Scene userSelection() {

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        EditableButton newUser = new EditableButton("Create user profile");

        ComboBox<String> loadUser = new ComboBox<>();

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users");
        File path = new File("Users/");

        File[] files = path.listFiles();

        for (File file : files) {
            loadUser.getItems().add(file.getName());
        }

        loadUser.setPromptText("Select user profile");
        //Button loadUser = new Button("Load user profiles");

        loadUser.setOnAction(e -> window.setScene(loadGame()));

        Button quit = new Button("Quit");

        menu.getChildren().addAll(newUser, loadUser, quit);
        menu.setAlignment(Pos.CENTER);

        root.setCenter(menu);
        root.setBottom(bottomBar());
        root.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());


        quit.setOnAction(e -> System.exit(0));

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }


    private Scene newGame() {
        BorderPane root = new BorderPane();

        VBox vBox = new VBox();

        Button startButton = new Button("New Game");

        vBox.getChildren().add(startButton);
        vBox.setAlignment(Pos.CENTER);

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        File path = new File("Level_Files/");
        File[] files = path.listFiles();

        assert files != null;

        startButton.setOnAction(e -> {
            String levelName = files[0].getName();
            levelName = levelName.substring(0, levelName.length() - 4);
            window.setScene(gaming(levelName));
        });

        root.setCenter(vBox);
        root.setBottom(bottomBar());

        root.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private Scene loadGame() {
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
        root.setBottom(bottomBar());

        loadButton.setOnAction(e -> window.setScene(displayLevel()));

        root.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

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

    private Scene displayLevel() {

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


        root.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        return new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    }

    public AnchorPane inventory(Level level) {
        HBox test = new HBox();
        AnchorPane inv = new AnchorPane();
        ArrayList<Item> lame = level.getPlayer().getInventory();


        for(Item item: lame) {
            test.getChildren().add(new ImageView(item.getSprite()));
        }


        // FOR THE TESTING
        Image change = new Image("images/ENTITY_FIRE_BOOTS.png",75,50,false,false);
        Image change2 = new Image("images/ENTITY_FLIPPERS.png",75,50,false,false);

        //inventory.setPrefSize(50,50);



        ImageView nr1 = new ImageView(change);
        ImageView nr2 = new ImageView(change2);

        //test.getChildren().addAll(nr1,nr2);

        AnchorPane.setTopAnchor(test, 600.0);
        AnchorPane.setLeftAnchor(test, 200.0);
        AnchorPane.setRightAnchor(test, 200.0);
        AnchorPane.setBottomAnchor(test,20.0);

        //inventory.setPrefSize(100,100);
        test.setStyle("-fx-background-color: #33ccff");

        test.setPrefSize(100,200);
        test.setAlignment(Pos.CENTER);

        inv.getChildren().add(test);

        inv.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        inv.setId("Inventory");

        return inv;
    }


    private AnchorPane pauseMenu(){
        AnchorPane pause = new AnchorPane();
        VBox vBox = new VBox();


        vBox.setPrefSize(200,200);

        Label title = new Label("JUNGLE HUNT");

        style(title);

        Button save = new Button("Save");
        save.setPrefSize(200,50);
        Button goBack = new Button("Return to main menu");
        goBack.setPrefSize(200,50);
        Button exitGame = new Button("Exit");
        exitGame.setPrefSize(200,50);

        vBox.getChildren().addAll(title, save, goBack, exitGame);


        vBox.setAlignment(Pos.CENTER);
        /*middleMenu.setCenter(vBox);*/
        vBox.setSpacing(25);


        goBack.setOnAction(e -> window.setScene(userSelection()));

        exitGame.setOnAction(e -> System.exit(0));

        vBox.setStyle("-fx-background-color: linear-gradient(to top, #003300 9%, #006600 100%)");
        vBox.setMargin(exitGame, new Insets(0,0,20,0));


        AnchorPane.setBottomAnchor(vBox,180.0);
        AnchorPane.setLeftAnchor(vBox,250.0);
        AnchorPane.setTopAnchor(vBox,150.0);
        AnchorPane.setRightAnchor(vBox,250.0);

        pause.getChildren().add(vBox);

        pause.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        pause.setId("pauseMenu");

        return pause;
    }


    private Scene gameSucceed() {
        BorderPane pane = new BorderPane();
        VBox vBox = new VBox();

        Label title = new Label("LEVEL FINISHED SUCCESSFULLY");
        style(title);

        Button selectLevel = new Button("SELECT LEVELS");

        Button returnMenu = new Button("USER SELECTION");

        Button quit = new Button("Quit");

        vBox.getChildren().addAll(title, selectLevel, returnMenu, quit);
        vBox.setAlignment(Pos.CENTER);

        selectLevel.setOnAction(e -> window.setScene(displayLevel()));

        returnMenu.setOnAction(e -> window.setScene(userSelection()));

        quit.setOnAction(e -> System.exit(0));

        pane.setCenter(vBox);
        pane.setBottom(bottomBar());
        pane.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        return new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private Scene gameOver() {
        BorderPane pane = new BorderPane();
        VBox vBox = new VBox();

        Label title = new Label("GAME OVER");
        style(title);

        Button restartLevel = new Button("RESTART LEVEL");

        Button returnMenu = new Button("USER SELECTION");

        Button quit = new Button("Quit");

        restartLevel.setOnAction(e -> window.setScene(gaming(level.getLevelName())));

        returnMenu.setOnAction(e -> window.setScene(userSelection()));

        quit.setOnAction(e -> System.exit(0));

        vBox.getChildren().addAll(title, restartLevel, returnMenu, quit);
        vBox.setAlignment(Pos.CENTER);

        pane.setCenter(vBox);
        pane.setBottom(bottomBar());
        pane.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        Scene scene = new Scene(pane,WINDOW_WIDTH,WINDOW_HEIGHT);

        return scene;
    }

    private void style(Label title) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC,40));
        title.setEffect(dropShadow);
    }

    private Scene gaming(String name) {

        BorderPane root = new BorderPane();
        root.setPrefSize(960,670);

        BorderPane drawing = new BorderPane();
        drawing.setPrefSize(960,670);

        StackPane stack = new StackPane();
        //stack.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        StackPane stack2 = new StackPane();

        System.out.println("SUCCESS!");

        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        drawing.setCenter(canvas);

        drawing.getStyleClass().add(getClass().getResource("layout.css").toExternalForm());

        drawing.setId("game");

        level = controller.makeLevel(name);

        game.drawGame(level, canvas);


        stack.getChildren().add(inventory(level));
        stack.getChildren().add(pauseMenu());
        stack.getChildren().add(drawing);


        root.setBottom(bottomBar());
        root.setCenter(stack);

        Scene play = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        play.addEventHandler(KeyEvent.KEY_PRESSED, event -> controller.processKeyEvent(event, level, game, canvas, gameSucceed(), gameOver()));
        play.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.processMenuEvent(event, stack));

        //play.getStylesheets().add(Main.class.getResource("layout.css").toExternalForm());



        //TESTING
        System.out.println(pauseMenu().getId());
        System.out.println(inventory(level).getId());


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

                window.setScene(newGame());

            });
        }

    }

}
