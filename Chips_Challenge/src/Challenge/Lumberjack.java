package Challenge;

public class Lumberjack {

    public Lumberjack() {
        // They're a lumberjack and they're OK
        // They sleeps all night and they works all day
        // https://www.youtube.com/watch?v=FshU58nI0Ts
    }

    public void log(String message) {

        System.out.println("Spam : " + message);

    }

    public void log(int priority, String message) {

        if (priority == 1) {
            System.out.println("Useful : " + message);
        } else {
            this.log(message);
        }

    }

    public void logCellGrid(Cell[][] cellGrid) {

        System.out.println("===== START CELL GRID =====");

        for (Cell[] row : cellGrid) {
            for (Cell c : row) {

                if (null == c) {
                    log("NULL");
                } else {
                    log(c.getClass().getSimpleName());
                }
            }
        }

    }

    public void logEntityGrid(Entity[][] entityGrid) {

        System.out.println("===== START ENTITY GRID =====");

        for (Entity[] row : entityGrid) {
            for (Entity e : row) {

                if (null == e) {
                    log("NULL");
                } else {
                    log(e.getClass().getSimpleName());
                }
            }
        }

    }

    public void logPlayerLoc(Player player, Entity[][] entityGrid) {

        int[] playerLoc = player.getLocation(entityGrid);
        int direction = player.getDirection();

        log(1, "Player Loc + Dir -> " + playerLoc[0] + " " + playerLoc[1] + " " + direction);

    }

}
