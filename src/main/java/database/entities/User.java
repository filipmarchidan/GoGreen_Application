package database.entities;

import org.hibernate.annotations.ManyToAny;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column (name = "totalscore")
    private int totalscore;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Achievement> achievements = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends = new HashSet<>();
    
    public User() {
    
    }
    
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.totalscore = 0;
    }

    public User(String email, int id,int totalscore) {
        this.totalscore = totalscore;
        this.email = email;
        this.id = id;
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
