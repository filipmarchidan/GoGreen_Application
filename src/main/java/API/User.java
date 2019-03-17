package API;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name =  "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)


    private int id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;


    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public void update(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
