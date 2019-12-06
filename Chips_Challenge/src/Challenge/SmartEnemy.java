package Challenge;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

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
     * @param position the position of the Enemy
     * @param direction The initial direction the enemy will take.
     */
    SmartEnemy(Position position, int direction) {
        super(SPRITE, position, direction);
    }

    /**
     * Gets the next direction of the Smart Enemy.
     * @param level The level being used.
     * @return An int from 0-3 representing NESW.
     */
    int nextDirection(Level level){
        // using wavefront:
        Player player = level.getPlayer();
        return this.nextDirection(level, player);
    }

    /**
     * Gets the next direction based on the player's location and impassable objects.
     * @param level The level object which holds the entity and cell grid
     * @param player The player in the level
     * @return An int from 0-3 representing NESW.
     */
    private int nextDirection(Level level, Player player) {

        Random random = new Random();

        // Grab cell and entity grid to flatten
        Cell[][] cellGrid = level.getCellGrid();
        Entity[][] entityGrid = level.getEntityGrid();

        int[][] flattenedGrid = SmartEnemy.flatten(entityGrid, cellGrid);


        // Find a path using waterfront planning (BFS)
        BFSVertex[][] waveFrontGrid = this.findPathToPlayer(flattenedGrid, player, entityGrid);
        // Find the next vertex the enemy should be on.
        int srcX= this.getPosition().x;
        int srcY = this.getPosition().y;
        int enemyDist = waveFrontGrid[srcX][srcY].getDist();
        BFSVertex nextNode = this.getFinalNode(waveFrontGrid,waveFrontGrid[srcX][srcY], enemyDist);
        int newEnemyY =nextNode.getY() - this.getPosition().y;
        int newEnemyX = nextNode.getX() - this.getPosition().x;

        // Using the next location that we know, find the direction the enemy must take.
        final int[] row = {0,1,0,-1};
        final int[] col = {-1,0,1,0};

        for(int i = 0; i < 4; i++) {
            if (newEnemyX == row[i] && newEnemyY == col[i]) {
                this.setDirection(i);
                return i;
            }
        }

        return random.nextInt(4);
    }

    private BFSVertex getFinalNode(BFSVertex[][] bfsGrid, BFSVertex node, int enemyDist) {
        //make sure this is the starting node and if in the grid and equal to zero return that node
        BFSVertex[] adjacentNodes = getSurroundingNodes(bfsGrid, node);
        for (BFSVertex adjNode : adjacentNodes) {
            if (adjNode != null) {
                if (adjNode.getDist() == enemyDist-1) {
                    return adjNode;
                }

                }
            }

        return node;
    }

    private BFSVertex[] getSurroundingNodes(BFSVertex[][] BFSgrid , BFSVertex node){
        // we assume the node passed is not null:
        BFSVertex[] adjNodes = new BFSVertex[4];
        BFSVertex up    = node.getY()-1 > 0 ? BFSgrid[node.getX()][node.getY()-1] : null;
        BFSVertex left  = node.getX()-1 > 0 ? BFSgrid[node.getX()-1][node.getY()] : null;
        BFSVertex down  = node.getY()+1 < BFSgrid[0].length ? BFSgrid[node.getX()][node.getY()+1] : null;
        BFSVertex right = node.getX()+1 < BFSgrid.length ? BFSgrid[node.getX() + 1][node.getY()] : null;

        // as long as the vertex isn't null then add it to the list of available adjacent nodes
        if(up != null){
            adjNodes[0] =up;
        }
        if(down != null){
            adjNodes[1] =down;
        }
        if(left != null){
            adjNodes[2] =left;
        }
        if(right != null){
            adjNodes[3] =right;
        }

        return adjNodes;
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

    /**
     * Finds a path from the smart enemy to the player.
     * @param flattenedLevel The level which combines all impassable entities and cells.
     * @param player The player.
     * @param entities The entities within the level.
     * @return The path to the player.
     */
    private BFSVertex[][] findPathToPlayer(int[][] flattenedLevel, Player player, Entity[][] entities) {

        Position playerPos = player.getPosition();

        // Print out useful message 1;
        final int[] row = {-1,0,0,1};
        final int[] col = {0,-1,1,0};

        boolean[][] visited = new boolean[flattenedLevel.length][flattenedLevel[0].length];

        // set the source node as visited and enqueue
        visited[playerPos.x][playerPos.y] = true;

        Queue<BFSVertex> vertices = new LinkedList<>();
        Stack<BFSVertex> pathToReturn = new Stack<>();
        vertices.add(new BFSVertex(playerPos.x, playerPos.y, 0));

        // store the minimum distance:
        int minDist = Integer.MAX_VALUE;
        // You know what they say about temporary solutions... they become permanent - Angelo 27/11/19
        // Oops - Gnome
        int srcX;
        int srcY;
        int dist;

        int counter = 0;

        do {

            // pop front not from queue and process it
            BFSVertex bfsVertex = vertices.poll();
            pathToReturn.push(bfsVertex);

            // source node and distance
            assert bfsVertex != null;

            srcX = bfsVertex.getX();
            srcY = bfsVertex.getY();
            dist = bfsVertex.getDist();


            // if destination is found, update minimum distance and stop
            if (srcX == this.getPosition().x && srcY == this.getPosition().y) {
                minDist = dist;
            }

            dist += 1;

            for (int i = 0 ; i < 4 ; i++ ) {
                // check for all 4 possible movements from current cell and enqueue
                if (isValid(flattenedLevel, visited, srcX + row[i],srcY + col[i])) {

                    // mark each cell as visited and enqueue it
                    visited[srcX + row[i]][srcY + col[i]] = true;
                    int nextVertexX = srcX + row[i];
                    int nextVertexY = srcY + col[i];

                    vertices.add(new BFSVertex(nextVertexX,nextVertexY, dist));
                }
            }

            counter += 1;

        } while (!vertices.isEmpty() && !(srcX == this.getPosition().x && srcY == this.getPosition().y));


        BFSVertex[][] bfsGrid = new BFSVertex[flattenedLevel.length][flattenedLevel[0].length];
        while(!pathToReturn.empty()){
            bfsGrid[pathToReturn.peek().getX()][pathToReturn.peek().getY()]=pathToReturn.pop();

        }
        for(int i =0; i<flattenedLevel.length; i++){
            for(int j=0; j< flattenedLevel[0].length; j++){
                //if the coordinates of the node and the coordinates of the bfs grid are equal then place in grid cell
                if(bfsGrid[i][j] == null == true){
                    bfsGrid[i][j] = null;
                }
            }
        }
        //return pathToReturn;
        return bfsGrid;
    }

    /**
     * Checks whether the cell is valid or not for BFS.
     * @param flattenedLevel The flattened level.
     * @param visited The array showing all visited nodes.
     * @param row The row (x) coordinate
     * @param col The column (y) coordinate
     * @return if the move is valid
     */
    private static boolean isValid (int[][] flattenedLevel, boolean[][] visited, int row, int col) {

        final int ROW = flattenedLevel.length - 1;
        final int COL = flattenedLevel[0].length - 1;
        return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && !visited[row][col] &&
                flattenedLevel[row][col] == 0;
    }

    /**
     * Gets a array showing passable and impassable objects in the level
     * @param entityGrid The entity grid for the level.
     * @param cellGrid The cell grid for the level.
     * @return  An array with 1 representing impassable objects and a 0 representing passable objects.
     */
    private static int[][] flatten(Entity[][]entityGrid, Cell[][] cellGrid) {

        int height = entityGrid.length;
        int width = entityGrid[0].length;

        int[][] level = new int[height][width];

        for (int i = 0 ; i < height ; i++ ) {
            for (int j = 0 ; j < width ; j++ ) {
                //Impassable objects (1): Entities and anything that is not ground.
                if (!(entityGrid[i][j] instanceof SmartEnemy) && !(cellGrid[i][j] instanceof Ground)) {
                    // if (entityGrid[i][j] instanceof Enemy || !cellGrid[i][j].isPassable() || cellGrid[i][j] instanceof Obstacle) {
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

