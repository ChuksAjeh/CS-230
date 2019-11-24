package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Teleporter extends Impassable {

    private static final Image SPRITE;
    private Teleporter pair;

    static {
        SPRITE = new Image("");
    }

    public Teleporter() {
        super(SPRITE, false);
    }

    public Teleporter(Teleporter pair) {
        super(SPRITE, false);
        this.pair = pair;
        pair.setPair(this);
    }

    private void setPair(Teleporter pair) {
        this.pair = pair;
    }

    private Teleporter getPair(){
        return pair;
    }

    public int[] setPlayerLocation(Cell[][] cellGrid) {

        for (int x = 0 ; x < cellGrid.length ; x++ ) {
            for (int y = 0 ; y < cellGrid[x].length ; y++ ) {

                Cell cell = cellGrid[x][y];

                if (cell instanceof Teleporter && cell==this.getPair()) {
                    // Pair is found
                    return new int[] {x, y};
                }

            }
        }

        return new int[] {0, 0};
    }
}