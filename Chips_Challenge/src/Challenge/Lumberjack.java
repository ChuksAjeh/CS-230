package Challenge;

/**
 * This class is used entirely for testing, although we may also submit it
 * @author George Carpenter
 * @version 1.0
 */
class Lumberjack {

    /**
     * Lumberjacks usually remove trees not construct them
     */
    Lumberjack() {
        // They're a lumberjack and they're OK
        // They sleeps all night and they works all day
        // https://www.youtube.com/watch?v=FshU58nI0Ts
    }

    /**
     * Logs stuff
     * @param message what to log
     */
    void log(String message) {

        System.out.println("Spam : " + message);

    }

    /**
     * Logs more stuff
     * @param priority for not spam
     * @param message what to log
     */
    void log(int priority, String message) {

        if (priority == 1) {
            System.out.println("Useful : " + message);
        } else {
            this.log(message);
        }

    }

    /**
     * Logs a grid
     * @param grid the grid to log
     * @param <T> Cell or Entity
     */
    public <T> void logGrid(T[][] grid) {

        System.out.println("===== START GRID =====");

        for (T[] row : grid) {
            for (T e : row) {

                if (null == e) {
                    log("NULL");
                } else {
                    log(e.getClass().getSimpleName());
                }
            }
        }

    }

    /**
     * Logs the Player location
     * @param player the Player object to track
     * @param entityGrid the grid to find them in
     */
    public void logPlayerLoc(Player player, Entity[][] entityGrid) {

        int[] playerLoc = player.getLocation(entityGrid);
        int direction = player.getDirection();

        log(1, "Player Loc + Dir -> " + playerLoc[0] + " " + playerLoc[1] + " " + direction);

    }

}
