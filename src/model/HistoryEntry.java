package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Client model of historyEntry
 */
public class HistoryEntry {

    private SimpleIntegerProperty level;
    private SimpleIntegerProperty score;
    private SimpleStringProperty date;
    private SimpleStringProperty playtime;

    public HistoryEntry(Integer level, Integer score, String date, String playtime) {
        this.level = new SimpleIntegerProperty(level);
        this.score = new SimpleIntegerProperty(score);
        this.date = new SimpleStringProperty(date);
        this.playtime = new SimpleStringProperty(playtime);
    }

    public HistoryEntry() {
    }


    public int getLevel() {
        return level.get();
    }

    public void setLevel(int level) {
        this.level = new SimpleIntegerProperty(level);
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(Integer score) {
        this.score = new SimpleIntegerProperty(score);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }
    
    /*
    public void setDate(LocalDate date) {
        this.date = new SimpleStringProperty(date.toString());
    }*/

    public String getPlaytime() {
        return playtime.get();
    }

    public void setPlaytime(String playtime) {
        this.playtime = new SimpleStringProperty(playtime);
    }

    @Override
    public String toString() {
        return "HistoryEntry [date=" + date + ", level=" + level + ", score=" + score + ", playtime=" + playtime + "]";
    }
}
