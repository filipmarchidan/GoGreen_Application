package database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @JsonIgnore
    private int id;
    
    @Column(name = "email", nullable = false, unique = true)
    @Getter
    @Setter
    @JsonIgnore
    private String email;
    
    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private String password;
    
    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    @JsonIgnore
    private Set<Activity> activities;
    
    
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
                && Objects.equals(password, user.password);
    }
    
}
