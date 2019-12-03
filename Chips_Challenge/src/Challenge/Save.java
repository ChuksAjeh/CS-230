package Challenge;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
 * This class is used to save the game state each tick to ensure Player
 * progress is never lost. Games may be loaded from original files or their
 * created save file variants produced by this class
 * @author Samuel Roach, George Carpenter
 * @version 1.0
 */
class Save {

    /**
     * The Level being saved
     */
    private Level level;

    /**
     * The cell grid being saved
     */
    private Cell[][] cellGrid;

    /**
     * The entity grid being saved
     */
    private Entity[][] entityGrid;

    /**
     * The file writer object
     */
    private FileWriter writer;

    /**
     * Because why not, I'm on a roll
     */
    private final Lumberjack jack = new Lumberjack();

    /**
     * Nice constructor
     */
    Save() {

    }

    /**
     * Saves the current Level to a file
     * @param level the level to save
     */
    void saveFile(Level level) {

        String directory;
        String levelName = level.getLevelName();

        this.level = level;
        this.cellGrid = level.getCellGrid();
        this.entityGrid = level.getEntityGrid();

        String fileName = levelName + "_" + "SAVE";

        // Create folder for the current User
        File dirFile = new File("Users/" + "Dave");

        if (!dirFile.exists()) {
            if (dirFile.mkdir()) {
                jack.log(1, "Directory for " + "DAVE" + " has been created!");
            } else {
                jack.log(1,"Failed to create directory!");
            }
        }

        // Create directory for new file
        directory = "Users/" + "Dave" + "/" + fileName + ".txt";
        File file = new File(directory);

        try {

            // Create the file
//            if (file.createNewFile()) {
//                jack.log(1,"File is created!");
//            } else {
//                jack.log(1,"File already exists.");
//            }

            // Write Content
            this.writer = new FileWriter(file);

            int[] size = writeSize();
            writeWalls(size);
            writeEntities(level);
            writeCells();

            writer.close();

        } catch (IOException e) {
            // Nothing, because Sam is a dumdum :p
        }

    }

//
//  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
//  8                                                   8
//  8  a---------------a                                8
//  8  |     Chips     |                                8
//  8  |   Challenge   |                                8
//  8  |    Vol 1.0    |                               8"
//  8  "---------------"                               8a
//  8                                                   8
//  8                                                   8
//  8                      ,aaaaa,                      8
//  8                    ad":::::"ba                    8
//  8                  ,d::;gPPRg;::b,                  8
//  8                  d::dP'   `Yb::b                  8
//  8                  8::8)     (8::8                  8
//  8                  Y;:Yb     dP:;P  O               8
//  8                  `Y;:"8ggg8":;P'                  8
//  8                    "Yaa:::aaP"                    8
//  8                       """""                       8
//  8                                                   8
//  8                       ,d"b,                       8
//  8                       d:::8                       8
//  8                       8:::8                       8
//  8                       8:::8                       8
//  8                       8:::8                       8
//  8                       8:::8                       8
//  8                  aaa  `bad'  aaa                  8
//  """""""""""""""""""' `"""""""""' `"""""""""""""""""""
//
//  Figure VI - A proper format!
//

    /**
     * Writes the size of the level to the save file,
     * before passing it to the remaining save functions
     * @return the size of the Level grids, used elsewhere
     * @throws IOException because IO is messy
     */
    private int[] writeSize() throws IOException {

        int xSize = this.cellGrid.length;
        int ySize = this.cellGrid[0].length;

        this.writer.write(xSize + "," + ySize + "," + "\n");

        return new int[] {xSize, ySize};
    }

    /**
     * Writes the walls to the save file diagram
     * @param size the size of the grids
     * @throws IOException because IO is messy
     */
    private void writeWalls(int[] size) throws IOException {

        // This is because Gnome is a big dumdum
        // Buying 1x PivotTable? I think it's called that..

        Cell[][] wonkyGrid = new Cell[size[1]][size[0]];

        for (int y = 0 ; y < this.cellGrid.length ; y ++ ) {
            for (int x = 0 ; x < this.cellGrid[0].length ; x ++) {

                wonkyGrid[x][y] = this.cellGrid[y][x];

            }
        }

        for (Cell[] cells : wonkyGrid) {
            for (Cell cell : cells) {

                if (cell instanceof Wall) {
                    this.writer.write('#');
                } else {
                    this.writer.write(' ');
                }

            }

            this.writer.write("\n");
        }
    }

    /**
     * Driver to write Entitys to the save file
     * @param level the Level Object to save from
     * @throws IOException because IO is messy
     */
    private void writeEntities(Level level) throws IOException {

        for (Entity[] row : level.getEntityGrid()) {
            for (Entity entity : row) {
                writeEntity(entity);
            }
        }

    }

    /**
     * Writes an Entity to the save file
     * @param entity the Entity to save
     * @throws IOException because IO is messy
     */
    private void writeEntity(Entity entity) throws IOException {

        if (entity instanceof Player) {

            // logWritten(entity);

            Player player = (Player) entity;
            Position playerPos = player.getPosition();

            this.writer.write("Player,");
            this.writer.write(playerPos.x + ",");
            this.writer.write(playerPos.y + ",");
            this.writer.write(player.getDirection() + ",\n");

        } else if (entity instanceof Enemy) {

            // logWritten(entity);

            Enemy enemy = (Enemy) entity;
            Position enemyPos = enemy.getPosition();

            this.writer.write(enemy.getClass().getSimpleName() + ",");
            this.writer.write(enemyPos.x + ",");
            this.writer.write(enemyPos.y + ",");
            this.writer.write(enemy.getDirection() + ",\n");

        } else if (entity instanceof Key) {

            // logWritten(entity);

            Key key = (Key) entity;
            writeKey(key);

        } else if (entity instanceof Item) {

            // logWritten(entity);

            Item item = (Item) entity;
            writeItem(item);

        } else {

          //  jack.log(1, "Entity of type " + entity.toString() + " hasn't been written. Blame Samuel.");

        }

    }

