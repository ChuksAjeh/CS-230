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
    }

    public void setPair(Teleporter pair) {
        this.pair = pair;
        pair.setPair(this);
    }

    public void setPlayerLocation(int[] playerLocation) {
        // This may not be void
    }

}