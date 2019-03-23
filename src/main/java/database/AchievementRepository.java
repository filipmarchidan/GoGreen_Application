package database;

import database.entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    
    //List<Achievement> findById(Integer id);

}
