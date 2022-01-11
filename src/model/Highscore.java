package model;

import java.sql.Date;
/**
 * save the new scores
 */
public class Highscore{

    public Integer score;
    public String username;
    public String date;

    public Highscore(int score, String username, String date) {
        this.score = score;
        this.username = username;
        this.date = date;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Highscore{" +
                "score=" + score +
                ", username='" + username + '\'' +
                ", date=" + date +
                '}';
    }
}
