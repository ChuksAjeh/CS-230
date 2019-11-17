package Challenge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;

    // The dimensions of the canvas
    private static final int CANVAS_WIDTH = 700;
    private static final int CANVAS_HEIGHT = 500;

    // The size of each cell
    private static int GRID_CELL_WIDTH = 50;
    private static int GRID_CELL_HEIGHT = 50;

    private enum Type {
        GAME, HELP
    }

    private Game.Type type = Game.Type.GAME;

    // The canvas in the GUI. This needs to be a global variable
    // (in this setup) as we need to access it in different methods.
    // We could use FXML to place code in the controller instead.
    private Canvas canvas;

    // Loaded images
    Image entityPlayer;

    Image cellGround;
    Image cellWall;
    Image cellGoal;
    Image cellWater;
    Image cellFire;
    Image cellKeyDoor;
    Image cellTokenDoor;

    Image help;

    // For testing ONLY
    Player player = new Player();
    Entity[][] entityGrid;

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

        // Load images
        entityPlayer = new Image("images/ENTITY_PLAYER.png");

        cellGround = new Image("images/CELL_GROUND.png");
        cellWall = new Image("images/CELL_WALL.png");
        cellGoal = new Image("images/CELL_GOAL.png");
        cellFire = new Image("images/CELL_FIRE.png");
        cellWater = new Image("images/CELL_WATER.png");
        cellKeyDoor = new Image("images/CELL_KEYDOOR.png");
        cellTokenDoor = new Image("images/CELL_TOKENDOOR.png");

        help = new Image("images/HELP_PAGE.png");

        Level level = makeLevel("Test_File");

        // Register an event handler for key presses
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event, level, player, entityGrid));

        // Display the scene on the stage
        drawGame(level);
        primaryStage.setTitle("RTX ON!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void processKeyEvent(KeyEvent event, Level level, Player player, Entity[][] entityGrid) {

        if (this.type == Game.Type.GAME) {

            Entity[][] newGrid;

            switch (event.getCode()) {

                case UP:

                    newGrid = player.move(0, entityGrid);
                    level.setEntityGrid(newGrid);

                    break;

                case RIGHT:
                    // Right key was pressed. So move the player right by one cell.

                    newGrid = player.move(1, entityGrid);
                    level.setEntityGrid(newGrid);

                    break;

                case DOWN:

                    newGrid = player.move(2, entityGrid);
                    level.setEntityGrid(newGrid);

                    break;

                case LEFT:

                    newGrid = player.move(3, entityGrid);
                    level.setEntityGrid(newGrid);

                    break;

                case H:
                    drawHelp();
                    break;
                case ESCAPE:
                    System.out.println("Adios Amigo!");
                    System.exit(0);
                default:
                    // Do nothing
                    break;
            }

            if (event.getCode().isArrowKey()) {
                drawGame(level);
            }

        } else if (this.type == Game.Type.HELP) {

            switch (event.getCode()) {

                case ESCAPE:
                    drawGame(level);
                    break;

            }

        }

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    public void drawHelp() {

        this.type = Game.Type.HELP;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.drawImage(help, 0, 0);

    }

    public void drawGame(Level level) {

        this.type = Game.Type.GAME;

        // Draw Level thing
        assert level != null;
        Cell[][] cellGrid = level.getCellGrid();

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

//                System.out.println(cellGrid[y][x].getCellType());

                switch (cellGrid[y][x].getCellType()) {
                    case GROUND:
                        gc.drawImage(cellGround, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                        break;
                    case WALL:
                        gc.drawImage(cellWall, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                        break;
                    case GOAL:
                        gc.drawImage(cellGoal, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                        break;
                    case FIRE:
                        gc.drawImage(cellFire, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                        break;
                    case WATER:
                        gc.drawImage(cellWater, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                        break;
                    case KEY_DOOR:
                        gc.drawImage(cellKeyDoor, x * GRID_CELL_WIDTH, y * GRID_CELL_WIDTH);
                        break;
                    case TOKEN_DOOR:
                        gc.drawImage(cellTokenDoor, x * GRID_CELL_WIDTH, y * GRID_CELL_WIDTH);
                        break;
                    default:
                        break;
                }

            }
        }

        entityGrid = level.getEntityGrid();

//        for (Entity[] row : entityGrid) {
//            for (Entity e : row) {
//
//                if (null == e) {
//                    System.out.println("NULL");
//                } else {
//                    System.out.println(e.toString());
//                }
//            }
//        }

        // TODO : RENDER ALL ENTITIES - GNOME

        int[] playerLoc = player.getLocation(entityGrid);

        System.out.println("Player x : " + playerLoc[0]);
        System.out.println("Player y : " + playerLoc[1]);

        // Draw player at current location
        gc.drawImage(entityPlayer, playerLoc[0] * GRID_CELL_WIDTH, playerLoc[1] * GRID_CELL_HEIGHT);
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


    public static void main(String[] args) {
        launch(args);
    }

    public Game() {

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
