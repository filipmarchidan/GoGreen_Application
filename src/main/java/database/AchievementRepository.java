package database;

import database.entities.Achievement;
import database.entities.AchievementType;
import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

}
