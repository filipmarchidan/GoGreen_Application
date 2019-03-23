package database;

import database.entities.Achievement;
import database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Integer> {

    
    List<User> findByEmail(String email);
    
    @Query("SELECT new database.entities.Achievement(ach.achievement_name, ach.achievement_value) FROM User u JOIN u.achievements ach Where u.id = :userId")
    Set<Achievement> getAchievementsFromUserId(@Param("userId") int userId);
    
}