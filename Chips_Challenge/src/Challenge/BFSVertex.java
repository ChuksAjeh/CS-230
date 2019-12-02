package Challenge;

/**
 * A BFS Vertex contains the x coordinate, the y coordinate, and distance
 * from the source node. This class is used in the Smart Enemy class which
 * requires these fields to implement the wavefront planning algorithm.
 * This is just a BFS search and from that, find the shortest path back to
 * the starting node from the goal node.
 * @author Chuks
 * @version 1.0
 */
class BFSVertex {

    /**
     * The x coordinate.
     */
    private int x;

    /**
     * The Y coordinate.
     */
    private int y;

    /**
     * The distance from the start node.
     */
    private int dist;

    /**
     * Constructs a BFSVertex node.
     * @param x The node's x coordinate
     * @param y The node's y coordinate
     * @param dist  The node's distance from the source node.
     */
    BFSVertex(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }

    /**
     * Gets the x coordinate.
     * @return The x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate.
     * @return The Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the distance from the source node.
     * @return The distance from the source node.
     */
    int getDist() {
        return dist;
    }

    /**
     * Sets the x coordinate.
     * @param x The x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the Y coordinate
     * @param y The Y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

}
