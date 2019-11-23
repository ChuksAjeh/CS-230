package Challenge;

import com.sun.deploy.util.Waiter;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.*;


/**
 * @author Chuks Ajeh
 * @version 1.0
 */
public class SmartEnemy extends Enemy {
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_SMART_ENEMY.png");
    }

    public SmartEnemy(int direction) {
        super(SPRITE, direction);
    }


    private int nextDirection(Level level, Player player) {

        //get the width and height of the grid:
        int width = level.getEntityGrid().length - 1;
        int height = level.getEntityGrid()[0].length - 1;

        //pull in current grid data: Cell & Entity:
        Cell[][] cellGrid = level.getCellGrid();
        Entity[][] entityGrid = level.getEntityGrid();

        //source node for the enemy/
        int srcX = getEnemyX();
        int srcY = getEnemyY();

        //find the player location.
        int sinkX = player.getLocation(entityGrid)[0];
        int sinkY = player.getLocation(entityGrid)[1];

        // walls, doors, hazards are all impassable for enemy
        int[][] dist = new int[height][width];

        for (int i = 0 ; i <= height ; i++) {
            for (int j = 0 ; j <= width ; j++) {

                Cell cell = cellGrid[i][j];

                if (cell instanceof Wall || cell instanceof Fire || cell instanceof TokenDoor || cell instanceof Water) {
                    dist[i][j] = 0;
                }

                /*

                //provides use with a set of distances
                if (cellGrid[i][j].getClass().getSimpleName().equals("Wall") || cellGrid[i][j].getClass().getSimpleName().equals("Fire")) {
                    dist[i][j] = 0;
                }
                //covers door and water obstacle
                else if (cellGrid[i][j].getClass().getSimpleName().equals("TokenDoor") || cellGrid[i][j].getClass().getSimpleName().equals("Water")) {
                    dist[i][j] = 0;
                } else {

                }

                */

            }
        }

        return 0;
    }


    // get the neighbours of the cell:
    private Cell[] getSurroundingCellsBFS(Cell[][] cellGrid, int i, int j) {

        Cell[] surround = new Cell[4];

        surround[0] = cellGrid[getEnemyX()][getEnemyY() - 1];
        surround[1] = cellGrid[getEnemyX() + 1][getEnemyY()];
        surround[2] = cellGrid[getEnemyX()][getEnemyY() + 1];
        surround[3] = cellGrid[getEnemyX() - 1][getEnemyY()];

        return surround;

    }

    //UNFINISHED
    void BFS(int x, int y, Level level) {

        // Mark all the vertices as not visited
        // (By default set as false)
        boolean visited[][] = new boolean[10][10];

        Cell[][] cellGrid = level.getCellGrid();

        // Create a queue for BFS
        LinkedList<Cell> queue = new LinkedList<Cell>();

        // Mark the current node as visited and enqueue it
        visited[x][y] = true;
        queue.add(cellGrid[x][y]);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            Cell vertex = queue.poll();
            //System.out.print(s+" ");

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it

        }


    }

    /**
     * Gets a array showing passable and impassable objects in the level
     * @param entityGrid The entity grid for the level.
     * @param cellGrid The cell grid for the level.
     * @return  An array with 0 representing impassable objects and a 1 representing passable objects
     */
    private static int[][] flatten(Entity[][]entityGrid, Cell[][] cellGrid) {

        int height = entityGrid.length;
        int width = entityGrid[0].length;

        int[][] level = new int[height][width];

        for (int i = 0 ; i < height ; i++) {
            for (int j = 0 ; j < width ; j++) {

                if (entityGrid[i][j] != null || cellGrid[i][j] instanceof Impassable) {
                    level[i][j] = 1;
                }
            }
        }

        return level;
    }

//    public static void main(String[] args) {
//        Entity[][] entity = new Entity[8][8];
//        Cell[][] cell = new Cell[8][8];
//
//        for (int i = 0; i < entity.length; i++) {
//            for (int j = 0; j < entity[i].length; j++) {
//                if (i % 2 == 1) {
//                    entity[i][j] = new FireBoots();
//                }
//            }
//        }
//
//        for (int i = 0; i < cell.length; i++) {
//            for (int j = 0; j < cell[i].length; j++) {
//                if (i % 5 == 0) {
//                    cell[i][j] = new Wall();
//                } else {
//                    cell[i][j] = new Ground();
//                }
//            }
//            int[][] toPrint = flatten(entity, cell);
//            System.out.println(Arrays.toString(toPrint));
//        }
    }


