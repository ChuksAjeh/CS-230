package Challenge;

import java.io.File;

class User {

    private String userName;
    private int[] scores;
    //private File[] saveFiles;

    User(String userName) {
        this.userName = userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    void setScores(int[] scores) {
        this.scores = scores;
    }

    String getUserName() {
        return this.userName;
    }

    int[] getScores() {
        return this.scores;
    }

}
