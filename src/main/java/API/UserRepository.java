package API;

import org.springframework.data.repository.CrudRepository;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {


   // @Query("SELECT p FROM user WHERE LOWER(p.email)")
    User findByEmail(String email);

    
}