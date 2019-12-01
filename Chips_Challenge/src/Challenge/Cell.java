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

    /**
     * Whether the cell is passable by movable entities.
     */
    private boolean passable;

    /**
     * Sprite used to render the object in game.
     */
    private final Image SPRITE;

    /**
     * Constructs a cell object.
     * @param sprite the sprite used to represent this Cell
     * @param passable whether or not this Cell is passable
     */
    Cell(Image sprite, boolean passable) {
        this.SPRITE = sprite;
        this.passable = passable;
    }

    /**
     * Changes a cells Passable state
     * @param newValue if the cell should be passable
     */
    public void setPassable(boolean newValue) {
        this.passable = newValue;
    }

    /**
     * Checks if the object is passable.
     * @return The object's passable state.
     */
    public boolean isPassable() {
        return this.passable;
    }

    /**
     * Gets the sprite the object is using.
     * @return The sprite being used to render the file.
     */
    public Image getSprite() {
        return SPRITE;
    }

}