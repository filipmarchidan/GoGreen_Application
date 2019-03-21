package database;

import database.entities.Achievement;
import database.entities.AchievementType;
import database.entities.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

    List<Achievement> findByUserId(Integer user_id);

}
