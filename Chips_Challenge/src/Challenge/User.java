package Challenge;

/**
 * This class is used to hold data on users in the game and their associated data
 * @author Samuel Roach, Ioan Mazurca, Blake Davies
 * @version 1.0
 */
class User {

    /**
     * Their name
     */
    private String userName;

    // private ArrayList<Integer> scores; // (or a Score class with Level name and time taken)
    // private File[] saveFiles;

    /**
     * Constructs a User object
     * @param userName their name
     */
    User(String userName) {
        this.userName = userName;
    }

    /**
     * gets the users name, for saving purposes
     * @return the Users name
     */
    String getUserName() {
        return this.userName;
    }

    /*

    Put stuff here pls, to make cleaning faster

     */

}
