package Challenge;

import javafx.scene.image.Image;
import java.util.Random;

/**
 * @author George Carpenter, Ioan Mazurca, Angelo Balistoy
 * @version 1.0
 */
class WallEnemy extends Enemy {

    private static final Image SPRITE;
    private final Random random;

    static {
        SPRITE = new Image("images/ENTITY_WALL_ENEMY.png");
    }

    WallEnemy(Position position, int direction) {
        super(SPRITE, position, direction);
        random = new Random();
    }

    public int nextDirection() {

        boolean[] passable = getCells();

        int numberOfMoves = countMoves(passable);

        if (0 == numberOfMoves) {
            // Cannot move .. something happens I guess, probably return 42 and then handle it later
            return 42;
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

    private int countMoves(boolean[] moves) {

        int count = 0;

        for (boolean b : moves) {
            if (b) {
                count += 1;
            }
        }

        return count;
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