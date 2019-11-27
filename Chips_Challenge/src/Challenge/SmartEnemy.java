package Challenge;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chuks Ajeh, Angelo Balistoy
 * @version 1.0
 */
public class SmartEnemy extends Enemy {

    /**
     * The sprite used to represent the smart enemy.
     */
    private static final Image SPRITE;

    static {
        SPRITE = new Image("images/ENTITY_SMART_ENEMY.png");
    }

    /**
     * Constructs an smart enemy
     * @param direction The initial direction the enemy will take.
     */
    public SmartEnemy(int direction) {
        super(SPRITE, direction);
    }

    // Do we have to use level's grids or can we use the enemies' own entity and cell grid.

    /**
     * Gets the next direction based on the player's location and impassable objects.
     * @param level The level object which holds the entity and cell grid
     * @param player The player in the level
     * @return An int from 0-3 representing NESW.
     */
    public int nextDirection(Level level, Player player) {

        // Grab cell and entity grid to flatten
        Cell[][] cellGrid = level.getCellGrid();
        Entity[][] entityGrid = level.getEntityGrid();

        int[][] flattenedGrid = SmartEnemy.flatten(entityGrid, cellGrid);

        // Find a path using waterfront planning (BFS)
        Queue<BFSVertex> path = SmartEnemy.findPathToPlayer(flattenedGrid, player, entityGrid);

        // Find the next vertex the enemy should be on.
        int[] nextNode = SmartEnemy.getNextNodeInPath(path);

        int newEnemyX = nextNode[0];
        int newEnemyY = nextNode[1];

        // Using the next location that we know, find the direction the enemy must take.
        if (0 == this.getEnemyX() - newEnemyX && 1 == this.getEnemyY() - newEnemyY) {
            return 0;
        } else if (-1 == this.getEnemyX() - newEnemyX && 0 == this.getEnemyY() - newEnemyY) {
            return 1;
        } else if (0 == this.getEnemyX() - newEnemyX && -1 == this.getEnemyY() - newEnemyY) {
            return 2;
        } else {
            return 3;
        }

    }

    /**
     *Gets the next location the enemy will take.
     * @param path The path the enemy will take.
     * @return The next location in the path.
     */
    private static int[] getNextNodeInPath(Queue<BFSVertex> path) {

        BFSVertex vertex = path.poll();

        while (vertex.getDist() != 1) {
            vertex = path.poll();
        }

        return new int[]{vertex.getX(), vertex.getY()};
    }


//    /** IS THIS NEEDED? IDK ILL JUST COMMENT IT AWAY
//     * gets the surr
//     * @param cellGrid
//     * @param i
//     * @param j
//     * @return
//     */
//    private Cell[] getSurroundingCellsBFS(Cell[][] cellGrid, int i, int j) {
//
//        Cell[] surround = new Cell[4];
//
//        surround[0] = cellGrid[getEnemyX()][getEnemyY() - 1];
//        surround[1] = cellGrid[getEnemyX() + 1][getEnemyY()];
//        surround[2] = cellGrid[getEnemyX()][getEnemyY() + 1];
//        surround[3] = cellGrid[getEnemyX() - 1][getEnemyY()];
//
//        return surround;
//
//    }

    /**
     * Finds a path from the smart enemy to the player.
     * @param flattenedLevel The level which combines all impassable entities and cells.
     * @param player The player.
     * @param entities The entities within the level.
     * @return The path to the player.
     */
    private static Queue<BFSVertex> findPathToPlayer(int[][] flattenedLevel, Player player, Entity[][] entities) {

        int[] playerLocation = player.getLocation(entities);

        // Print out useful message 1;
        // todo: pull these out as variables pls
        if (flattenedLevel[new SmartEnemy(0).getEnemyX()][new SmartEnemy(0).getEnemyY()] != 1 || 1 != flattenedLevel[playerLocation[0]][playerLocation[1]]) {
            System.out.println("unable to find shortest path");
        }

        final int[] row ={-1,0,0,1};
        final int[] col ={0,-1,1,0};

        boolean[][] visited = new boolean[flattenedLevel.length][flattenedLevel[0].length];

        // set the source node as visited and enqueue
        visited[new SmartEnemy(0).getEnemyX()][new SmartEnemy(0).getEnemyY()] = true;

        Queue<BFSVertex> vertices = new LinkedList<>();
        vertices.add(new BFSVertex(new SmartEnemy(0).getEnemyX(),new SmartEnemy(0).getEnemyY(),0));

        // store the minimum distance:
        int minDist = Integer.MAX_VALUE;
        // You know what they say about temporary solutions... they become permanent - Angelo 27/11/19
        // Oops - Gnome
        int srcX = vertices.peek().getX();
        int srcY = vertices.peek().getY();
        int dist = vertices.peek().getDist();

        do {

            // pop front not from queue and process it
            BFSVertex bfsVertex = vertices.poll();
            // source node and distance
            assert bfsVertex != null;
            srcX = bfsVertex.getX();
            srcY = bfsVertex.getY();
            dist = bfsVertex.getDist();

            // if destination is found, update minimum distance and stop
            if (srcX == playerLocation[0] && srcY == playerLocation[1]) {
                minDist = dist;
                return vertices;

            }

            for(int i = 0 ; i < 4 ; i++ ) {
                // check for all 4 possible movements from current cell and enqueue it
                if (isValid(flattenedLevel, visited, srcX + row[i],srcY + col[i])) {
                    // mark each cell as visited and enqueue it
                    visited[srcX + row[i]][srcY + col[i]] = true;
                    vertices.add(new BFSVertex(srcX+row[i],srcY+col[i],dist+1));
                }
            }

        } while (!vertices.isEmpty() && srcX == playerLocation[0] && srcY == playerLocation[1]);

        // Print useful messasge 2;
        if (minDist != Integer.MAX_VALUE) {
            System.out.println(minDist);
        } else {
            System.out.println("Destination can't be reached.");
        }
        return vertices;
    }


    /**
     * Checks whether the cell is valid or not for BFS.
     * @param level The flattened level.
     * @param visited The array showing all visited nodes.
     * @param row The row (x) coordinate
     * @param col The column (y) coordinate
     * @return
     */
    private static boolean isValid (int[][] level, boolean[][] visited, int row, int col){
        final int ROW = level.length - 1;
        final int COL = level[0].length - 1;
        return (row >= 0) &&(row<ROW)&&(col>=0)&&(col<COL) && !visited[ROW][COL];
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

        for (int i = 0 ; i < height ; i++)  {
            for (int j = 0 ; j < width ; j++ ) {

                if (entityGrid[i][j] != null || cellGrid[i][j] instanceof Impassable) {
                    level[i][j] = 1;
                }

            }
        }

        return level;
    }

//    public static void main(String[] args){
//
//        SmartEnemy s = new SmartEnemy(0);
//        Player player = new Player(0);
//
//        int[][] mat =
//                {
//                        { 1, 1, 1, 1, 1, 0, 0, 1, 1, 1 },
//                        { 0, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
//                        { 0, 0, 1, 0, 1, 1, 1, 0, 0, 1 },
//                        { 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
//                        { 0, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
//                        { 1, 0, 1, 1, 1, 0, 0, 1, 1, 0 },
//                        { 0, 0, 0, 0, 1, 0, 0, 1, 0, 1 },
//                        { 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
//                        { 1, 1, 1, 1, 1, 0, 0, 1, 1, 1 },
//                        { 0, 0, 1, 0, 0, 1, 1, 0, 0, 1 },
//                };
//
//    }
    }


