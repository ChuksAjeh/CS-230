package Challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @author Samuel Roach
 * @version 1.0
 */

public class Save {

    private String fileName = "";
    private Level level;
    private Cell[][] cellGrid;
    private Entity[][] entityGrid;
    private FileWriter w;
    private final Lumberjack jack = new Lumberjack();

    public Save () {

    }

    public void saveFile(Level level) throws IOException {

        String directory;
        String levelName = level.getLevelName();

        this.level = level;
        this.cellGrid = level.getCellGrid();
        this.entityGrid = level.getEntityGrid();
        this.fileName = levelName + "_" + "SAVE";

        //Create folder for the current User
        File dirFile = new File("Users/" + "Dave");
        if (!dirFile.exists()) {
            if (dirFile.mkdir()) {
                jack.log(1, "Directory for " + "DAVE" +
                        " has been created!");
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
        this.w = new FileWriter(file);

        writeSize();
        writeWalls();
        writeEntities(level);
        writeCells();

        w.close();
    }

    private void writeSize() throws IOException {

        int xSize = this.cellGrid.length;
        int ySize = this.cellGrid[0].length;

        this.w.write(xSize + "," + ySize + "," + "\n");
    }

    private void writeWalls() throws IOException {

        for (int x = 0 ; x < this.cellGrid.length ; x++ ) {
            for (int y = 0 ; y < this.cellGrid[x].length ; y++ ) {

                if (cellGrid[y][x] instanceof Wall) {
                    this.w.write('#');
                } else {
                    this.w.write(' ');
                }

            }
            this.w.write('\n');
        }
    }

    private void writeEntities(Level level) throws IOException {
        for (Entity[] row : level.getEntityGrid()) {
            for (Entity entity : row) {
                if (entity instanceof Player) {

                    logWritten(entity);
                    Player player = (Player) entity;
                    int[] plyLoc = player.getLocation(this.entityGrid);

                    this.w.write("Player,");
                    this.w.write(plyLoc[0] + ",");
                    this.w.write(plyLoc[1] + ",");
                    this.w.write(player.getDirection() + "\n");

                } else if (entity instanceof Enemy) {

                    logWritten(entity);
                    Enemy enemy = (Enemy) entity;
                    writeEnemy(enemy);

                } else if (entity instanceof Key) {

                    logWritten(entity);
                    Key key = (Key) entity;
                    writeKey(key);

                } else if (entity instanceof Item) {

                    logWritten(entity);
                    Item item = (Item) entity;
                    writeItem(item);

                } else {

                    //jack.log(1, "Entity of type " + entity.toString()
                            //+ " hasn't been written. Blame Samuel.");

                }
            }
        }
    }

    private void writeEnemy(Enemy enemy) throws IOException {

        this.w.write(enemy.getClass().getSimpleName());
        this.w.write(enemy.getEnemyX() + ",");
        this.w.write(enemy.getEnemyY() + ",");
        this.w.write(enemy.getDirection() + "\n");

    }

    private void writeItem(Item i) throws IOException {

        int[] itemPos = level.getLocation(entityGrid, i);

        this.w.write(i.getClass().getSimpleName() + ",");
        this.w.write(itemPos[0] + ",");
        this.w.write(itemPos[1] + "\n");

    }

    private void writeKey(Key key) throws IOException {

        int red = (int) key.getColour().getRed() * 255;
        int blue = (int) key.getColour().getBlue() * 255;
        int green = (int) key.getColour().getGreen() * 255;
        int[] keyDoorCoords = new int[] {0,0};
        KeyDoor cDoor; //Current door

        //Find the right KeyDoor and get its coords
        for (Cell[] row : this.cellGrid) {
            for (Cell cell : row) {
                if (cell instanceof KeyDoor) {
                    cDoor = (KeyDoor) cell;
                    if (cDoor.getColour() == key.getColour()) {
                        keyDoorCoords = cDoor.findCell(cDoor, this.cellGrid);
                    }
                }
            }
        }

        int[] keyLoc = level.getLocation(entityGrid, key);

        this.w.write("KeyDoor" + ",");
        this.w.write(keyDoorCoords[0] + "," + keyDoorCoords[1] + ",");
        this.w.write(red + "," + blue + "," + green + ",");
        this.w.write(keyLoc[0] + "," + keyLoc[1]);
        this.w.write("\n");

    }

    private void writeCells() throws IOException {

        ArrayList<Teleporter> writtenTP = new ArrayList<>();

        for (Cell[] row : this.cellGrid) {
            for (Cell cell : row) {
                if (cell instanceof TokenDoor) {

                    logWritten(cell);
                    TokenDoor tokenDoor = (TokenDoor) cell;
                    writeTokenDoor(tokenDoor);

                } else if (cell instanceof KeyDoor) {

                    //Prevent it from re-writing KeyDoor
                    //This is just a catch. That's it.

                } else if (cell instanceof Obstacle) {

                    logWritten(cell);
                    Obstacle obstacle = (Obstacle) cell;
                    writeObstacle(obstacle);

                } else if (cell instanceof Goal) {

                    logWritten(cell);
                    Goal goal = (Goal) cell;
                    writeCellPos(goal);
                    this.w.write("\n");

                } else if (cell instanceof Teleporter) {

                    logWritten(cell);
                    Teleporter teleporter = (Teleporter) cell;
                    writeTeleporter(teleporter, writtenTP);

                }
            }
        }
    }

    private void writeTokenDoor(TokenDoor tD) throws IOException {

        writeCellPos(tD);
        this.w.write(",");
        this.w.write(tD.getRequirement() + "\n");

    }

    private void writeObstacle(Obstacle obstacle) throws IOException {

        writeCellPos(obstacle);
        this.w.write("\n");

    }

    private void writeTeleporter(Teleporter teleporter, ArrayList<Teleporter> writtenTP) throws IOException {

        Teleporter pair = teleporter.getPair();
        int[] pairCell = pair.findCell(pair, this.cellGrid);

        if (writtenTP.contains(teleporter)) {
            //jack.log(1,"Cannot write teleporter");
        } else {
            writeCellPos(teleporter);
            this.w.write(",");
            this.w.write(pairCell[0] + ",");
            this.w.write(pairCell[1] + "\n");
        }

        writtenTP.add(teleporter);
        writtenTP.add(pair);
    }

    private void writeCellPos(Cell cell) throws IOException {

        int[] cellCoords = cell.findCell(cell, this.cellGrid);

        this.w.write(cell.getClass().getSimpleName() + ",");
        this.w.write(cellCoords[0] + ",");
        this.w.write(cellCoords[1] + "");

    }

    private void logWritten(Object object) {
        //jack.log(1, "Writing a " + object.getClass().getSimpleName() + " to the save file");
    }
}