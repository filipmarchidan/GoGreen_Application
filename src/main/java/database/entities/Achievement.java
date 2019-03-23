package database.entities;



import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "achievements")
public class Achievement {

    @Id
    private Integer id;

    @Column(name = "achievement_name", nullable = false)
    private String achievement_name;

    @Column(name = "achievement_value", nullable = false)
    private Integer achievement_value;
    
    @ManyToMany(mappedBy = "achievements",cascade = CascadeType.ALL)
    Set<User> users;

    public Achievement(){

    }
    
    public Achievement (String achievement_name, int achievement_value){
        this.achievement_name = achievement_name;
        this.achievement_value = achievement_value;
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
    
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if (obj instanceof Achievement){
            Achievement ach = (Achievement)obj;
            return ach.id == this.id;
        }
        return false;
    }
}
