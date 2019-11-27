package Challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Samuel Roach
 * @version 1.0
 */

public class Save {

    private int saveNo = 0;
    private String fileName = "";
    private Cell[][] cellGrid;
    private Entity[][] entityGrid;
    private final Lumberjack jack = new Lumberjack();

    public Save () {

    }

    public void saveFile(Level level) throws IOException {

        String directory;
        String levelName = level.getLevelName();

        this.cellGrid = level.getCellGrid();
        this.fileName = levelName + "_" + saveNo;
        this.saveNo += 1;

        //Create folder for the current User
        File dirFile = new File("Users/" + "Dave");
        if (!dirFile.exists()) {
            if (dirFile.mkdir()) {
                jack.log(1, "Directory for " + "DAVE" + " has been created!");
            } else {
                jack.log(1,"Failed to create directory!");
            }
        }

        //Create directory for new file
        directory = "Users/" + "Dave" + "/" + this.fileName + ".txt";
        File file = new File(directory);

        //Create the file
        if (file.createNewFile()) {
            jack.log(1,"File is created!");
        } else {
            jack.log(1,"File already exists.");
        }

        //Write Content
        FileWriter writer = new FileWriter(file);

        writeSize(level.getCellGrid(),writer);
        writeWalls(level.getCellGrid(),writer);
        writeEntities(level,writer);
        writeCells(cellGrid,writer);

        writer.close();
    }

    private void writeSize(Cell[][] cGrid, FileWriter w) throws IOException {

        int xSize = cGrid.length;
        int ySize = cGrid[0].length;

        w.write(xSize + "," + ySize + "," + "\n");
    }

    private void writeWalls(Cell[][] cGrid,FileWriter w) throws IOException {

        for (Cell[] row : cGrid) {
            for (Cell cell : row) {

                if (cell instanceof Wall) {
                    w.write("#");
                } else {
                    w.write(" ");
                }

            }
            w.write("\n");
        }
    }

    private void writeEntities(Level level, FileWriter w) throws IOException {
        for (Entity[] row : level.getEntityGrid()) {
            for (Entity entity : row) {
                if (entity instanceof Player) {

                    logWritten(entity);
                    Player player = (Player) entity;
                    int[] playerLoc = player.getLocation(level.getEntityGrid());

                    w.write("Player,");
                    w.write(playerLoc[0] + ",");
                    w.write(playerLoc[1] + ",");
                    w.write(player.getDirection() + "\n");

                } else if (entity instanceof Enemy) {

                    logWritten(entity);
                    Enemy enemy = (Enemy) entity;
                    writeEnemy(enemy, w);

                } else if (entity instanceof Key) {

                    logWritten(entity);
                    Key key = (Key) entity;
                    writeKey(key, level, w);

                } else if (entity instanceof Item) {

                    logWritten(entity);
                    Item item = (Item) entity;
                    writeItem(item, level, w);

                } else {

                    //jack.log(1, "Entity of type " + entity.toString()
                            //+ " hasn't been written. Blame Samuel.");

                }
            }
        }
    }

    private void writeEnemy(Enemy enemy, FileWriter w) throws IOException {

        w.write(enemy.getClass().getSimpleName());
        w.write(enemy.getEnemyX() + ",");
        w.write(enemy.getEnemyY() + ",");
        w.write(enemy.getDirection() + "\n");

    }

    private void writeItem(Item i, Level level, FileWriter w) throws IOException {

        w.write(i.getClass().getSimpleName() + ",");
        w.write(i.findEntity(i, level.getEntityGrid())[0] + ",");
        w.write(i.findEntity(i, level.getEntityGrid())[1] + "\n");

    }

    private void writeKey(Key key, Level level, FileWriter w) throws IOException {

        int red = (int) key.getColour().getRed() * 255;
        int blue = (int) key.getColour().getBlue() * 255;
        int green = (int) key.getColour().getGreen() * 255;
        int[] keyDoorCoords = new int[] {0,0};
        KeyDoor currentDoor;

        //Find the right KeyDoor and get its coords
        for (Cell[] row : level.getCellGrid()) {
            for (Cell cell : row) {
                if (cell instanceof KeyDoor) {
                    currentDoor = (KeyDoor) cell;
                    if (currentDoor.getColour() == key.getColour()) {
                        keyDoorCoords = currentDoor.findCell(currentDoor, level.getCellGrid());
                    }
                }
            }
        }

        int[] keyLoc = key.findEntity(key,level.getEntityGrid());

        w.write("KeyDoor" + ",");
        w.write(keyDoorCoords[0] + "," + keyDoorCoords[1] + ",");
        w.write(red + "," + blue + "," + green + ",");
        w.write(keyLoc[0] + "," + keyLoc[1]);
        w.write("\n");

    }

    private void writeCells(Cell[][] tempGrid, FileWriter w) throws IOException {

        for (Cell[] row : tempGrid) {
            for (Cell cell : row) {
                if (cell instanceof TokenDoor){

                    logWritten(cell);
                    TokenDoor tokenDoor = (TokenDoor) cell;
                    writeTokenDoor(tempGrid, tokenDoor, w);

                } else if (cell instanceof Obstacle) {

                    logWritten(cell);
                    Obstacle obstacle = (Obstacle) cell;
                    writeObstacle(tempGrid, obstacle, w);

                } else if (cell instanceof Goal) {

                    logWritten(cell);
                    Goal goal = (Goal) cell;
                    writeCellPos(tempGrid, goal, w);
                    w.write("\n");

                } else if (cell instanceof Teleporter) {

                    logWritten(cell);
                    Teleporter teleporter = (Teleporter) cell;
                    writeTeleporter(tempGrid, teleporter, w);

                }
            }
        }
    }

    private void writeTokenDoor(Cell[][] cG, TokenDoor tD, FileWriter w) throws IOException {

        writeCellPos(cG, tD, w);
        w.write(",");
        w.write(tD.getRequirement() + "\n");

    }

    private void writeObstacle(Cell[][] cellGrid, Obstacle obstacle, FileWriter writer) throws IOException {

        writeCellPos(cellGrid, obstacle, writer);
        writer.write("\n");

    }

    private void writeTeleporter(Cell[][] cellGrid, Teleporter teleporter, FileWriter writer) throws IOException {

        Teleporter pair = teleporter.getPair();

        writeCellPos(cellGrid, teleporter, writer);
        writer.write(",");
        writer.write(pair.findCell(pair,cellGrid)[0] + ",");
        writer.write(pair.findCell(pair,cellGrid)[1] + "\n");

    }

    private void writeCellPos(Cell[][] cellGrid, Cell cell, FileWriter writer) throws IOException {

        int[] cellCoords = cell.findCell(cell, cellGrid);

        writer.write(cell.getClass().getSimpleName() + ",");
        writer.write(cellCoords[0] + ",");
        writer.write(cellCoords[1] + "");

    }

    private void logWritten(Object object) {
        //jack.log(1, "Writing a " + object.getClass().getSimpleName() + " to the save file");
    }
}

/*
    Create a pointer to this.cellGrid in saveFile to level.cellGrid etc
    Create a global variable for the filewriter
    Make sure every line is < 80
    Refactor code to follow this
 */