package Challenge;

import javafx.scene.image.Image;

import java.util.ArrayList;

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

    /*
    private int nextDirection(Level level, Player player){

        //get the width and height of the grid:
        int width = level.getEntityGrid().length-1;
        int height = level.getEntityGrid()[0].length-1;

        //pull in current grid data: Cell & Entity:
        Cell [][] cellGrid = level.getCellGrid();
        Entity[][] entityGrid = level.getEntityGrid();

        //source node for the enemy/
        int srcX = getEnemyX();
        int srcY = getEnemyY();
        //find the player location.

        player.getLocation(entityGrid);
        // walls, doors, hazards are all impassable for enemy
        int[][] dist = new int[height][width];
        for(int i =0; i<=height; i++){
            for(int j=0; j<=width; j++){
                //provides use with a set of distances
                if(cellGrid[i][j].getClass().getSimpleName().equals("Wall")||cellGrid[i][j].getClass().getSimpleName().equals("Fire")){
                    dist[i][j] = 0;
                }
                //covers door and water obstacle
                else if(cellGrid[i][j].getClass().getSimpleName().equals("TokenDoor")||cellGrid[i][j].getClass().getSimpleName().equals("Water")){
                    dist[i][j] = 0;
                }else{

                }
            }
        }
        return 0;
    }
    */


    /*
    private void dijkstra (Level level, Player player){

        //get the width and height of the grid:
        int width = level.getEntityGrid().length-1;
        int height = level.getEntityGrid()[0].length-1;


        //pull in current grid data: Cell & Entity:
        Cell [][] cellGrid = level.getCellGrid();
        Entity[][] entityGrid = level.getEntityGrid();


        //source node for enemy
        int srcX = getEnemyX();
        int srcY = getEnemyY();
        //find the player location.


        //player.getLocation(entityGrid);
        // walls, doors, hazards are all impassable for enemy



    }
    */

    /*
    // get the neighbours of the cell:
    private String getSurroundingCells(Cell[][] cellGrid, int i, int j){
        String up = cellGrid[this.getEnemyX()][this.getEnemyY() -1].getClass().getSimpleName();
        String right = cellGrid[this.getEnemyX() + 1][this.getEnemyY()].getClass().getSimpleName();
        String down = cellGrid[this.getEnemyX()][this.getEnemyY() + 1].getClass().getSimpleName();
        String left = cellGrid[this.getEnemyX() - 1][this.getEnemyY()].getClass().getSimpleName();
        return null;

    }
     */





}