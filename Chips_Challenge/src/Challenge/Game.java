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

    // Loaded images
    private Image cellGround;
    private Image cellWall;
    private Image cellGoal;
    private Image cellKeyDoor;
    private Image cellTokenDoor;
    private Image cellFire;
    private Image cellWater;

    private Image entityPlayer;

    private Image entityEnemy; // TESTING
    private Image entitySmartEnemy;
    private Image entityDumbEnemy;
    private Image entityWallEnemy;
    private Image entityLineEnemy;
    private Image entityKey;
    private Image entityToken;
    private Image entityFireBoots;
    private Image entityFlippers;

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

        // Load images
        cellGround = new Image("images/CELL_GROUND.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        cellWall = new Image("images/CELL_WALL.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        cellGoal = new Image("images/CELL_GOAL.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        cellKeyDoor = new Image("images/CELL_KEY_DOOR.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        cellTokenDoor = new Image("images/CELL_TOKEN_DOOR.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        cellFire = new Image("images/CELL_FIRE.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        cellWater = new Image("images/CELL_WATER.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);

        entityPlayer = new Image("images/ENTITY_PLAYER.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);

        entityEnemy = new Image("images/ENTITY_ENEMY.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false); // TESTING
//        entitySmartEnemy = new Image("images/ENTITY_PLAYER.png"), GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false;
//        entityDumbEnemy = new Image("images/ENTITY_PLAYER.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
//        entityWallEnemy = new Image("images/ENTITY_PLAYER.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
//        entityLineEnemy = new Image("images/ENTITY_PLAYER.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        entityKey = new Image("images/ENTITY_KEY.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        entityToken = new Image("images/ENTITY_TOKEN.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        entityFireBoots = new Image("images/ENTITY_FIRE_BOOTS.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);
        entityFlippers = new Image("images/ENTITY_FLIPPERS.png", GRID_CELL_WIDTH, GRID_CELL_HEIGHT, true, false);

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

                renderCell(gc, x, y);

            }
        }

    }

    private void renderCell(GraphicsContext gc, int x, int y) {

        Cell.CellType type = cellGrid[x][y].getCellType();

        if (Cell.CellType.GROUND == type) {
            gc.drawImage(cellGround, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.WALL == type) {
            gc.drawImage(cellWall, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.GOAL == type) {
            gc.drawImage(cellGoal, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.KEY_DOOR == type) {
            gc.drawImage(cellKeyDoor, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.TOKEN_DOOR == type) {
            gc.drawImage(cellTokenDoor, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.FIRE == type) {
            gc.drawImage(cellFire, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.WATER == type) {
            gc.drawImage(cellWater, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Cell.CellType.TELEPORTER == type) {
            // Oops
        } else {
            jack.log(1, "Incorrect Cell");
        }

    }

    private void renderEntityGrid(GraphicsContext gc, Entity[][] entityGrid) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0; y < entityGrid[x].length; y++) {

                if (null != entityGrid[x][y]) {
                    renderEntity(gc, x, y);
                }

            }
        }
    }

    private void renderEntity(GraphicsContext gc, int x, int y) {

        Entity.EntityType type = entityGrid[x][y].getEntityType();

        if (Entity.EntityType.PLAYER == type) {

            int direction = player.getDirection();
            entityPlayer = rotate(entityPlayer, direction);
            gc.drawImage(entityPlayer, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);

            // Reset the image
            entityPlayer = rotate(entityPlayer, 4 - direction);

        } else if (Entity.EntityType.SMART_ENEMY == type) {
            gc.drawImage(entityEnemy, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.DUMB_ENEMY == type) {
            gc.drawImage(entityEnemy, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.WALL_ENEMY == type) {
            gc.drawImage(entityEnemy, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.LINE_ENEMY == type) {
            gc.drawImage(entityEnemy, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.KEY == type) {
            gc.drawImage(entityKey, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.TOKEN == type) {
            gc.drawImage(entityToken, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.FIRE_BOOTS == type) {
            gc.drawImage(entityFireBoots, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else if (Entity.EntityType.FLIPPERS == type) {
            gc.drawImage(entityFlippers, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
        } else {
            jack.log(1, "Incorrect Cell");
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
