package database.entities;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class AchievementTest {
    Achievement achievement1 = new Achievement(1,"test",50);
    Achievement achievement2 = new Achievement(1,"test",50);
    Achievement achievement3 = new Achievement(2,"test",40);
    
    @Test
    public void achievementEqualsItself() {
        Assert.assertEquals(achievement1,achievement1);
    }
    
    @Test
    public void achievementEqualsSimilar() {
        Assert.assertEquals(achievement1,achievement2);
    }
    
    @Test
    public void achievementDoesNotEqualOther() {
        Assert.assertNotEquals(achievement1,achievement3);
    }
    
    @Test
    public void achievementDoesNotEqualNull() {
        Assert.assertNotEquals(achievement1,null);
    }
    
    @Test
    public void achievementDoesNotEqualString() {
        
        Assert.assertNotEquals(achievement1,"Hello");
    }
    
    @Test
    public void getIdTest() {
        
        Integer test = 1;
        Assert.assertEquals(achievement1.getId(),test);
        
    }
    @Test
    public void getValueTest() {
        
        Integer test = 50;
        Assert.assertEquals(achievement1.getAchievement_value(),test);
        
    }
    
    @Test
    public void getNameTest() {
        
        String test = "test";
        Assert.assertEquals(achievement1.getAchievement_name(),test);
        
    }
    @Test
    public void setIdTest() {
        achievement1.setId(4);
        Integer four = 4;
        Assert.assertEquals(achievement1.getId(),four);
        
    }
    
    @Test
    public void setValueTest() {
        achievement1.setAchievement_value(4);
        Integer four = 4;
        Assert.assertEquals(achievement1.getAchievement_value(),four);
        
    }
    
    @Test
    public void setNameTest() {
        achievement1.setAchievement_name("namechange");
        Assert.assertEquals(achievement1.getAchievement_name(),"namechange");
        
    }
    @Test
    public void setUser() {
        Set<User> users = new HashSet<>();
        User user = new User("name","password");
        users.add(user);
        achievement1.setUsers(users);
        Assert.assertTrue(users.equals(achievement1.getUsers()));
    }
    
    
}
