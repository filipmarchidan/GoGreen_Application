package API.entities;

import API.User;
import API.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;




public class UserTest {
    
    
    User user1 = new User("test@gmail.com", "test");
    
    User user2 = new User("test@gmail.com","test");
    
    User user3 = new User("testFalse@gmail.com", "testFalse");
    
    @Test
   public void equalsTestTrue(){
       
    
       assertTrue(user1.equals(user2));
   }
   
   @Test
    public void equalsTestFalse(){
        
        assertFalse(user1.equals(user3));
   }
    
    @Test
    void emailGetter() {
        
        assertEquals("test@gmail.com", user1.getEmail());
    }
    
    @Test
    void emailSetter() {
        user1.setEmail("testtest@gmail.com");
        assertEquals("testtest@gmail.com", user1.getEmail());
    }
    
    @Test
    void passwordgetter() {
        
        assertEquals("test", user1.getPassword());
    }
    
    @Test
    void passwordSetter() {
        
        user1.setPassword("password");
        assertEquals("password", user1.getPassword());
    }
    
    @Test
    void contructorTest(){
        
        assertEquals("test@gmail.comtest", user2.getEmail() + user2.getPassword());
    }
}
