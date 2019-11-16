package Challenge;
/**
 * @author ..
 * @version 1.0
 */
abstract class Entity {

    private Type type;

    public enum Type {
        PLAYER, ENEMY, ITEM
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}