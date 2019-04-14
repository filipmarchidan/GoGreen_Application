package database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


import javax.persistence.Table;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Users")
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;
    
    @Column(name = "email", nullable = false, unique = true)
    @Getter
    @Setter
    private String email;
    
    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private String password;

    @Column (name = "totalscore")
    @Getter
    @Setter
    private int totalscore;

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<Achievement> achievements = new HashSet<>();

    @Column (name = "solarPanel")
    @Getter
    @Setter
    private boolean solarPanel;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Getter
    @Setter
    @JsonIgnore
    private Set<User> friends = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    @JsonIgnore
    private Set<Activity> activities;
    
    /**
     * creates a new user.
     * @param email email
     * @param password password
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.totalscore = 0;
        this.solarPanel = false;
    }
    
    /**
     * creates a new user.
     * @param email email
     * @param id id
     * @param totalscore totalscore
     */
    public User(String email, int id,int totalscore) {
        this.totalscore = totalscore;
        this.email = email;
        this.id = id;
        this.solarPanel = false;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return Objects.equals(email, user.email)
                && totalscore == user.totalscore;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
}
