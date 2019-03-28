package database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ManyToAny;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Users")
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private int totalscore;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Achievement> achievements = new HashSet<>();

    @Column (name = "solarPanel")
    private boolean solarPanel;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> friends = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.totalscore = 0;
        this.solarPanel = false;
    }

    public User(String email, int id,int totalscore) {
        this.totalscore = totalscore;
        this.email = email;
        this.id = id;
        this.solarPanel = false;
    }

    public int getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    public int getId() {
        return id;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Set<Achievement> achievements) {
        this.achievements = achievements;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    @JsonIgnore
    private Set<Activity> activities;

    public boolean isSolarPanel() {
        return solarPanel;
    }

    public void setSolarPanel(boolean solarPanel) {
        this.solarPanel = solarPanel;
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
        return Objects.equals(id, user.id)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && totalscore == user.totalscore;
    }
    
}
