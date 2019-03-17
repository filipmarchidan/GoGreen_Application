package API.entities;

import API.User;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;


public class UserTest {
    
    
    User user1 = new User("test@gmail.com", "test");
    
    User user2 = new User("test@gmail.com","test");
    
    User user3 = new User("testFalse@gmail.com", "testFalse");
    
    User userNull = null;
    
    @Test
   public void equalsTestTrue(){
       
    
       assertTrue(user1.equals(user2));
   }
   
   @Test
   void equalsTestSameObject(){
        assertTrue(user1.equals(user1));
        
   }
   
   @Test
    public void equalsTestFalse(){
        
        assertFalse(user1.equals(user3));
   }
   
   @Test
   void equalsTestNullObject(){
        
        assertFalse(user1.equals(userNull));
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
    
    @Test
    void user_equals_itself() {
        assertEquals(user1,user1);
    }
    
    @Test
    void user_does_not_equal_string() {
        String string = "Not a User";
        assertFalse(user1.equals(string));
    }
}
