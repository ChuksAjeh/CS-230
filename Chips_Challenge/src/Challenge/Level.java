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

    public void buildLevel(String level) throws FileNotFoundException {
        // This WILL be private in the final version

        reader = new Scanner(new File("Level_Files/" + level + ".txt"));
        reader.useDelimiter(",");

        int x = reader.nextInt();
        int y = reader.nextInt();

        // Throw away the rest of the line
        reader.nextLine();

        // The thing we return
        Cell[][] cellGrid;

        // Make an empty grid
        cellGrid = new Cell[x][y];

        for (int i = 0 ; i < y ; i++) {

            char[] row = reader.nextLine().toCharArray();

            System.out.println(row);

            for (int j = 0 ; j < x ; j++) {

                // cellGrid[j][i] = new Ground();

                if ('#' == row[j]) {
                    // This is a wall
                    cellGrid[j][i] = new Wall();
                } else {
                    // This is a ground
                    cellGrid[j][i] = new Ground();
                }

            }
        }

        Entity[][] entityGrid;

        int dir; // Direction used for Enemies only
        int req; // Requirement value used for TokenDoors

        // Red, Green and Blue components for Keys and KeyDoors
        int red;
        int green;
        int blue;

        entityGrid = new Entity[x][y];

        while (reader.hasNextLine()) {

            String[] line = reader.nextLine().toUpperCase().split(",");

            String name = line[0];

            x = Integer.parseInt(line[1]);
            y = Integer.parseInt(line[2]);

            if (name.contains("ENEMY")) {

                dir = Integer.parseInt(line[3]);

                switch (name) {
                    case "SMART_ENEMY":
                        entityGrid[x][y] = new SmartEnemy(dir);
                        break;
                    case "DUMB_ENEMY":
                        entityGrid[x][y] = new DumbEnemy(dir);
                        break;
                    case "WALL_ENEMY":
                        entityGrid[x][y] = new WallEnemy(dir);
                        break;
                    case "LINE_ENEMY":
                        entityGrid[x][y] = new LineEnemy(dir);
                        break;
                }
            }

            if (name.contains("KEY")) {

                red = Integer.parseInt(line[3]);
                green = Integer.parseInt(line[4]);
                blue = Integer.parseInt(line[5]);

                if ("KEY_DOOR".equals(name)) {
                    cellGrid[x][y] = new KeyDoor(Color.rgb(red, green, blue));
                } else if ("KEY".equals(name)) {
                    entityGrid[x][y] = new Key(Color.rgb(red, green, blue));
                }

            }

            if ("TOKEN".equals(name)) {
                entityGrid[x][y] = new Token();
            } else if ("TOKEN_DOOR".equals(name)) {
                req = Integer.parseInt(line[3]);
                cellGrid[x][y] = new TokenDoor(req);
            }


            if ("PLAYER".equals(name)) {
                dir = Integer.parseInt(line[3]);
                entityGrid[x][y] = new Player(dir);
            } else if ("GOAL".equals(name)) {
                cellGrid[x][y] = new Goal();
            } else if ("FIRE".equals(name)) {
                cellGrid[x][y] = new Fire();
            } else if ("WATER".equals(name)) {
                cellGrid[x][y] = new Water();
            } else if ("FIRE_BOOTS".equals(name)) {
                entityGrid[x][y] = new FireBoots();
            } else if ("FLIPPERS".equals(name)) {
                entityGrid[x][y] = new Flippers();
            } else if ("TELEPORTER".equals(name)) {

                // This might not work, I have not tested it yet
                Teleporter temp = new Teleporter();
                cellGrid[x][y] = temp;

                x = Integer.parseInt(line[3]);
                y = Integer.parseInt(line[4]);

                cellGrid[x][y] = new Teleporter(temp);
            }

        }

        this.setCellGrid(cellGrid);
        this.setEntityGrid(entityGrid);
    }

    public void updateCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    public void updateEntityGrid(Entity[][] entityGrid) {
        this.entityGrid = entityGrid;
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