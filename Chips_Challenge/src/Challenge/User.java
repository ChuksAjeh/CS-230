package Challenge;

class User {

    private String userName;
    private int[] scores;

    User() {

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
