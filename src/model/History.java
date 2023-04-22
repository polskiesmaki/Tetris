package model;

public class History {

    private int score;
    private String date;

    public History(int score, String date) {
        this.score = score;
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }
}
