package database.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;

import javax.persistence.Table;




@Entity // This tells Hibernate to make a table out of this class
@Table(name = "achievements")
public class Achievement {

    @Id
    @Getter
    @Setter
    private Integer id;

    @Column(name = "achievement_name", nullable = false)
    @Getter
    @Setter
    private String achievement_name;

    @Column(name = "achievement_value", nullable = false)
    @Getter
    @Setter
    private Integer achievement_value;
    
    @ManyToMany(mappedBy = "achievements",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JsonIgnore
    private Set<User> users;

    public Achievement() {
        users = new HashSet<>();
    }
    
    /** Constructor for Achievement. THIS SHOULD NOT BE USED,
     * IS ONLY USED ON START UP FOR GENERATION.
     *
     * @param id    the id of the achievement
     * @param achievement_name the name of the achievement
     * @param achievement_value the CO2value of the achievement
     */
    public Achievement(int id,String achievement_name, int achievement_value) {
        this.id = id;
        users = new HashSet<>();
        this.achievement_name = achievement_name;
        this.achievement_value = achievement_value;
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
    
    @Override
    public int hashCode() {
        return achievement_name.hashCode();
    }
}
