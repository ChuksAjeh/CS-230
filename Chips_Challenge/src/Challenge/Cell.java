package Challenge;

import javafx.scene.image.Image;

/**
 * This is a cell class representing an abstract cell in the game. Not meant to be instantiated, it is used to allow all
 * cells to be able to set and get its type and also indicate whether it is a passable cell or not.
 * @author ..
 * @version 1.0
 *
 */
abstract class Cell {

    private CellType cellType;
    private boolean passable;
    private Image sprite;

    public enum CellType {
        GROUND, WALL, GOAL, KEY_DOOR, TOKEN_DOOR, TELEPORTER, FIRE, WATER
    }

    public Cell(CellType cellType, Image sprite, boolean passable) {
        this.cellType = cellType;
        this.sprite = sprite;
        this.passable = passable;
    }

    public CellType getCellType() {
        return this.cellType;
    }

    public void setPassable() {

    }

    public boolean isPassable() {
        return this.passable;
    }

    public Image getSprite() {
        return sprite;
    }
}