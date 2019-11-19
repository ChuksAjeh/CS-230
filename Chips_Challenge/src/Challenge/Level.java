package Challenge;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {

    private Cell[][] cellGrid;
    private Entity[][] entityGrid;

    Scanner reader;

    public Level(String levelName) throws FileNotFoundException {

        buildLevel(levelName);

    }

    public void updateLevel() {

    }

    private void buildLevel(String level) throws FileNotFoundException {

        reader = new Scanner(new File("Level_Files/" + level + ".txt"));
        reader.useDelimiter(",");

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

    private void buildCompleteGrids(Scanner reader) {

        int x; // X component of a thing
        int y; // Y component of a thing

        int dir; // Direction used for Enemy and Player
        int req; // Requirement value used for TokenDoor

        Color colour; // Colour used for Key and KeyDoor

        while (reader.hasNextLine()) {

            String[] line = reader.nextLine().toUpperCase().split(",");
            String name = line[0];

            x = Integer.parseInt(line[1]);
            y = Integer.parseInt(line[2]);

            if (name.contains("ENEMY")) {

                dir = Integer.parseInt(line[3]);

                if ("SMART_ENEMY".equals(name)) {
                    this.entityGrid[x][y] = new SmartEnemy(dir);
                } else if ("DUMB_ENEMY".equals(name)) {
                    this.entityGrid[x][y] = new DumbEnemy(dir);
                } else if ("WALL_ENEMY".equals(name)) {
                    this.entityGrid[x][y] = new WallEnemy(dir);
                } else if ("LINE_ENEMY".equals(name)) {
                    this.entityGrid[x][y] = new LineEnemy(dir);
                }

            } else if (name.contains("KEY")) {

                colour = Color.rgb(Integer.parseInt(line[3]), Integer.parseInt(line[4]), Integer.parseInt(line[5]));

                if ("KEY_DOOR".equals(name)) {
                    this.cellGrid[x][y] = new KeyDoor(colour);
                } else if ("KEY".equals(name)) {
                    this.entityGrid[x][y] = new Key(colour);
                }

            } else if ("TOKEN".equals(name)) {
                this.entityGrid[x][y] = new Token();
            } else if ("TOKEN_DOOR".equals(name)) {
                req = Integer.parseInt(line[3]);
                this.cellGrid[x][y] = new TokenDoor(req);
            } else if ("PLAYER".equals(name)) {
                dir = Integer.parseInt(line[3]);
                this.entityGrid[x][y] = new Player(dir);
            } else if ("GOAL".equals(name)) {
                this.cellGrid[x][y] = new Goal();
            } else if ("FIRE".equals(name)) {
                this.cellGrid[x][y] = new Fire();
            } else if ("WATER".equals(name)) {
                this. cellGrid[x][y] = new Water();
            } else if ("FIRE_BOOTS".equals(name)) {
                this.entityGrid[x][y] = new FireBoots();
            } else if ("FLIPPERS".equals(name)) {
                this.entityGrid[x][y] = new Flippers();
            } else if ("TELEPORTER".equals(name)) {

                // This might not work, I have not tested it yet
                Teleporter temp = new Teleporter();
                this.cellGrid[x][y] = temp;

                x = Integer.parseInt(line[3]);
                y = Integer.parseInt(line[4]);

                this.cellGrid[x][y] = new Teleporter(temp);
            }

        }

    }

    private void buildEnemy() {

    }

    public void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    public void setEntityGrid(Entity[][] entityGrid) {
        this.entityGrid = entityGrid;
    }

    public Cell[][] getCellGrid() {
        return this.cellGrid;
    }

    public Entity[][] getEntityGrid() {
        return this.entityGrid;
    }

    public int[] getLocation() {
        return null;
    }

}