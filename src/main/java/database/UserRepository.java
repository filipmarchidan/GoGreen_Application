package database;

import database.entities.Achievement;
import database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;


import java.util.List;
import java.util.Set;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();
    User findByEmail(String email);

    @Query("SELECT new database.entities.User(fr.email, fr.id,fr.totalscore)"
            + "FROM User u JOIN u.friends fr Where u.id = :userId "
            + "ORDER BY fr.totalscore DESC")
    Set<User> getFriendsfromUser(@Param("userId") int userId);
    
    
    @Query("SELECT new database.entities.User(u.email, u.id,u.totalscore)"
            + "From User u")
    Set<User> findUsersSimple();
    

}