package database.entities;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity // This tells Hibernate to make a table out of this class
@Table(name = "achievements")
public class Achievement {

    @Id
    private Integer id;

    @Column(name = "achievement_name", nullable = false)
    private String achievement_name;

    @Column(name = "achievement_value", nullable = false)
    private Integer achievement_value;
    
    @ManyToMany(mappedBy = "achievements",cascade = CascadeType.PERSIST)
    private Set<User> users;

    public Achievement() {
        users = new HashSet<>();
    }
    
    /** Constructor for Achievement. THIS SHOULD NOT BE USED,
     * IS ONLY USED ON START UP FOR GENERATION.
     *
     * @param achievement_name the name of the achievement
     * @param achievement_value the CO2value of the achievement
     */
    public Achievement(String achievement_name, int achievement_value) {
        
        users = new HashSet<>();
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
    
    public Set<User> getUsers() {
        return users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Achievement) {
            Achievement ach = (Achievement)obj;
            return ach.id == this.id;
        }
        return false;
    }
}
