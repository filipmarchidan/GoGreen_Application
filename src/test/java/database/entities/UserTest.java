package database.entities;


import org.junit.Assert;
import org.junit.Test;


public class UserTest {
    
    
    User user1 = new User();
    
    
    
    User user2 = new User();
    
    User user3 = new User();
    
    User userNull = null;
    
    @Test
   public void equalsTestTrue(){
    
        Assert.assertTrue(user1.equals(user2));
   }
   
   @Test
   public void equalsTestSameObject(){
       Assert.assertTrue(user1.equals(user1));
        
   }
   
   @Test
    public void equalsTestFalse(){
    
       Assert.assertFalse(user1.equals(user3));
   }
   
   @Test
   public void equalsTestNullObject(){
    
       Assert.assertFalse(user1.equals(userNull));
   }
    
    @Test
    public void emailGetter() {
    
        Assert.assertEquals("test@gmail.com", user1.getEmail());
    }
    
    @Test
    public void emailSetter() {
        user1.setEmail("testtest@gmail.com");
        Assert.assertEquals("testtest@gmail.com", user1.getEmail());
    }
    
    @Test
    public void passwordgetter() {
    
        Assert.assertEquals("test", user1.getPassword());
    }
    
    @Test
    public void passwordSetter() {
        
        user1.setPassword("password");
        Assert.assertEquals("password", user1.getPassword());
    }
    
    @Test
    public void contructorTest(){
    
        Assert.assertEquals("test@gmail.comtest", user2.getEmail() + user2.getPassword());
    }

    @Test
    public void user_does_not_equal_string() {
        String string = "Not a User";
        Assert.assertFalse(user1.equals(string));
    }
}
