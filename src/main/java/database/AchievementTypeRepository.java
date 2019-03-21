package database;

import database.entities.AchievementType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AchievementTypeRepository extends CrudRepository<AchievementType, Integer> {

    //List<AchievementType> findById(Integer id);

}
