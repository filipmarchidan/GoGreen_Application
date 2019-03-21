package database;

import database.entities.AchievementType;
import org.springframework.data.repository.CrudRepository;

public interface AchievementTypeRepository extends CrudRepository<AchievementType, Integer> {

}
