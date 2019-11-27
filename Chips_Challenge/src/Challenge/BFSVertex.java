package Challenge;

import java.util.LinkedList;
import java.util.Queue;

public class BFSVertex {
    int x;
    int y;
    int dist;

    public BFSVertex(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
//
//    public static void Breadth (int[][]level, Player player, Entity[][] entities){
//        int[] playerLocation = player.getLocation(entities);
//
//        if(level[new SmartEnemy(0).getEnemyX()][new SmartEnemy(0).getEnemyY()] != 1 ||level[playerLocation[0]][playerLocation[1]]!=1){
//            System.out.println("unable to find shortest path");
//        }
//
//        final int[] row ={-1,0,0,1};
//        final int[] col ={0,-1,1,0};
//        boolean[][] visited =new boolean[level.length][level[0].length];
//        //set the source node as visited and enqueue
//        visited[new SmartEnemy(0).getEnemyX()][new SmartEnemy(0).getEnemyY()] = true;
//        Queue<BFS> vertices = new LinkedList<>();
//        vertices.add(new BFS(new SmartEnemy(0).getEnemyX(),new SmartEnemy(0).getEnemyY(),0));
//
//        //store the minimum distance:
//        int minDist = Integer.MAX_VALUE;
//
//
//        while(!vertices.isEmpty()){
//            // pop front not from queue and process it
//            BFS bfs = vertices.poll();
//            // source node and distance
//            int srcX = bfs.getX();
//            int srcY =bfs.getY();
//            int dist = bfs.getDist();
//
//            //if destination is found, update minimum distance and stop
//            if(srcX == playerLocation[0] && srcY == playerLocation[1]){
//                minDist = dist;
//                break;
//            }
//
//            for(int i =0; i<4; i++){
//                //check for all 4 possible movements from current cell and enqueue it
//                if(isValid(level, visited, srcX+row[i],srcY+col[i])){
//                    //mark each cell as visited and enqueue it
//                    visited[srcX+row[i]][srcY+col[i]] = true;
//                    vertices.add(new BFS(srcX+row[i],srcY+col[i],dist+1));
//                }
//            }
//
//        }
//
//        if(minDist != Integer.MAX_VALUE){
//            System.out.println(minDist);
//        }else{
//            System.out.println("Destination can't be reached.");
//        }
//    }
//
//
//    //check whether it is a valid cell or not.
//    private static boolean isValid (int[][] level, boolean[][] visited, int row, int col){
//        final int ROW = level.length - 1;
//        final int COL = level[0].length - 1;
//        return (row >= 0) &&(row<ROW)&&(col>=0)&&(col<COL) && !visited[ROW][COL];
//    }

}
