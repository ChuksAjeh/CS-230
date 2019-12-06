package Challenge;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

class GUI {

    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 960;
    private static final int CANVAS_HEIGHT = 684;

    // The two canvases we use in the game
    private static Canvas gameCanvas;
    private static Canvas miniMapCanvas;

    private static Game game;
    private static final MiniMap miniMap = new MiniMap();

    // Not fat, just a controller
    private static final Controller controller = new Controller();

    private static String USER_NAME;

    static Level LEVEL;

    // Not electric unfortunately, just used for timing stuff
    static long START_TIME = 0;
    static long END_TIME;
    static long ELAPSED_TIME;
    static long CONVERTED_TIME;

    public static final Scene scene = new Scene(begin(), WINDOW_WIDTH, WINDOW_HEIGHT);

    private static Label messageOfTheDay() {

        Label message = new Label();
        AtomicReference<String> stuff = new AtomicReference<>(new Ping().getPing());

        message.textProperty().set(stuff.get());
        message.setTextFill(Color.rgb(200, 200, 200));

        Timeline timeline = new Timeline(
                new KeyFrame(new Duration(30000), e -> {
                    stuff.set(new Ping().getPing());
                    message.textProperty().set(stuff.get());
                })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return message;
    }

    private static HBox bottomBar(){

        HBox bottomBar = new HBox();

        bottomBar.setPrefHeight(36);
        bottomBar.setPadding(new Insets(10, 10, 10, 10));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.getChildren().add(messageOfTheDay());
        bottomBar.setStyle("-fx-background-color: #222222");
        bottomBar.setMinHeight(36);

        return bottomBar;
    }

    private static BorderPane begin() {
        BorderPane root = new BorderPane();

        Button startButton = new Button("START");

        Button leaderboard = new Button("LEADERBOARD");

        Button quit = new Button("Quit");

        VBox vBox = new VBox();

        vBox.getChildren().addAll(startButton, leaderboard, quit);

        root.setCenter(vBox);
        vBox.setAlignment(Pos.CENTER);
        //BorderPane.setAlignment(root.getCenter(), Pos.CENTER);

        root.setBottom(bottomBar());


        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        startButton.setOnAction(e -> {
            scene.setRoot(userSelection());
            Main.window.setScene(scene);
        });

        leaderboard.setOnAction(e -> {
            scene.setRoot(levelLeaderboard());
            Main.window.setScene(scene);
        });

        quit.setOnAction(e -> System.exit(0));

        return root;
    }

    private static ArrayList<Button> makeButtons(File[] files, VBox menu) {

        ArrayList<Button> buttons = new ArrayList<>();
        String remove;


        for (int i = 0 ; i < files.length ; i++ ) {
            remove = files[i].getName();
            remove = remove.substring(0, remove.length()-4);
            buttons.add(new Button(remove));
            buttons.get(i).getStyleClass().add("button1");
            menu.getChildren().add(buttons.get(i));

        }

        menu.getStyleClass().add(GUI.class.getResource("layout.css").toExternalForm());
        return buttons;

    }


    private static BorderPane levelLeaderboard() {

        BorderPane root = new BorderPane();
        VBox menu = new VBox();

        //File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files\\");
        File path = new File("Level_Files/");

        File[] files = path.listFiles();

        ArrayList<Button> buttons = makeButtons(Objects.requireNonNull(files), menu);

        root.setBottom(bottomBar());
        menu.setAlignment(Pos.CENTER);
        root.setCenter(menu);
        root.setMargin(menu, new Insets(100,0,0,0));


        for (Button button : buttons) {
            button.setOnAction(e -> {
                String levelName = button.getText();
                scene.setRoot(leaderboard(levelName));
                Main.window.setScene(scene);
            });
        }


        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        return root;
    }

    private static BorderPane leaderboard(String name) {
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        Label title;
        String user;

        Button back = new Button("Back");

        AnchorPane.setRightAnchor(back, 20.0);
        AnchorPane.setBottomAnchor(back,20.0);

        AnchorPane bottomRight = new AnchorPane();
        bottomRight.getChildren().add(back);

        LEVEL = controller.makeLevel(name);

        controller.update(LEVEL);

        boolean ok = true;
        int l = 0;

        for(int i = 0 ; i < controller.getLeaderboardScores().size() && ok; i ++) {
            if(controller.getLeaderboardScores().get(i)!=0){
                user = (i+1)+")"+ controller.getLeaderboardUsers().get(i) +" - "+ controller.getLeaderboardScores().get(i);
                title = new Label(user);
                title.getStyleClass().add("label1");
                vBox.getChildren().add(title);
                l++;
            }
            if(l==3) {
                ok=false;
            }
        }
        if(ok==true) {
            for(int i = l; i<3 ; i ++) {
                user = (i+1)+")"+ controller.getLeaderboardUsers().get(0) +" - 0";
                title = new Label(user);
                title.getStyleClass().add("label1");
                vBox.getChildren().add(title);
            }
        }


        root.setBottom(bottomBar());
        vBox.setAlignment(Pos.CENTER_LEFT);
        root.setCenter(vBox);
        //root.setRight(bottomRight);

        back.setOnAction(e -> {
            scene.setRoot(begin());
            Main.window.setScene(scene);
        });

        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());
        return root;
    }

