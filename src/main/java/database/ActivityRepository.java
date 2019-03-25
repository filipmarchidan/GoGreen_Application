package database;

import database.entities.Activity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActivityRepository extends CrudRepository<Activity, Integer> {

    List<Activity> findByUserId(Integer user_id);

}