package database.entities;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class UserTest {
    
    
    User user1 = new User();
    
    User user2 = new User();
    
    User user3 = new User();
    User user4 = new User();
    User user5 = new User();
    
    
    @Before
    public void setup() {
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setTotalscore(10);
        
        user2.setEmail("email");
        user2.setPassword("password");
        user2.setTotalscore(10);
        
        user3.setEmail("bla");
        user3.setPassword("password");
        user3.setTotalscore(10);
    
        user4.setEmail("email");
        user4.setPassword("bla");
        user4.setTotalscore(10);
    
        user5.setEmail("email");
        user5.setPassword("password");
        user5.setTotalscore(15);
    }
    
    @Test
   public void equalsTestTrue(){
    
        Assert.assertTrue(user1.equals(user2));
   }
   
   @Test
   public void equalsTestSameObject(){
       Assert.assertTrue(user1.equals(user1));
        
   }
   
    
    @Test
    public void equalsTestFalseForEmail(){
        
        Assert.assertFalse(user1.equals(user3));
    }
    
    @Test
    public void equalsTestFalseForScore(){
        
        Assert.assertFalse(user1.equals(user5));
    }
    
    
    
    @Test
   public void equalsTestNullObject(){
    
       Assert.assertFalse(user1.equals(null));
   }
    
   
    @Test
    public void user_does_not_equal_string() {
        String string = "Not a User";
        Assert.assertFalse(user1.equals(string));
    }
    
    
}
