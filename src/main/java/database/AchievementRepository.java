package database;

import database.entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    
    //List<Achievement> findById(Integer id);
    @Query("SELECT new database.entities.Achievement(ach.id, ach.achievement_name,"
        + " ach.achievement_value)"
            + "FROM User u JOIN u.achievements ach Where u.id = :userId")
    Set<Achievement> getAchievementsFromUserId(@Param("userId") int userId);

}
