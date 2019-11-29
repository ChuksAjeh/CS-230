package Challenge;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author George Carpenter
 * @version 2.0
 */
public class Level {

    /**
     * The cell grid to build
     */
    private Cell[][] cellGrid;

    /**
     * The entity grid to build
     */
    private Entity[][] entityGrid;

    /**
     * The name of the level to build
     */
    private final String levelName;

    /**
     * Constructs a Level from a given file name
     * @param levelName the name of the level to build
     */
    public Level(String levelName) {

        this.levelName = levelName;

        try {
            buildLevel(levelName);
        } catch (FileNotFoundException e) {
            // Nothing
        }

    }

    /**
     * Begins the build process
     * @param level the level to build
     * @throws FileNotFoundException if the file does not exist
     */
    private void buildLevel(String level) throws FileNotFoundException {

        //Scanner reader = new Scanner(new File("D:\\IdeaProjects\\CS-230\\Chips_Challenge\\Level_Files\\" + level + ".txt"));
        Scanner reader = new Scanner(new File("Level_Files/" + level + ".txt"));

        // Set a delimiter
        reader.useDelimiter(",");

        // Read in the size of the level to create
        int x = reader.nextInt();
        int y = reader.nextInt();

        // Throw away the rest of the line
        reader.nextLine();

        // Build basic grids
        setCellGrid(new Cell[x][y]);
        setEntityGrid(new Entity[x][y]);

        // Build basic cell grid
        buildBasicCellGrid(reader, x, y);

        // Populate grids
        buildCompleteGrids(reader);

    }

    /**
     * Builds a basic Wall / Ground grid
     * @param reader the scanner
     * @param x the width of the grid
     * @param y the height of the grid
     */
    private void buildBasicCellGrid(Scanner reader, int x, int y) {

        for (int i = 0 ; i < y ; i++) {

            char[] row = reader.nextLine().toCharArray();

            for (int j = 0 ; j < x ; j++) {

                if ('#' == row[j]) {
                    // This is a wall
                    this.cellGrid[j][i] = new Wall();
                } else {
                    // This is a ground
                    this.cellGrid[j][i] = new Ground();
                }

            }
        }

    }

    /**
     * Builds the remainder of the level grids
     * @param reader the scanner
     */
    private void buildCompleteGrids(Scanner reader) {

        String[] line; // Line being parsed
        String name; // Name of a thing
        int x; // X component of a thing
        int y; // Y component of a thing
        int dir; // Direction used for Enemy and Player
        int req; // Requirement value used for TokenDoor
        Color colour; // Colour used for Key and KeyDoor

        while (reader.hasNextLine()) {

            line = reader.nextLine().toUpperCase().split(",");
            name = line[0];
            x = Integer.parseInt(line[1]);
            y = Integer.parseInt(line[2]);

            if (name.contains("ENEMY") || "PLAYER".equals(name)) {
                dir = Integer.parseInt(line[3]);

                if ("PLAYER".equals(name)) {
                    this.entityGrid[x][y] = new Player(dir);
                } else if ("SMARTENEMY".equals(name)) {
                    this.entityGrid[x][y] = new SmartEnemy(dir,x,y);
                } else if ("DUMBENEMY".equals(name)) {
                    this.entityGrid[x][y] = new DumbEnemy(dir,x,y);
                } else if ("WALLENEMY".equals(name)) {
                    this.entityGrid[x][y] = new WallEnemy(dir,x,y);
                } else if ("LINEENEMY".equals(name)) {
                    this.entityGrid[x][y] = new LineEnemy(dir,x,y);
                }

            } else if (name.equals("KEYDOOR")) {

                colour = Color.rgb(Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]));

                int keyX = Integer.parseInt(line[6]);
                int keyY = Integer.parseInt(line[7]);

                this.cellGrid[x][y] = new KeyDoor(colour);
                this.entityGrid[keyX][keyY] = new Key(colour);

            } else if ("TOKEN".equals(name)) {
                this.entityGrid[x][y] = new Token();
            } else if ("TOKENDOOR".equals(name)) {
                req = Integer.parseInt(line[3]);
                this.cellGrid[x][y] = new TokenDoor(req);
            } else if ("GOAL".equals(name)) {
                this.cellGrid[x][y] = new Goal();
            } else if ("FIRE".equals(name)) {
                this.cellGrid[x][y] = new Fire();
            } else if ("WATER".equals(name)) {
                this. cellGrid[x][y] = new Water();
            } else if ("FIREBOOTS".equals(name)) {
                this.entityGrid[x][y] = new FireBoots();
            } else if ("FLIPPERS".equals(name)) {
                this.entityGrid[x][y] = new Flippers();
            } else if ("TELEPORTER".equals(name)) {

                Teleporter temp = new Teleporter();

                this.cellGrid[x][y] = temp;

                int pairX = Integer.parseInt(line[3]);
                int pairY = Integer.parseInt(line[4]);

                this.cellGrid[pairX][pairY] = new Teleporter(temp);
            }

        }

    }

    /**
     * Sets the cell grid
     * @param cellGrid the cell grid for the level Object
     */
    public void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    /**
     * Sets the entity grid
     * @param entityGrid the entity grid for the level Object
     */
    public void setEntityGrid(Entity[][] entityGrid) {
        this.entityGrid = entityGrid;
    }

    /**
     * Gets the cell grid
     * @return the cell grid off the Level object
     */
    public Cell[][] getCellGrid() {
        return this.cellGrid;
    }

    /**
     * Gets the entity grid
     * @return the entity grid for the level Object
     */
    public Entity[][] getEntityGrid() {
        return this.entityGrid;
    }

    /**
     * Gets the level name
     * @return the name of the Level, for saving
     */
    public String getLevelName() {
        return this.levelName;
    }

    /**
     * Used to find an Item or Cell in the Level grids
     * @param grid which grid to search
     * @param thing what you want to find
     * @param <T> Cell or Entity
     * @return The location if not null
     */
    public <T> int[] getLocation(T[][] grid, T thing) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                if (thing == grid[x][y]) {
                    return new int[] {x, y};
                }

            }
        }

        return null;
    }

}
