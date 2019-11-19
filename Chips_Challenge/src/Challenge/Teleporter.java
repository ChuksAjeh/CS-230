package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Teleporter extends Impassable {

    private static final CellType cellType;
    private static final Image sprite;
    private Teleporter pair;

    static {
        cellType = CellType.TELEPORTER;
        sprite = new Image("");
    }

    public Teleporter() {
        super(cellType, sprite, false);
    }

    public Teleporter(Teleporter pair) {
        super(cellType, sprite, false);
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