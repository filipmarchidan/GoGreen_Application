package database;

import database.entities.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Integer> {

    @Query("SELECT actType.co2_savings FROM ActivityType as actType ORDER BY actType.id ASC ")
    List findAllCo2SavingsSorted();


}
