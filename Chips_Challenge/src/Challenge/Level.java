package Challenge;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author George Carpenter
 * @version 2.0
 */
class Level {

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
    Level(String levelName) {

        this.levelName = levelName;

        try {
            buildLevel(levelName);
        } catch (FileNotFoundException e) {
            // Nothing
        }

    }

    private void buildLevel(String level) throws FileNotFoundException {

        Scanner reader = new Scanner(new File("Level_Files/" + level + ".txt"));
        reader.useDelimiter(",");

        buildBasicGrids(reader);
        buildGrids(readRemainingLines(reader));

    }

    private void buildBasicGrids(Scanner reader) {

        int x = reader.nextInt();
        int y = reader.nextInt();

        // Throw away the rest of the line
        reader.nextLine();

        this.cellGrid = new Cell[x][y];
        this.entityGrid = new Entity[x][y];

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

    private String readRemainingLines(Scanner reader) {

        StringBuilder stringBuilder = new StringBuilder();

        while (reader.hasNextLine()) {
            stringBuilder.append(reader.nextLine());
        }

        reader.close();

        return stringBuilder.toString();

    }

    private void buildGrids(String file) {

        StringTokenizer t = new StringTokenizer(file, ",");

        String label;
        Position position;
        int direq;

        while (t.hasMoreTokens()) {

            label = t.nextToken();

            System.out.println(label);

            position = new Position(Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken()));

            if ("PLAYER".equals(label) || label.contains("ENEMY")) {

                direq = Integer.parseInt(t.nextToken());

                if ("PLAYER".equals(label)) {
                    this.entityGrid[position.x][position.y] = new Player(position, direq);
                } else if ("SMARTENEMY".equals(label)) {
                    this.entityGrid[position.x][position.y] = new SmartEnemy(position, direq);
                } else if ("DUMBENEMY".equals(label)) {
                    this.entityGrid[position.x][position.y] = new DumbEnemy(position, direq);
                } else if ("WALLENEMY".equals(label)) {
                    this.entityGrid[position.x][position.y] = new WallEnemy(position, direq);
                } else if ("LINEENEMY".equals(label)) {
                    this.entityGrid[position.x][position.y] = new LineEnemy(position, direq);
                }

            } else if ("KEYDOOR".equals(label)) {

                int r = Integer.parseInt(t.nextToken());
                int g = Integer.parseInt(t.nextToken());
                int b = Integer.parseInt(t.nextToken());

                Color colour = Color.rgb(r, g, b);

                this.cellGrid[position.x][position.y] = new KeyDoor(colour);

                position = new Position(Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken()));

                this.entityGrid[position.x][position.y] = new Key(colour);

            } else if ("TOKEN".equals(label)) {
                this.entityGrid[position.x][position.y] = new Token();
            } else if ("TOKENDOOR".equals(label)) {
                direq = Integer.parseInt(t.nextToken());
                this.cellGrid[position.x][position.y] = new TokenDoor(direq);
            } else if ("GOAL".equals(label)) {
                this.cellGrid[position.x][position.y] = new Goal();
            } else if ("FIRE".equals(label)) {
                this.cellGrid[position.x][position.y] = new Fire();
            } else if ("WATER".equals(label)) {
                this.cellGrid[position.x][position.y] = new Water();
            } else if ("FIREBOOTS".equals(label)) {
                this.entityGrid[position.x][position.y] = new FireBoots();
            } else if ("FLIPPERS".equals(label)) {
                this.entityGrid[position.x][position.y] = new Flippers();
            } else if ("TELEPORTER".equals(label)) {

                Teleporter temp = new Teleporter();

                this.cellGrid[position.x][position.y] = temp;

                position = new Position(Integer.parseInt(t.nextToken()), Integer.parseInt(t.nextToken()));

                this.cellGrid[position.x][position.y] = new Teleporter(temp);
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
    String getLevelName() {
        return this.levelName;
    }

    /**
     * Used to find an Item or Cell in the Level grids
     * @param grid which grid to search
     * @param thing what you want to find
     * @param <T> Cell or Entity
     * @return The location if not null
     */
    <T> int[] getLocation(T[][] grid, T thing) {

        for (int x = 0 ; x < entityGrid.length ; x++ ) {
            for (int y = 0 ; y < entityGrid[x].length ; y++ ) {

                if (thing == grid[x][y]) {
                    return new int[] {x, y};
                }

            }
        }

        return null;
    }

    /**
     * Gets the Player object from the level
     * @return the Player object
     */
    Player getPlayer() {

        for (Entity[] row : this.entityGrid) {
            for (Entity entity : row) {
                if (entity instanceof Player) {
                    return (Player) entity;
                }
            }
        }

        return null;

    }

    ArrayList<Enemy> getEnemies(Entity[][] entityGrid) {

        ArrayList<Enemy> enemies = new ArrayList<>();

        for (Entity[] row : entityGrid) {
            for (Entity entity : row) {

                if (entity instanceof Enemy) {
                    enemies.add((Enemy) entity);
                }

            }
        }

        return enemies;
    }

}
