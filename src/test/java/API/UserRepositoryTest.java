package API;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private UserRepository userRepository;

        @Test
        public void checkDatabaseFunctionality(){
           User user = getOneUser();
           User saveData = entityManager.persist(user);
           User getData = userRepository.findById(saveData.getId()).get();
           assertThat(getData).isEqualTo(saveData);
        }

        private User getOneUser(){
            User spongeBob = new User();
            spongeBob.setEmail("Spongebob");
            spongeBob.setPassword("12345");
            return spongeBob;
        }
}
