package Challenge;

import javafx.scene.image.Image;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author George Carpenter, Ioan Mazurca, Angelo Balistoy
 * @version 1.0
 */
public class WallEnemy extends Enemy {

    private static final Image SPRITE;
    private final Random random;

    static {
        SPRITE = new Image("images/ENTITY_WALL_ENEMY.png");
    }

    public WallEnemy(int direction) {
        super(SPRITE, direction);
        random = new Random();
    }

    private int nextDirection() {

        Cell[] sc = getSurroundingCells();
        Entity[] se = getSurroundingEntitys();

        boolean[] passable = new boolean[4];
        List list = Collections.singletonList(passable);
        int numberOfMoves = Collections.frequency(list, true);

        for (int i = 0; i < passable.length; i++) {
            passable[i] = sc[i] instanceof Ground && se[i] == null;
        }

        if (0 == numberOfMoves) {
            // Cannot move .. something happens I guess, probably return 5 and then handle it later
        } else if (1 == numberOfMoves) {
            // Only 1 available space
            return findMove(passable, true);
        } else if (2 == numberOfMoves) {
            // 2 spaces
            int[] moves = findMoves(passable);
            return random.nextBoolean() ? moves[0] : moves[1];
        } else if (3 == numberOfMoves) {
            // 3 spaces
            int wall = findMove(passable, false);
            return random.nextBoolean() ? (wall + 1) % 4 : (wall + 3) % 4;
        }

        // If none of above, 4 available spaces, return random
        return random.nextInt(4);
    }

    private int[] findMoves(boolean[] passable) {

        int[] moves = new int[2];

        moves[0] = findMove(passable, true);
        passable[moves[0]] = false;
        moves[1] = findMove(passable, true);

        return moves;

    }

    private int findMove(boolean[] passable, boolean val) {

        for (int i = 0 ; i < passable.length ; i++ ) {

            if (val == passable[i]) {
                return i;
            }

        }

        return 0;

    }

}