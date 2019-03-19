package API;

import database.UserRepository;
import database.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
        private static final String email1 = "Spongebob";
        private static final String pass1 = "12345";
        private static final String email2 = "Patrick";
        private static final String pass2 = "1234";
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

        @Test
        public void checkFindByEmailFunctionality()
        {
            User user = getOneUser();
            User saveData = entityManager.persist(user);
            User getUser = userRepository.findByEmail(email1);
            assertEquals(saveData.getEmail(), getUser.getEmail());
        }
        private User getOneUser(){
            User spongeBob = new User();
            spongeBob.setEmail(email1);
            spongeBob.setPassword(pass1);
            return spongeBob;
        }
}
