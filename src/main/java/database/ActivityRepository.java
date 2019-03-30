package database;

import database.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByUserId(Integer user_id);
    
    @Query("SELECT new database.entities.Activity(ach.id, ach.activity_type,ach.activity_amount,ach.date_time)"
            + "FROM User u JOIN u.activities ach Where u.id = :userId ORDER BY ach.id DESC")
    Set<Activity> findByUserIdSorted(@Param("userId") Integer user_id);

}