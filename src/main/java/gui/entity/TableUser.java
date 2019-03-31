package gui.entity;

import javafx.beans.property.SimpleStringProperty;

import javafx.scene.layout.HBox;


public class TableUser {
    
    private final SimpleStringProperty rank;
    private final SimpleStringProperty email;
    private final HBox achievements;
    private final SimpleStringProperty score;
    
    /** constructor.
     *
     * @param rank the rank in the Leaderboard
     * @param email email of the user
     * @param achievements hbox with achievement images
     * @param score the total score of the user
     */
    public TableUser(int rank, String email, HBox achievements, int score) {
        this.rank = new SimpleStringProperty(Integer.toString(rank));
        this.email = new SimpleStringProperty(email);
        this.achievements = achievements;
        this.score = new SimpleStringProperty(Integer.toString(score));
    }
    
    public String getRank() {
        return rank.get();
    }
    
    public SimpleStringProperty rankProperty() {
        return rank;
    }
    
    public void setRank(String rank) {
        this.rank.set(rank);
    }
    
    public String getEmail() {
        return email.get();
    }
    
    public SimpleStringProperty emailProperty() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public HBox getAchievements() {
        return achievements;
    }
    
    public String getScore() {
        return score.get();
    }
    
    public SimpleStringProperty scoreProperty() {
        return score;
    }
    
    public void setScore(String score) {
        this.score.set(score);
    }
}
