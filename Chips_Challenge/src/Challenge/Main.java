package Challenge;

import javafx.application.Application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.Stage;


import java.nio.file.Paths;

/**
 * This done mainy stuff
 */
public class Main extends Application {


    private static MediaPlayer mediaPlayer;
    static Stage window;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        window = primaryStage;

        window.setTitle("Jungle Hunt");
        window.setScene(GUI.scene);
        window.show();

        try {
            //Media media = new Media(Paths.get("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\music\\music.mp3").toUri().toString());
            Media media = new Media(Paths.get("music/music.mp3").toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.2);
            mediaPlayer.play();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
