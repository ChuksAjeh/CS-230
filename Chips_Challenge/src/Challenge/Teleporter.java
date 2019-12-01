package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
class Teleporter extends Cell {

    /**
     * The sprite used for this class
     */
    private static final Image SPRITE;

    /**
     * Pair Teleporter attribute
     */
    private Teleporter pair;

    static {
        SPRITE = new Image("images/CELL_TELEPORTER.png");
    }

    /**
     * Constructor
     */
    Teleporter() {
        super(SPRITE, false);
    }

    /**
     * Secondary constructor
     * @param pair the pair Teleporter for this Teleporter Object
     */
    Teleporter(Teleporter pair) {
        super(SPRITE, false);
        this.pair = pair;
        pair.setPair(this);
    }

    /**
     * Adds a pair to a Teleporter Object
     * @param pair the pair Teleporter Object
     */
    private void setPair(Teleporter pair) {
        this.pair = pair;
    }

    /**
     * Returns the pair Object
     * @return the pair Teleporter
     */
    Teleporter getPair(){
        return pair;
    }

}