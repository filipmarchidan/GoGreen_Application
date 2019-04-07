package API;


import database.UserRepository;
import database.UserServiceImpl;
import database.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
//        User testUser = userRepository.save(user2);
//        Assert.assertFalse(expectedUser.equals(testUser));
        userService.updateUser(expectedUser,"Spongebob1","123456");
        //Assert.assertEquals(expectedUser, resultUser);
        Assert.assertEquals("Spongebob1", expectedUser.getEmail());
        Assert.assertEquals("123456", expectedUser.getPassword());
    }
    @Test
    public void checkFindAll()
    {
        List<User> expected = userRepository.findAll();
        List<User> result = userService.getAllUsers();
        Assert.assertEquals(expected.size(),result.size());
    }
    
    @Test
    public void checkDelete()
    {
        User user = new User("360noScope","420blazeIt");
        userRepository.save(user);
        List<User> before = userRepository.findAll();
        userService.deleteUser(user.getId());
        List<User> after = userRepository.findAll();
        Assert.assertTrue("The numbers of users is bigger before", before.size() > after.size());
    }
    @Test
    public void checkFindById()
    {
        userRepository.save(user1);
        int expectedId = user1.getId();
        Assert.assertEquals(expectedId, userService.getUserById(user1.getId()).getId());
    }
    @After
    public void delete()
    {
        userRepository.delete(user1);
        userRepository.delete(user2);
    }
    
}
