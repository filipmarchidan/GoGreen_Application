package API;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class UserServiceTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    private  UserService userService;

    private static final String email1 = "Spongebob";
    private static final String pass1 = "12345";

    @Test
    public void checkFindByEmail()
    {
        User newUser = getOneUser();
        User saveUser = entityManager.persist(newUser);
        List<User> users = userRepository.findByEmail(email1);
        assertEquals(saveUser.getEmail(), users.get(0).getEmail());
    }
    private User getOneUser(){
        User spongeBob = new User();
        spongeBob.setEmail(email1);
        spongeBob.setPassword(pass1);
        return spongeBob;
    }
}
