package API;


import javax.persistence.*;
import java.util.Objects;


@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private Integer id;
	
	private String email;

	 private String password;
	 
	 public User() {
	 
	 }
	 
	 public User(String email, String password) {
	 	this.email = email;
	 	this.password = password;
	 }

	 /*public Integer getId() {
		  return id;
	 }

	 public void setId(Integer id) {
		  this.id = id;
	 }*/



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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
			Objects.equals(email, user.email) &&
			Objects.equals(password, user.password);
	}
	
}
