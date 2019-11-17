package Challenge;

import javafx.scene.image.Image;

/**
 * @author ..
 * @version 1.0
 */
public class Teleporter extends Impassable {

    private Teleporter pair;

    public Teleporter() {
        super(CellType.TELEPORTER, new Image(""));
    }

    public Teleporter(Teleporter pair) {
        super(CellType.TELEPORTER, new Image(""));
        this.pair = pair;
    }

    public void setPair(Teleporter pair) {
        this.pair = pair;
        pair.setPair(this);
    }

    public void setPlayerLocation(int[] playerLocation) {

    }

}