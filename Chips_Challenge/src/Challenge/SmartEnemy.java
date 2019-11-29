package Challenge;

import javafx.scene.image.Image;

import java.util.*;

/**
 * @author Chuks Ajeh, Angelo Balistoy
 * @version 1.0
 */
class SmartEnemy extends Enemy {
    private Lumberjack jack = new Lumberjack();
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
    public SmartEnemy(int direction, int x, int y) {
        super(SPRITE, direction, x, y);
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
        Stack<BFSVertex> path = this.findPathToPlayer(flattenedGrid, player, entityGrid);

        // Find the next vertex the enemy should be on.
        int[] nextNode = SmartEnemy.getNextNodeInPath(path);

        int newEnemyX = this.getEnemyX() - nextNode[0];
        System.out.println(Arrays.toString(nextNode));
        int newEnemyY = this.getEnemyY() - nextNode[1];

        // Using the next location that we know, find the direction the enemy must take.
        final int[] row ={0,1,0,-1};
        final int[] col ={-1,0,1,0};
        for(int i = 0; i < 4; i++) {
            if (newEnemyX == row[i] && newEnemyY == col[i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     *Gets the next location the enemy will take.
     * @param path The path the enemy will take.
     * @return The next location in the path.
     */
    private static int[] getNextNodeInPath(Stack<BFSVertex> path) {

        BFSVertex vertex = path.pop();

        while (vertex.getDist() != 1) {
            vertex = path.pop();
        }

        return new int[]{vertex.getX(), vertex.getY()};
    }

    int counter = 0;
    /**
     * Finds a path from the smart enemy to the player.
     * @param flattenedLevel The level which combines all impassable entities and cells.
     * @param player The player.
     * @param entities The entities within the level.
     * @return The path to the player.
     */
    private Stack<BFSVertex> findPathToPlayer(int[][] flattenedLevel, Player player, Entity[][] entities) {

        int[] playerLocation = player.getLocation(entities);

        // Print out useful message 1;
        // todo: pull these out as variables pls
        if (flattenedLevel[this.getEnemyX()][this.getEnemyY()] != 1 || 1 != flattenedLevel[playerLocation[0]][playerLocation[1]]) {
            System.out.println("unable to find shortest path");
        }

        final int[] row ={-1,0,0,1};
        final int[] col ={0,-1,1,0};

        boolean[][] visited = new boolean[flattenedLevel.length][flattenedLevel[0].length];

        // set the source node as visited and enqueue
        visited[this.getEnemyX()][this.getEnemyY()] = true;

        Queue<BFSVertex> vertices = new LinkedList<>();
        Stack<BFSVertex> pathToReturn = new Stack<>();
        vertices.add(new BFSVertex(this.getEnemyX(),this.getEnemyY(),0));

        // store the minimum distance:
        int minDist = Integer.MAX_VALUE;
        // You know what they say about temporary solutions... they become permanent - Angelo 27/11/19
        // Oops - Gnome
        int srcX = vertices.peek().getX();
        int srcY = vertices.peek().getY();
        int dist = Objects.requireNonNull(vertices.peek()).getDist();

        do {

            // pop front not from queue and process it
            BFSVertex bfsVertex = vertices.poll();
            pathToReturn.push(bfsVertex);
            // source node and distance
            assert bfsVertex != null;
            srcX = bfsVertex.getX();
            srcY = bfsVertex.getY();
            dist = bfsVertex.getDist();
            System.out.print("Counter " + counter + ": ");
            System.out.print(srcX);
            System.out.print(", " + srcY);
            System.out.println(" dist:" + dist);
            // if destination is found, update minimum distance and stop
            if (srcX == playerLocation[0] && srcY == playerLocation[1]) {
                minDist = dist;
            }
            dist += 1;
            for(int i = 0 ; i < 4 ; i++ ) {
                // check for all 4 possible movements from current cell and enqueue it
                if (isValid(flattenedLevel, visited, srcX + row[i],srcY + col[i])) {
                    // mark each cell as visited and enqueue it
                    visited[srcX + row[i]][srcY + col[i]] = true;
                    int nextVertexX = srcX + row[i];
                    int nextVertexY = srcY + col[i];
                    vertices.add(new BFSVertex(nextVertexX,nextVertexY, dist));
                }
            }

            counter++;
        } while (!vertices.isEmpty() && !(srcX == playerLocation[0] && srcY == playerLocation[1]));
        // Print useful messasge 2;
        System.out.println(srcX == playerLocation[0]);
        System.out.println(srcY == playerLocation[1]);
        System.out.println(vertices.isEmpty());
        if (minDist != Integer.MAX_VALUE) {
            System.out.println(minDist);
        } else {
            System.out.println(Arrays.deepToString(flattenedLevel));
        }
        counter = 0;
        return pathToReturn;
    }


    /**
     * Checks whether the cell is valid or not for BFS.
     * @param flattenedLevel The flattened level.
     * @param visited The array showing all visited nodes.
     * @param row The row (x) coordinate
     * @param col The column (y) coordinate
     * @return if the move is valid
     */
    private static boolean isValid (int[][] flattenedLevel, boolean[][] visited, int row, int col){
        final int ROW = flattenedLevel.length - 1;
        final int COL = flattenedLevel[0].length - 1;
        return (row >= 0) &&(row<ROW)&&(col>=0)&&(col<COL) && !visited[row][col] && flattenedLevel[row][col] != 1;
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

                if (entityGrid[i][j] instanceof Enemy || cellGrid[i][j] instanceof Impassable || cellGrid[i][j]
                        instanceof Obstacle) {
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


