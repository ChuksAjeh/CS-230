package Challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void saveFile(Cell[][] cellGrid, Entity[][] entityGrid) throws IOException {
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
        writeSize(cellGrid,writer);
        writeWalls(cellGrid,writer);
        writePlayer(entityGrid,writer);
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

    private void writePlayer(Entity[][] entityGrid, FileWriter writer) throws IOException{
        for (Entity[] row : entityGrid) {
            for (Entity entity : row) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    writer.write("PLAYER,");
                    writer.write(player.getLocation(entityGrid)[0] + ",");
                    writer.write(player.getLocation(entityGrid)[1] + ",");
                    writer.write(player.getDirection() + ",\n");
                }
            }
        }
    }
}
