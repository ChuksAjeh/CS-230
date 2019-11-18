package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class LineEnemy extends Enemy {

    public LineEnemy(int direction) {
        super(EntityType.LINE_ENEMY, new Image("images/ENTITY_ENEMY.png"), direction);
    }

    private int changeDirection() throws Exception {

        //Check for direction, then check whether next position is a wall
        //If it is a wall, change direction, else keep same direction.
        //This should be called every update.
        if (0 == this.getDirection()) {
            // UP
            if (Cell.CellType.WALL == this.getCellGrid()[this.getEnemyX()][this.getEnemyY()+1].getCellType()) {
                return 2;
            } else {
                return 0;
            }
        } else if (1 == this.getDirection()) {
            // RIGHT
            if (Cell.CellType.WALL == this.getCellGrid()[this.getEnemyX()+1][this.getEnemyY()].getCellType()) {
                return 3;
            } else {
                return 1;
            }
        } else if (2 == this.getDirection()) {
            // DOWN
            if (Cell.CellType.WALL == this.getCellGrid()[this.getEnemyX()][this.getEnemyY()-1].getCellType()) {
                return 0;
            } else {
                return 2;
            }
        } else if (3 == this.getDirection()) {
            // LEFT
            if (Cell.CellType.WALL == this.getCellGrid()[this.getEnemyX()-1][this.getEnemyY()].getCellType()) {
                return 1;
            } else {
                return 3;
            }
        } else {
            //throw exception (custom one like OutOfDirectionRange?
            throw new Exception("Direction out of range!");
        }
    }

}