    private static BorderPane userSelection() {

        BorderPane root = new BorderPane();

        VBox menu = new VBox();

        EditableButton newUser = new EditableButton("Create user profile");

        ComboBox<String> loadUser = new ComboBox<>();

        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Users");
        //File path = new File("Users/");

        File[] files = path.listFiles();

        for (File file : Objects.requireNonNull(files)) {
            loadUser.getItems().add(file.getName());
        }

        loadUser.setPromptText("Select user profile");
        //Button loadUser = new Button("Load user profiles");

        loadUser.setOnAction(e -> {
            USER_NAME = loadUser.getSelectionModel().getSelectedItem();
            scene.setRoot(loadGame());
            Main.window.setScene(scene);
        });

        Button back = new Button("Back");

        back.setOnAction(e -> {
            scene.setRoot(begin());
            Main.window.setScene(scene);
        });


        Button quit = new Button("Quit");

        menu.getChildren().addAll(newUser, loadUser, back, quit);
        menu.setAlignment(Pos.CENTER);

        root.setCenter(menu);
        root.setBottom(bottomBar());
        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());


        quit.setOnAction(e -> System.exit(0));

        //this.userName = newUser.getName();

        return root;
    }


    private static BorderPane newGame() {
        BorderPane root = new BorderPane();

        VBox vBox = new VBox();

        Button startButton = new Button("New Game");

        vBox.getChildren().add(startButton);
        vBox.setAlignment(Pos.CENTER);

        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        //File path = new File("Level_Files/");
        File[] files = path.listFiles();

        assert files != null;

        startButton.setOnAction(e -> {
            String levelName = files[0].getName();
            levelName = levelName.substring(0, levelName.length()-4);
            scene.setRoot(gaming(levelName));
            Main.window.setScene(scene);
        });

        root.setCenter(vBox);
        root.setBottom(bottomBar());

        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        return root;
    }

    private static BorderPane loadGame() {
        BorderPane root = new BorderPane();

        VBox vBox = new VBox();


        Button startButton = new Button("New game");

        Button loadButton = new Button("Load game");

        File path = new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files");
        //File path = new File("Level_files/");
        File[] files = path.listFiles();

        assert files != null;

        startButton.setOnAction(e -> {
            String levelName = files[0].getName();
            levelName = levelName.substring(0,levelName.length() - 4);
            scene.setRoot(gaming(levelName));
            Main.window.setScene(scene);
        });


        vBox.getChildren().addAll(startButton,loadButton);
        vBox.setAlignment(Pos.CENTER);

        root.setCenter(vBox);
        root.setBottom(bottomBar());

        loadButton.setOnAction(e -> {
            scene.setRoot(displayLevel());
            Main.window.setScene(scene);
        });

        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        return root;
    }

    private static BorderPane displayLevel() {

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
                scene.setRoot(gaming(levelName));
                Main.window.setScene(scene);
            });
        }


        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        return root;
    }

    private AnchorPane inventory(Level level) {
        HBox test = new HBox();
        AnchorPane inv = new AnchorPane();

        // FOR THE TESTING
        Image item1 = new Image("images/ENTITY_FIRE_BOOTS.png",75,50,false,false);
        Image item2 = new Image("images/ENTITY_FLIPPERS.png",75,50,false,false);
        Image item3 = new Image("images/ENTITY_KEY.png",75,50,false,false);
        Image item4 = new Image("images/ENTITY_TOKEN.png",75,50,false,false);

        //inventory.setPrefSize(50,50);

        ImageView nr1 = new ImageView(item1);
        ImageView nr2 = new ImageView(item2);
        ImageView nr3 = new ImageView(item3);
        ImageView nr4 = new ImageView(item4);

        test.getChildren().addAll(nr1,nr2, nr3, nr4);

        AnchorPane.setTopAnchor(test, 600.0);
        AnchorPane.setLeftAnchor(test, 200.0);
        AnchorPane.setRightAnchor(test, 200.0);
        AnchorPane.setBottomAnchor(test,20.0);

        //inventory.setPrefSize(100,100);
        test.setStyle("-fx-background-color: #33ccff");

        test.setPrefSize(100,200);
        test.setAlignment(Pos.CENTER);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);

        inv.getChildren().add(test);

        inv.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());

        inv.setId("Inventory");

        return inv;
    }


    private static AnchorPane pauseMenu(){
        AnchorPane pause = new AnchorPane();
        VBox vBox = new VBox();

        vBox.setPrefSize(200,200);

        //Label title = new Label("JUNGLE HUNT");

        //style(title);

        Button goBack = new Button("Return to main menu");

        Button exitGame = new Button("Exit");


        vBox.getChildren().addAll(goBack, exitGame);
        vBox.setMargin(vBox.getChildren().get(0), new Insets(50,0,0,0));

        vBox.setAlignment(Pos.CENTER);
        /*middleMenu.setCenter(vBox);*/
        vBox.setSpacing(25);

        goBack.setOnAction(e -> {
            scene.setRoot(userSelection());
            Main.window.setScene(scene);
        });

        exitGame.setOnAction(e -> System.exit(0));

        VBox.setMargin(exitGame, new Insets(0,0,20,0));

        AnchorPane.setBottomAnchor(vBox,180.0);
        AnchorPane.setLeftAnchor(vBox,250.0);
        AnchorPane.setTopAnchor(vBox,150.0);
        AnchorPane.setRightAnchor(vBox,250.0);

        pause.getChildren().add(vBox);

        pause.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        pause.setId("pauseMenu");

        return pause;
    }


    private static BorderPane gameSucceed() {
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();

        Label title = new Label("LEVEL FINISHED SUCCESSFULLY");
        style(title);

        Button selectLevel = new Button("SELECT LEVELS");

        Button returnMenu = new Button("USER SELECTION");

        Button quit = new Button("Quit");

        vBox.getChildren().addAll(title, selectLevel, returnMenu, quit);
        vBox.setAlignment(Pos.CENTER);

        selectLevel.setOnAction(e -> {
            scene.setRoot(displayLevel());
            Main.window.setScene(scene);
        });

        returnMenu.setOnAction(e -> {
            scene.setRoot(userSelection());
            Main.window.setScene(scene);
        });

        quit.setOnAction(e -> System.exit(0));

        root.setCenter(vBox);
        root.setBottom(bottomBar());
        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        return root;
    }

    private static BorderPane gameOver() {
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();

        Label title = new Label("GAME OVER");
        style(title);

        Button restartLevel = new Button("RESTART LEVEL");

        Button returnMenu = new Button("USER SELECTION");

        Button quit = new Button("Quit");

        restartLevel.setOnAction(e -> {
            scene.setRoot(gaming(LEVEL.getLevelName()));
            Main.window.setScene(scene);
        });

        returnMenu.setOnAction(e -> {
            scene.setRoot(userSelection());
            Main.window.setScene(scene);
        });

        quit.setOnAction(e -> System.exit(0));

        vBox.getChildren().addAll(title, restartLevel, returnMenu, quit);
        vBox.setAlignment(Pos.CENTER);

        root.setCenter(vBox);
        root.setBottom(bottomBar());
        root.getStylesheets().add(GUI.class.getResource("layout.css").toExternalForm());

        return root;
    }


    private static void style(Label title) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.ITALIC,40));
        title.setEffect(dropShadow);
    }


    private static BorderPane gaming(String name) {

        // jack.log(2, "user created " + userName);
        game = new Game(USER_NAME);

        BorderPane root = new BorderPane();
        root.setPrefSize(960,670);

        BorderPane drawing = new BorderPane();
        drawing.setPrefSize(960,670);

        StackPane stack = new StackPane();
        StackPane maps = new StackPane();
        //stack.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        System.out.println("SUCCESS!");

        gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        AnchorPane awesome = new AnchorPane();
        BorderPane mini = new BorderPane();
        mini.setPrefSize(150,150);
        miniMapCanvas = new Canvas(150, 150);

        drawing.setCenter(gameCanvas);

        drawing.getStyleClass().add(GUI.class.getResource("layout.css").toExternalForm());

        drawing.setId("game");

        LEVEL = controller.makeLevel(name);

        game.drawGame(LEVEL, gameCanvas);

        mini.setCenter(miniMapCanvas);

        mini.setStyle("-fx-border-color: #42832d ; -fx-border-width: 2px ");

        miniMap.drawMap(LEVEL, miniMapCanvas);

        AnchorPane.setBottomAnchor(mini, 500.0);
        AnchorPane.setLeftAnchor(mini, 750.0);

        awesome.getChildren().add(mini);

        awesome.getStyleClass().add(GUI.class.getResource("layout.css").toExternalForm());

        awesome.setId("miniMap");

        maps.getChildren().add(drawing);
        maps.getChildren().add(awesome);


        stack.getChildren().add(pauseMenu());
        stack.getChildren().add(maps);

        root.setBottom(bottomBar());
        root.setCenter(stack);


        START_TIME = System.nanoTime();

        root.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                controller.processKeyEvent(event, LEVEL, game, gameCanvas, new BorderPane[] {gameSucceed(), gameOver()}));
        root.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                controller.processMiniMap(event, LEVEL, miniMap, miniMapCanvas));
        root.addEventFilter(KeyEvent.KEY_PRESSED, event ->
                controller.processMenuEvent(event, stack));


        System.out.println(drawing.getId());

        return root;

    }

    static class EditableButton extends Button {

        String user;

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

                //path.mkdir();


                USER_NAME = tf.getText();


                scene.setRoot(newGame());
                Main.window.setScene(scene);

            });
        }


        public String getName() {
            return this.user;
        }

    }

}
