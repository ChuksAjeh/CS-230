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
                    Player player = (Player) entity;
                    writer.write("PLAYER,");
                    writer.write(player.getLocation(level.getEntityGrid())[0] + ",");
                    writer.write(player.getLocation(level.getEntityGrid())[1] + ",");
                    writer.write(player.getDirection() + "\n");
                } else if (entity instanceof LineEnemy) {

                    LineEnemy lineEnemy = (LineEnemy) entity;
                    writer.write("LINE_ENEMY,");
                    writeEnemyCoords(lineEnemy, writer);

                } else if (entity instanceof SmartEnemy) {

                    SmartEnemy smartEnemy = (SmartEnemy) entity;
                    writer.write("SMART_ENEMY,");
                    writeEnemyCoords(smartEnemy, writer);

                } else if (entity instanceof DumbEnemy) {

                    DumbEnemy dumbEnemy = (DumbEnemy) entity;
                    writer.write("SMART_ENEMY,");
                    writeEnemyCoords(dumbEnemy, writer);

                } else if (entity instanceof WallEnemy) {

                    WallEnemy wallEnemy = (WallEnemy) entity;
                    writer.write("WALL_ENEMY");
                    writeEnemyCoords(wallEnemy, writer);
                } else if (entity instanceof Key) {

                    Key key = (Key) entity;
                    writeKey(key,level,writer);

                } else if (entity instanceof Item) {
                    Item item = (Item) entity;
                    writeItemCoords(item, level, writer);
                }
            }
        }
    }

    private void writeEnemyCoords(Enemy enemy, Writer writer) throws IOException {
        writer.write(enemy.getEnemyX() + ",");
        writer.write(enemy.getEnemyY() + ",");
        writer.write(enemy.getDirection() + "\n");
    }

    private void writeItemCoords(Item item, Level level, Writer writer) throws IOException {
        if (item instanceof FireBoots) {
            writer.write("FIRE_BOOTS,");
        } else if (item instanceof Flippers) {
            writer.write("FLIPPERS,");
        } else if (item instanceof Token) {
            writer.write("TOKEN,");
        } else {
            writer.write("NOT A FUCKING ITEM WE SHOULD BE PRINTING HERE,");
        }
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

        writer.write("KEY_DOOR,");
        writer.write(keyDoorCoords[0] + "," + keyDoorCoords[1] + ",");
        writer.write(color + ",");
        writer.write(key.findEntity(key,level.getEntityGrid())[0] + "," + key.findEntity(key,level.getEntityGrid())[1]);
    }
}

/*
    Cells and Entities I sill need to implement
    FIRE
    WATER
    TOKENDOOR
    TELEPORTER
    GOAL
 */
