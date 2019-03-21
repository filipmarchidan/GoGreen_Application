package database;

import database.entities.Activity;
import database.entities.ActivityType;
import org.springframework.data.repository.CrudRepository;

public interface ActivityTypeRepository extends CrudRepository<ActivityType, Integer> {
}
