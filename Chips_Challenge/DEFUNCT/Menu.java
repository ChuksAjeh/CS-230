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

import java.io.FileNotFoundException;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting point to show
 * how to draw an image on a canvas and respond to arrow key presses.
 *
 * Do not build the whole application in this file. This file should probably remain very small.
 *
 * @author Liam O'Reilly
 */
public class Menu extends Application {
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

    private Type type = Type.GAME;

    // The canvas in the GUI. This needs to be a global variable
    // (in this setup) as we need to access it in different methods.
    // We could use FXML to place code in the controller instead.
    private Canvas canvas;

    // Loaded images
    Image playerSprite;
    Image ground;
    Image wall;
    Image help;

    // X and Y coordinate of player
    int playerX = 0;
    int playerY = 0;

    // For testing ONLY
    Player player = new Player();
    Entity[][] entityGrid;

    public void start(Stage primaryStage) {
        // Build the GUI
        Pane root = buildGUI();

        // Create a scene from the GUI
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Load images
        playerSprite = new Image("images/ENTITY_PLAYER.png");
        ground = new Image("images/CELL_GROUND.png");
        wall = new Image("images/CELL_WALL.png");
        help = new Image("images/HELP_PAGE.png");

        // Register an event handler for key presses
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event, player, entityGrid));

        // Display the scene on the stage
        drawGame();
        primaryStage.setTitle("RTX ON!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Process a key event due to a key being pressed, e.g., to the player.
     * @param event The key event that was pressed.
     */
    public void processKeyEvent(KeyEvent event, Player player, Entity[][] entityGrid) {

        if (this.type == Type.GAME) {

            switch (event.getCode()) {

                case RIGHT:
                    // Right key was pressed. So move the player right by one cell.

                    player.move(1, entityGrid);

                    playerX += 1;
                    break;
                case LEFT:
                    playerX -= 1;
                    break;
                case UP:
                    playerY -= 1;
                    break;
                case DOWN:
                    playerY += 1;
                    break;
                case R:
                    restartGame();
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
                drawGame();
            }

        } else if (this.type == Type.HELP) {

            switch (event.getCode()) {

                case ESCAPE:
                    drawGame();
                    break;

            }

        }

        // Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
        event.consume();
    }

    /**
     * Draw some kind of help on screen .. Arrows + R + H etc
     */
    public void drawHelp() {

        this.type = Type.HELP;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.drawImage(help, 0, 0);

    }

    /**
     * Draw the game on the canvas.
     */
    public void drawGame() {

        this.type = Type.GAME;

        // Build a Level thing
        Level level = null;

        try {
            level = new Level("Test_File");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw Level thing
        Cell[][] cellGrid = level.getCellGrid();

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                switch (cellGrid[y][x].getType()) {
                    case GROUND:
                        gc.drawImage(ground, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
                        break;
                    case WALL:
                        gc.drawImage(wall, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
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

        int[] playerLoc = player.getLocation(entityGrid);

        System.out.println("Player x : " + playerLoc[0]);
        System.out.println("Player y : " + playerLoc[1]);

        // Draw player at current location
        gc.drawImage(playerSprite, playerLoc[0] * GRID_CELL_WIDTH, playerLoc[1] * GRID_CELL_HEIGHT);
    }

    /**
     * Restart the game.
     */
    public void restartGame() {
        // We just move the player to cell (0, 0)
        playerX = 0;
        playerY = 0;
        drawGame();
    }

    /**
     * Create the GUI.
     * @return The panel that contains the created GUI.
     */
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
}