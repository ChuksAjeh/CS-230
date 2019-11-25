package Challenge;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {

    private Cell[][] cellGrid;
    private Entity[][] entityGrid;

    private String levelName;

    Scanner reader;

    public Level(String levelName) throws FileNotFoundException {
        buildLevel(levelName);

        this.levelName = levelName;
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
                    this.entityGrid[x][y] = new SmartEnemy(dir);
                } else if ("DUMBENEMY".equals(name)) {
                    this.entityGrid[x][y] = new DumbEnemy(dir);
                } else if ("WALLENEMY".equals(name)) {
                    this.entityGrid[x][y] = new WallEnemy(dir);
                } else if ("LINEENEMY".equals(name)) {
                    this.entityGrid[x][y] = new LineEnemy(dir);
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

                // This might not work, I have not tested it yet
                Teleporter temp = new Teleporter();

                this.cellGrid[x][y] = temp;

                int pairX = Integer.parseInt(line[3]);
                int pairY = Integer.parseInt(line[4]);

                this.cellGrid[pairX][pairY] = new Teleporter(temp);
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

    public String getLevelName() {
        return this.levelName;
    }

    public int[] getLocation() {
        return null;
    }

}