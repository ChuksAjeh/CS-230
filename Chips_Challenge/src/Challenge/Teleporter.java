package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Teleporter extends Impassable {

    private static final Image sprite;
    private Teleporter pair;

    static {
        sprite = new Image("");
    }

    public Teleporter() {
        super(sprite, false);
    }

    public Teleporter(Teleporter pair) {
        super(sprite, false);
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