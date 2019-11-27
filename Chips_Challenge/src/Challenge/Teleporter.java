package Challenge;

import javafx.scene.image.Image;

/**
 * @author George Carpenter
 * @version 1.0
 */
public class Teleporter extends Impassable {

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
    public Teleporter() {
        super(SPRITE, false);
    }

    /**
     * Secondary constructor
     * @param pair the pair Teleporter for this Object
     */
    public Teleporter(Teleporter pair) {
        super(SPRITE, false);
        this.pair = pair;
        pair.setPair(this);
    }

    /**
     * Adds a pair to a Teleporter Object
     * @param pair the pair Object
     */
    private void setPair(Teleporter pair) {
        this.pair = pair;
    }

    /**
     * Returns the pair Object
     * @return the pair Teleporter
     */
    public Teleporter getPair(){
        return pair;
    }

}