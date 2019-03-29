package database;

import database.entities.ActivityType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Integer> {

    List<ActivityType> findAll();
}
