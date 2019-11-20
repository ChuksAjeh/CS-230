package Challenge;

public class User {

    private String userName;
    private int[] scores;

    public User() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public String getUserName() {
        return this.userName;
    }

    public int[] getScores() {
        return this.scores;
    }

}
