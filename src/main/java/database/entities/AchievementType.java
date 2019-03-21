package database.entities;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "achievement_types")
public class AchievementType {

    @Id
    private Integer id;

    @Column(name = "achievement_name", nullable = false)
    private String achievement_name;

    @Column(name = "achievement_value", nullable = false)
    private Integer achievement_value;

    public AchievementType(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAchievement_name() {
        return achievement_name;
    }

    public void setAchievement_name(String achievement_name) {
        this.achievement_name = achievement_name;
    }

    public Integer getAchievement_value() {
        return achievement_value;
    }

    public void setAchievement_value(Integer achievement_value) {
        this.achievement_value = achievement_value;
    }
}
