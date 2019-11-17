package Challenge;
/**
 * @author ..
 * @version 1.0
 */
public class Teleporter extends Impassable {

    private Teleporter pair;

    public Teleporter() {
        super(CellType.TELEPORTER);
    }

    public Teleporter(Teleporter pair) {
        super(CellType.TELEPORTER);
        this.pair = pair;
    }

    public void setPair(Teleporter pair) {
        this.pair = pair;
        pair.setPair(this);
    }

    public void setPlayerLocation(int[] playerLocation) {

    }

}