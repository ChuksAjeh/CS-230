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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Pane root = mainMenu();

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("Thing?");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private BorderPane mainMenu() {

        BorderPane root = new BorderPane();

        HBox bottomBar = new HBox();
        Label message = new Label();

        Pane centerThing = new Pane(); // No idea

        Button startButton = new Button("Start!");

        AtomicReference<String> stuff = new AtomicReference<>(new Ping().getPing());

        message.textProperty().set(stuff.get());
        message.setTextFill(Color.rgb(200, 200, 200));

        bottomBar.setPadding(new Insets(10, 10, 10, 10));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.getChildren().add(message);

        bottomBar.setStyle("-fx-background-color: #222222");
        bottomBar.setMinHeight(36);

        centerThing.getChildren().add(startButton);

        startButton.setOnAction(e -> {

            canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
            root.setCenter(canvas);

            drawThing();
        });

        Timeline timeline = new Timeline(
            new KeyFrame(new Duration(5000), e -> {
                stuff.set(new Ping().getPing());
                message.textProperty().set(stuff.get());
            })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        root.setBottom(bottomBar);
        root.setCenter(centerThing);

        return root;

    }

    public void drawThing() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.drawImage(new Image("images/GUI_PLS.png"), 0, 0);

    }

}
