package Challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author ..
 * @version 1.0
 */

public class Save {

    private static int saveNo = 0;
    private static String saveName = "SaveFile";

    private String fileName;
    private Lumberjack jack = new Lumberjack();

    public Save () {
    }

    public void saveFile(Level level) throws IOException {
        String directory;

        this.fileName = saveName + "_" + saveNo;
        saveNo++;

        directory = "Level_Files/" + this.fileName;
        File file = new File(directory);

        //Create the file
        if (file.createNewFile())
        {
            jack.log(1,"File is created!");
        } else {
            jack.log(1,"File already exists.");
        }

        //Write Content
        FileWriter writer = new FileWriter(file);
        writeSize(level.getCellGrid(),writer);
        writeWalls(level.getCellGrid(),writer);
        writeEntities(level,writer);
        writeCells(level,writer);
        writer.close();
    }

    private void writeSize(Cell[][] cellGrid, FileWriter writer) throws IOException{
        int xSize = cellGrid.length;
        int ySize = cellGrid[0].length;

        writer.write(xSize+","+ySize+","+"\n");
    }

    private void writeWalls(Cell[][] cellGrid,FileWriter writer) throws IOException{
        for (Cell[] row : cellGrid) {
            for (Cell cell : row) {
                if (cell instanceof Wall) {
                    writer.write("#");
                } else {
                    writer.write(" ");
                }
            }
            writer.write("\n");
        }
    }

    private void writeEntities(Level level, FileWriter writer) throws IOException{
        for (Entity[] row : level.getEntityGrid()) {
            for (Entity entity : row) {
                if (entity instanceof Player) {

                    logWritten(entity);
                    Player player = (Player) entity;
                    writer.write("PLAYER,");
                    writer.write(player.getLocation(level.getEntityGrid())[0] + ",");
                    writer.write(player.getLocation(level.getEntityGrid())[1] + ",");
                    writer.write(player.getDirection() + "\n");

                } else if (entity instanceof Enemy) {

                    logWritten(entity);
                    Enemy enemy = (Enemy) entity;
                    writeEnemy(enemy, writer);

                } else if (entity instanceof Key) {

                    logWritten(entity);
                    Key key = (Key) entity;
                    writeKey(key, level, writer);

                } else if (entity instanceof Item) {

                    logWritten(entity);
                    Item item = (Item) entity;
                    writeItem(item, level, writer);

                } else {

                    jack.log(1, "Entity of type " + entity.getClass().getSimpleName() + " hasn't been" +
                            "written. Blame Samuel.");

                }
            }
        }
    }

    private void writeEnemy(Enemy enemy, Writer writer) throws IOException {
        writer.write(enemy.getClass().getSimpleName());
        writer.write(enemy.getEnemyX() + ",");
        writer.write(enemy.getEnemyY() + ",");
        writer.write(enemy.getDirection() + "\n");
    }

    private void writeItem(Item item, Level level, Writer writer) throws IOException {
        writer.write(item.getClass().getSimpleName() + ",");
        writer.write(item.findEntity(item, level.getEntityGrid())[0] + ",");
        writer.write(item.findEntity(item, level.getEntityGrid())[1] + "\n");
    }

    private void writeKey(Key key, Level level, Writer writer) throws IOException {
        String color = key.getColour().toString();
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

        writer.write(key.getClass().getSimpleName() + ",");
        writer.write(keyDoorCoords[0] + "," + keyDoorCoords[1] + ",");
        writer.write(color + ",");
        writer.write(key.findEntity(key,level.getEntityGrid())[0] + "," + key.findEntity(key,level.getEntityGrid())[1]);
    }

    private void writeCells(Level level, FileWriter writer) throws IOException {
        Cell[][] tempGrid = level.getCellGrid();

        for (Cell[] row : tempGrid) {
            for (Cell cell : row) {
                if (cell instanceof TokenDoor){

                    logWritten(cell);
                    TokenDoor tokenDoor = (TokenDoor) cell;
                    writeTokenDoor(tempGrid, tokenDoor, writer);

                } else if (cell instanceof Obstacle) {

                    logWritten(cell);
                    Obstacle obstacle = (Obstacle) cell;
                    writeObstacle(tempGrid, obstacle, writer);

                } else if (cell instanceof Goal) {

                    logWritten(cell);
                    Goal goal = (Goal) cell;
                    writeCellPos(tempGrid, goal, writer);
                    writer.write("\n");

                } else if (cell instanceof Teleporter) {

                    /*logWritten(cell);
                    Teleporter teleporter = (Teleporter) cell;
                    writeTeleporter(tempGrid, teleporter, writer);*/

                }
            }
        }
    }

    private void writeTokenDoor(Cell[][] cellGrid, TokenDoor tokenDoor, FileWriter writer) throws IOException{

        writeCellPos(cellGrid, tokenDoor, writer);
        writer.write(",");
        writer.write(tokenDoor.getRequirement() + "\n");
    }

    private void writeObstacle(Cell[][] cellGrid, Obstacle obstacle, FileWriter writer) throws IOException{

        writeCellPos(cellGrid, obstacle, writer);
        writer.write("\n");

    }

    private void writeTeleporter(Cell[][] cellGrid, Teleporter teleporter, FileWriter writer) throws IOException {
        Teleporter pair = new Teleporter();
    }

    private void writeCellPos(Cell[][] cellGrid, Cell cell, FileWriter writer) throws IOException {

        int[] cellCoords = cell.findCell(cell, cellGrid);

        writer.write(cell.getClass().getSimpleName() + ",");
        writer.write(cellCoords[0] + ",");
        writer.write(cellCoords[1]);
    }

    private void logWritten(Object object) {
        jack.log(1, "Writing a " + object.getClass().getSimpleName() + "to the savefile");
    }
}

/*
    Cells and Entities I sill need to implement
    --FIRE
    --WATER
    --TOKENDOOR
    TELEPORTER
    --Goal
 */
