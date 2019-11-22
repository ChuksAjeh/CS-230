package Challenge;

import javafx.scene.image.Image;
import java.util.ArrayList;

/**
 * @author ..
 * @version 1.0
 */
public class LineEnemy extends Enemy {
    /**
     * The sprite to represent the dumb enemy.
     */
    private static final Image sprite;

    static {
        sprite = new Image("images/ENTITY_LINE_ENEMY.png");
    }

    public LineEnemy(int direction) {
        super(sprite, direction);
    }

    private int changeDirection() {

        //Check for direction, then check whether next position is a wall
        //If it is a wall, change direction, else keep same direction.
        //This should be called every update.

        ArrayList<String> surroundingCells = getSurroundingCells();

        if (0 == this.getDirection()) {
            // UP
            if ("Wall".equals(surroundingCells.get(0))) {
                return 2;
            } else {
                return 0;
            }
        } else if (1 == this.getDirection()) {
            // RIGHT
            if ("Wall".equals(surroundingCells.get(1))) {
                return 3;
            } else {
                return 1;
            }
        } else if (2 == this.getDirection()) {
            // DOWN
            if ("Wall".equals(surroundingCells.get(2))) {
                return 0;
            } else {
                return 2;
            }
        } else if (3 == this.getDirection()) {
            // LEFT
            if ("Wall".equals(surroundingCells.get(3))) {
                return 1;
            } else {
                return 3;
            }
        }

        return 0;
    }

    private ArrayList<String> getSurroundingCells(){
        ArrayList<String> surroundingCellsArray = new ArrayList<>();
        surroundingCellsArray.add(0, this.getCellGrid()[this.getEnemyX()][this.getEnemyY() -1].getClass().getSimpleName());
        surroundingCellsArray.add(1, this.getCellGrid()[this.getEnemyX() + 1][this.getEnemyY()].getClass().getSimpleName());
        surroundingCellsArray.add(2, this.getCellGrid()[this.getEnemyX()][this.getEnemyY() + 1].getClass().getSimpleName());
        surroundingCellsArray.add(3, this.getCellGrid()[this.getEnemyX() - 1][this.getEnemyY()].getClass().getSimpleName());
        return surroundingCellsArray;
    }

}