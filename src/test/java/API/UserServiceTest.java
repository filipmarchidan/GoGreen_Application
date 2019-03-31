package API;


import database.UserRepository;
import database.UserServiceImpl;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

     static final String email1 = "Spongebob";
     static final String pass1 = "12345";
     User user1 = new User();
     User user2 = new User();

    @Before
    public void setup(){
        user1.setEmail(email1);
        user1.setPassword(pass1);
        user2.setEmail("Trump@Donald.Orange");
        user2.setPassword("iLoveOil");
    }

    @Test
    public void checkUpdateUser()
    {
        User expectedUser = userRepository.save(user1);
        User testUser = userRepository.save(user2);
        Assert.assertFalse(expectedUser.equals(testUser));
        User resultUser = userService.updateUser(testUser,"Spongebob","12345");
        Assert.assertEquals(expectedUser, resultUser);
    }
//    @Test
//    public void checkDeleteUser()
//    {
//        User expectedUser = userRepository.save(user1);
//        userService.deleteUser(expectedUser.getId());
//        Assert.assertEquals(null, userRepository.findByEmail(expectedUser.getEmail()));
//    }
}
