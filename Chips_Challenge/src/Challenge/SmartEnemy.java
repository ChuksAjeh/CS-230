package Challenge;

import javafx.scene.image.Image;

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



    public void  nextDirection(Level level, Player player) {
      
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
        boolean[][] visited = new boolean[10][10];

        Cell[][] cellGrid = level.getCellGrid();

        // Create a queue for BFS
        LinkedList<Cell> queue = new LinkedList<>();

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

    public static void Breadth (int[][]level, Player player, Entity[][] entities){
        int[] playerLocation = player.getLocation(entities);

        if(level[new SmartEnemy(0).getEnemyX()][new SmartEnemy(0).getEnemyY()] != 1 ||level[playerLocation[0]][playerLocation[1]]!=1){
            System.out.println("unable to find shortest path");
        }

        final int[] row ={-1,0,0,1};
        final int[] col ={0,-1,1,0};
        boolean[][] visited =new boolean[level.length][level[0].length];
       //set the source node as visited and enqueue
        visited[new SmartEnemy(0).getEnemyX()][new SmartEnemy(0).getEnemyY()] = true;
        Queue<BFS> vertices = new LinkedList<>();
        vertices.add(new BFS(new SmartEnemy(0).getEnemyX(),new SmartEnemy(0).getEnemyY(),0));

        //store the minimum distance:
        int minDist = Integer.MAX_VALUE;


        while(!vertices.isEmpty()){
            // pop front not from queue and process it
            BFS bfs = vertices.poll();
            // source node and distance
            int srcX = bfs.getX();
            int srcY =bfs.getY();
            int dist = bfs.getDist();

            //if destination is found, update minimum distance and stop
            if(srcX == playerLocation[0] && srcY == playerLocation[1]){
                minDist = dist;
                break;
            }

            for(int i =0; i<4; i++){
                //check for all 4 possible movements from current cell and enqueue it
                if(isValid(level, visited, srcX+row[i],srcY+col[i])){
                    //mark each cell as visited and enqueue it
                    visited[srcX+row[i]][srcY+col[i]] = true;
                    vertices.add(new BFS(srcX+row[i],srcY+col[i],dist+1));
                }
            }

        }

        if(minDist != Integer.MAX_VALUE){
            System.out.println(minDist);
        }else{
            System.out.println("Destination can't be reached.");
        }
    }


    //check whether it is a valid cell or not.
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

    public static void main(String[] args){

        SmartEnemy s = new SmartEnemy(0);
        Player player = new Player(0);

        int[][] mat =
                {
                        { 1, 1, 1, 1, 1, 0, 0, 1, 1, 1 },
                        { 0, 1, 1, 1, 1, 1, 0, 1, 0, 1 },
                        { 0, 0, 1, 0, 1, 1, 1, 0, 0, 1 },
                        { 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
                        { 0, 0, 0, 1, 0, 0, 0, 1, 0, 1 },
                        { 1, 0, 1, 1, 1, 0, 0, 1, 1, 0 },
                        { 0, 0, 0, 0, 1, 0, 0, 1, 0, 1 },
                        { 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
                        { 1, 1, 1, 1, 1, 0, 0, 1, 1, 1 },
                        { 0, 0, 1, 0, 0, 1, 1, 0, 0, 1 },
                };

    }
    }