    /**
     * Writes Items to the save file
     * @param i the Item to save
     * @throws IOException because IO is messy
     */
    private void writeItem(Item i) throws IOException {

        int[] itemPos = level.getLocation(entityGrid, i);

        this.writer.write(i.getClass().getSimpleName() + ",");
        this.writer.write(itemPos[0] + ",");
        this.writer.write(itemPos[1] + ",\n");

    }

    /**
     * Writes Keys and by extension KeyDoors to the save file
     * @param key the Key to save
     * @throws IOException because IO is messy
     */
    private void writeKey(Key key) throws IOException {

        String colour = key.getColour().toString();

        int[] keyDoorCoords = new int[] {0,0};

        KeyDoor cDoor; // Current door

        // Find the right KeyDoor and get its coords
        for (Cell[] row : this.cellGrid) {
            for (Cell cell : row) {

                if (cell instanceof KeyDoor) {

                    cDoor = (KeyDoor) cell;

                    if (cDoor.getColour().toString().equals(key.getColour().toString())) {
                        keyDoorCoords = this.level.getLocation(this.cellGrid, cDoor);
                    }
                }

            }
        }

        int[] keyLoc = level.getLocation(entityGrid, key);

        this.writer.write("KeyDoor" + ",");
        this.writer.write(keyDoorCoords[0] + "," + keyDoorCoords[1] + ",");
        this.writer.write(colour + ",");
        this.writer.write(keyLoc[0] + "," + keyLoc[1]);
        this.writer.write(",\n");

    }

    /**
     * Driver to save all Cells
     * @throws IOException because IO is messy
     */
    private void writeCells() throws IOException {

        ArrayList<Teleporter> writtenTP = new ArrayList<>();

        for (Cell[] row : this.cellGrid) {
            for (Cell cell : row) {
                if (cell instanceof TokenDoor) {

                    // logWritten(cell);

                    TokenDoor tokenDoor = (TokenDoor) cell;
                    writeTokenDoor(tokenDoor);

                } else if (cell instanceof KeyDoor) {

//                    Prevent it from re-writing KeyDoor
//                    This is just a catch. That's it.

                } else if (cell instanceof Obstacle) {

                    // logWritten(cell);

                    Obstacle obstacle = (Obstacle) cell;
                    writeObstacle(obstacle);

                } else if (cell instanceof Goal) {

                    // logWritten(cell);

                    Goal goal = (Goal) cell;
                    writeCellPos(goal);

                    this.writer.write(",\n");

                } else if (cell instanceof Teleporter) {

                    // logWritten(cell);

                    Teleporter teleporter = (Teleporter) cell;
                    writeTeleporter(teleporter, writtenTP);

                }
            }
        }
    }

    /**
     * Writes TokenDoors to the save file
     * @param tD the TokenDoor to save
     * @throws IOException because IO is messy
     */
    private void writeTokenDoor(TokenDoor tD) throws IOException {

        writeCellPos(tD);
        this.writer.write(",");
        this.writer.write(tD.getRequirement() + ",\n");

    }

    /**
     * Writes Obstacles to the save file
     * @param obstacle the Obstacle to save
     * @throws IOException because IO is messy
     */
    private void writeObstacle(Obstacle obstacle) throws IOException {

        writeCellPos(obstacle);
        this.writer.write(",\n");

    }

    /**
     * Writes Teleporters to the save file
     * @param teleporter the Teleporter to save
     * @param writtenTP the list of already written teleporters,
     *                  used to avoid duplication (not that it matters)
     * @throws IOException because IO is messy
     */
    private void writeTeleporter(Teleporter teleporter, ArrayList<Teleporter> writtenTP) throws IOException {

        Teleporter pair = teleporter.getPair();
        int[] pairCell = this.level.getLocation(this.cellGrid, pair);

        if (writtenTP.contains(teleporter)) {

//            jack.log(1,"Cannot write teleporter");

        } else {

            writeCellPos(teleporter);
            this.writer.write(",");
            this.writer.write(pairCell[0] + ",");
            this.writer.write(pairCell[1] + ",\n");

        }

        writtenTP.add(teleporter);
        writtenTP.add(pair);

    }

    /**
     * Writes a cell position to the save file
     * @param cell the Cell to save the position of
     * @throws IOException because IO is messy
     */
    private void writeCellPos(Cell cell) throws IOException {

        int[] cellCoords = this.level.getLocation(this.cellGrid, cell);

        this.writer.write(cell.getClass().getSimpleName() + ",");
        this.writer.write(cellCoords[0] + ",");
        this.writer.write(cellCoords[1] + "");

    }

    /**
     * Writes a Log to the terminal when a thing is written to file.
     * @param object what to write
     */
    private void logWritten(Object object) {
        jack.log(1, "Writing a " + object.getClass().getSimpleName() + " to the save file");
    }

}
