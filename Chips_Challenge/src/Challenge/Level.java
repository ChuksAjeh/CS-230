package Challenge;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {

    private Cell[][] cellGrid;
    private Entity[][] entityGrid;

    Scanner reader;

    public Level() {

    }

    public void updateLevel() {

    }

    public void buildLevel(String level) throws FileNotFoundException {

        reader = new Scanner(new File("Level_Files/" + level + ".txt"));
        reader.useDelimiter(",");

        int x = reader.nextInt();
        int y = reader.nextInt();

        Cell[][] cellGrid = new Cell[x][y];
        Entity[][] entityGrid = new Entity[x][y];

        reader.nextLine();

        for (int i = 0 ; i < y ; i++) {

            char[] row = reader.nextLine().toCharArray();

//            System.out.println(row);

            for (int j = 0 ; j < x ; j++) {

                if ('#' == row[j]) {
                    // This is a wall
                    cellGrid[i][j] = new Wall();
                } else {
                    // This is a ground
                    cellGrid[i][j] = new Ground();
                }

            }
        }

        setCellGrid(cellGrid);

        int dir;
        String colour;
        int req;
        int xTo;
        int yTo;

        while (reader.hasNextLine()) {

            // This code is incredibly bad, I will refactor it

            String thingName = reader.next();

            if ("PLAYER".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                entityGrid[x][y] = new Player();

            } else if ("SMART_ENEMY".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                dir = reader.nextInt();
                entityGrid[x][y] = new SmartEnemy(dir);

            } else if ("DUMB_ENEMY".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                dir = reader.nextInt();
                entityGrid[x][y] = new DumbEnemy(dir);

            } else if ("WALL_ENEMY".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                dir = reader.nextInt();
                entityGrid[x][y] = new WallEnemy(dir);

            } else if ("LINE_ENEMY".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                dir = reader.nextInt();
                entityGrid[x][y] = new LineEnemy(dir);

            } else if ("GOAL".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                cellGrid[x][y] = new Goal();

            } else if ("KEY_DOOR".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                colour = reader.next();
                cellGrid[x][y] = new KeyDoor(colour);

            } else if ("TOKEN_DOOR".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                req = reader.nextInt();
                cellGrid[x][y] = new TokenDoor(req);

            } else if ("KEY".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                colour = reader.next();
                entityGrid[x][y] = new Key(colour);

            } else if ("TOKEN".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                entityGrid[x][y] = new Token();

            } else if ("FIRE".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                cellGrid[x][y] = new Fire();

            } else if ("WATER".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                cellGrid[x][y] = new Water();

            } else if ("FIRE_BOOTS".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                entityGrid[x][y] = new FireBoots();

            } else if ("FLIPPERS".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                entityGrid[x][y] = new Flippers();

            } else if ("TELEPORTER".equalsIgnoreCase(thingName)) {

                x = reader.nextInt();
                y = reader.nextInt();
                xTo = reader.nextInt();
                yTo = reader.nextInt();

                // This might not work, I have not tested it yet

                cellGrid[x][y] = new Teleporter();
                cellGrid[xTo][yTo] = new Teleporter((Teleporter) cellGrid[x][y]);

            }

            // Todo : the rest of these

        }

        setEntityGrid(entityGrid);
    }

    private void setCellGrid(Cell[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    private void setEntityGrid(Entity[][] entityGrid) {
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