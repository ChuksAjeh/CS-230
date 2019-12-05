package Challenge;

import java.util.ArrayList;

/**
 * Class used to represent a User profile
 * @author Samuel Roach, Blake Davies
 * @version 1.0
 */

class User {

    /**
     * The username for the profile
     */
    private String userName;

    /**
     * The list of the players scores for each level
     */
    private static ArrayList<Integer> scores = new ArrayList<>();

    /**
     * The class to create log files for debugging
     */
    private Lumberjack jack = new Lumberjack();

    /**
     * Constructor for User class
     * @param userName The username of the created User
     */
    User(String userName) {
        this.userName = userName;
    }

    /**
     * Setter for the User profiles name
     * @param userName the user name to change to
     */
    void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * Setter to set the level scores for the User
     * @param scores
     */
    void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    /**
     * Getter to retreive the Username of this class
     * @return The username
     */
    String getUserName() {
        return this.userName;
    }

    /**
     * Getter to retrieve the scores for the user
     * @return The array list of scores
     */
    ArrayList<Integer> getScores() {
        return this.scores;
    }

    /**
     * Method to add a score for a level to a User
     * @param level The level for which the score is for
     * @param score The score which was acheived on the level
     */
    void addScore(Level level, int score){
        int levelNo = Integer.parseInt(level.getLevelName().substring(6));
        jack.log(2,"USER " + Integer.toString(score));
        if ((this.getScores().get(levelNo-1) > score) || (0 == this.getScores().get(levelNo-1))) {
            this.scores.set((levelNo - 1), score);
        }
    }

}
