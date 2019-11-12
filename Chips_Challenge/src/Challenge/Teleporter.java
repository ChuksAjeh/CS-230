package Challenge;

public class Teleporter extends Impassable {

    private Teleporter pair;

    public Teleporter() {

    }

    public void setPair(Teleporter pair) {
        this.pair = pair;
        pair.setPair(this);
    }

    public void setPlayerLocation(int[] playerLocation) {

    }

}