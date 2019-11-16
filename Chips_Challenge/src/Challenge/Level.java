package Challenge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {

    private Object[][] cellGrid;
    private Object[][] entityGrid;

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

        Object[][] cellGrid = new Object[x][y];
        Object[][] entityGrid = new Object[x][y];

        for (int i = 0 ; i < y ; i++) {

            char[] row = reader.nextLine().toCharArray();

            for (int j = 0 ; j < x ; j++) {

                if (row[j] == '#') {
                    // This is a wall
                    cellGrid[i][j] = new Wall();
                } else {
                    // This is a ground
                    cellGrid[i][j] = new Ground();
                }

            }
        }

        setCellGrid(cellGrid);

        while (reader.hasNextLine()) {

            String thingName = reader.next();

            if (thingName.equals("PLAYER")) {
                x = reader.nextInt();
                y = reader.nextInt();
                entityGrid[x][y] = new Player();
            }

            // Todo : the rest of these

        }

        setEntityGrid(entityGrid);
    }

    private void setCellGrid(Object[][] cellGrid) {
        this.cellGrid = cellGrid;
    }

    private void setEntityGrid(Object[][] entityGrid) {
        this.entityGrid = entityGrid;
    }

    public int[] getLocation() {
        return null;
    }

}