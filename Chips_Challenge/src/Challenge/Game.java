package Challenge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends Application {

    private ArrayList<User> users;

    private Scanner reader;
    private String userFile = "users.txt";

    // The dimensions of the window
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1080;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 1920;
    private static final int CANVAS_HEIGHT = 1080;

    // The size of each cell
    private static int GRID_CELL_WIDTH = 120;
    private static int GRID_CELL_HEIGHT = 120;

    private enum Type {
        GAME, HELP
    }

    private Game.Type type = Game.Type.GAME;

    // The canvas in the GUI. This needs to be a global variable
    // (in this setup) as we need to access it in different methods.
    // We could use FXML to place code in the controller instead.
    private Canvas canvas;

    private Image help;

    private Cell[][] cellGrid;
    private Entity[][] entityGrid;


    // For testing ONLY
    Player player = new Player(0);
    Lumberjack jack = new Lumberjack();

    public Level makeLevel(String levelName) {

        // Build a Level thing
        Level level = null;

        try {
            level = new Level(levelName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return level;

    }

    public void start(Stage primaryStage) {
        // Build the GUI
        Pane root = buildGUI();

        // Create a scene from the GUI
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        help = new Image("images/HELP_PAGE.png", CANVAS_WIDTH, CANVAS_HEIGHT, true, false);

        Level level = makeLevel("Test_File");

        // Register an event handler for key presses
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event, level, player, entityGrid));

        // Display the scene on the stage
        drawGame(level);
        primaryStage.setTitle("RTX ON!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void processKeyEvent(KeyEvent event, Level level, Player player, Entity[][] entityGrid) {

        if (this.type == Game.Type.GAME) {

            Entity[][] newGrid;

            if (KeyCode.UP == event.getCode()) {
                newGrid = player.move(0, entityGrid);
                level.setEntityGrid(newGrid);
            } else if (KeyCode.RIGHT == event.getCode()) {
                newGrid = player.move(1, entityGrid);
                level.setEntityGrid(newGrid);
            } else if (KeyCode.DOWN == event.getCode()) {
                newGrid = player.move(2, entityGrid);
                level.setEntityGrid(newGrid);
            } else if (KeyCode.LEFT == event.getCode()) {
                newGrid = player.move(3, entityGrid);
                level.setEntityGrid(newGrid);
            } else if (KeyCode.H == event.getCode()) {
                drawHelp();
            } else if (KeyCode.ESCAPE == event.getCode()) {
                System.out.println("Adios Amigo!");
                System.exit(0);
            }

            if (event.getCode().isArrowKey()) {
                drawGame(level);
            }

        } else if (this.type == Game.Type.HELP) {

            if (KeyCode.ESCAPE == event.getCode()) {
                drawGame(level);
            }

        }

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    private void drawHelp() {

        this.type = Game.Type.HELP;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.drawImage(help, 0, 0);

    }

    private void drawGame(Level level) {

        this.type = Game.Type.GAME;

        // Because it's logical
        assert null != level;

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Get, Render and Log Cells
        cellGrid = level.getCellGrid();
        this.renderCellGrid(gc, cellGrid);
//        jack.logCellGrid(cellGrid);

        // Get, Render and Log Entitys
        entityGrid = level.getEntityGrid();
        this.renderEntityGrid(gc, entityGrid);
//        jack.logEntityGrid(entityGrid);

        // Log Player
        jack.logPlayerLoc(player, entityGrid);

    }

    private void renderCellGrid(GraphicsContext gc, Cell[][] cellGrid) {

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                Cell cell = cellGrid[x][y];
                Image sprite = resize(cell.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

                gc.drawImage(sprite, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);

            }
        }

    }

    private void renderEntityGrid(GraphicsContext gc, Entity[][] entityGrid) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0; y < entityGrid[x].length; y++) {

                if (null != entityGrid[x][y]) {

                    Entity entity = entityGrid[x][y];
                    Image sprite = resize(entity.getSprite(), GRID_CELL_HEIGHT, GRID_CELL_WIDTH);

                    // TODO : Enemy rotation - Gnome

                    if (Entity.EntityType.PLAYER == entity.getEntityType()) {
                        gc.drawImage(rotate(sprite, player.getDirection()), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                    } else {
                        gc.drawImage(sprite, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                    }

                }

            }
        }
    }

//    public void restartGame() {
        // We just move the player to cell (0, 0)
//        playerX = 0;
//        playerY = 0;
//        drawGame();
//    }

    private Pane buildGUI() {
        // Create top-level panel that will hold all GUI
        BorderPane root = new BorderPane();

        // Create canvas
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);

//        Create a toolbar with some nice padding and spacing
//        HBox toolbar = new HBox();
//        toolbar.setSpacing(10);
//        toolbar.setPadding(new Insets(10, 10, 10, 10));
//        root.setTop(toolbar);
//
//        Create toolbar content
//        Button restartButton = new Button("Restart");
//        toolbar.getChildren().add(restartButton);
//
//        Add button event handlers
//        restartButton.setOnAction(e -> {
//            restartGame();
//        });

        return root;
    }

    private Image resize(Image image, int height, int width) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Resize
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        // Capture it? I think
        SnapshotParameters param = new SnapshotParameters();

        return imageView.snapshot(param, null);

    }

    private Image rotate(Image image, int direction) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Rotate
        imageView.setRotate(90 * direction);

        // Capture it? I think
        SnapshotParameters param = new SnapshotParameters();

        return imageView.snapshot(param, null);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ArrayList<User> loadUsers() {
        ArrayList<User> temp = new ArrayList<>();

        try {

            reader = new Scanner(new File(userFile));

            while (reader.hasNext()) {

                String line = reader.nextLine();

                User tempUser = new User();
                tempUser.setUserName(line.split(",")[0]);

                // TODO : Read scores

                users.add(tempUser);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return users;

    }
}
