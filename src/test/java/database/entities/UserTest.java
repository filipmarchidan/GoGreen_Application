package database.entities;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;


public class UserTest {
    
    
    User user1 = new User();
    
    User user2 = new User();
    
    User user3 = new User();

    User user4 = new User("test@test.junit",13,100);

    User user5 = new User();
    User userNull = null;
    @Before
    public void setup() {
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setTotalscore(10);
        
        user2.setEmail("email");
        user2.setPassword("password");
        user2.setTotalscore(10);
        
        user3.setEmail("ugh");
        user3.setPassword("bla");
        user3.setSolarPanel(true);
        user3.setTotalscore(100);

        Set<Achievement> achievementstest = new HashSet<>();
        Achievement achievement1 = new Achievement(1,"A",10);
        Achievement achievement2 = new Achievement(2,"B",20);
        achievementstest.add(achievement1);
        achievementstest.add(achievement2);
        user5.setAchievements(achievementstest);
        Set<User> friends = new HashSet<>();
        User friend1 = new User("a","a");
        User friend2 = new User("b","b");
        friends.add(friend1);
        friends.add(friend2);
        user5.setFriends(friends);
        Set<Activity> activities = new HashSet<>();
        Activity activity1 = new Activity(ActType.vegetarian_meal,10,"0");
        Activity activity2 = new Activity(ActType.solar_panel,20,"0");
        activities.add(activity1);
        activities.add(activity2);
        user5.setActivities(activities);
    }
    @Test
    public void testConstructor(){
        User user = new User();
        user.setSolarPanel(true);
        user.setPassword("bla");
        user.setEmail("ugh");
        user.setTotalscore(100);
        Assert.assertEquals(user3, user);
    }
    @Test
    public void testSolarPanel()
    {
        User user = new User();
        user.setSolarPanel(true);
        Assert.assertEquals(true, user.isSolarPanel());
    }
    @Test
    public void testScore()
    {
        User user = new User();
        user.setTotalscore(100);
        Assert.assertEquals(100, user3.getTotalscore());
    }
    @Test
    public void testSecondConstructor()
    {
        User user = new User("test@test.junit",13,100);
        Assert.assertTrue(user4.equals(user));
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
   public void equalsTestFalse() {
    
       Assert.assertFalse(user1.equals(user3));
   }
   
   @Test
   public void equalsTestNullObject(){
    
       Assert.assertFalse(user1.equals(userNull));
   }
    
    @Test
    public void equalsTestStringObject(){
        
        Assert.assertFalse(user1.equals("hello"));
    }
    
    @Test
    public void TestSameEmailDifferentPassword(){
        User userx = new User("email","blah");
        Assert.assertFalse(user1.equals(userx));
    }
   
   @Test
   public void testGetPassword(){
        Assert.assertEquals("password", user1.getPassword());
   }
   
   @Test
   public void testGetAchievements(){
       Set<Achievement> achievementstest = new HashSet<>();
       Achievement achievement1 = new Achievement(1,"A",10);
       Achievement achievement2 = new Achievement(2,"B",20);
       achievementstest.add(achievement1);
       achievementstest.add(achievement2);
       user5.setAchievements(achievementstest);
       Assert.assertEquals(achievementstest, user5.getAchievements());
   }
   
   @Test
   public void testFriendsGetterAndSetter()
   {
       Set<User> friends = new HashSet<>();
       User friend1 = new User("a","a");
       User friend2 = new User("b","b");
       friends.add(friend1);
       friends.add(friend2);
       Assert.assertEquals(friends, user5.getFriends());
   }
   
   @Test
   public void testActivityGetterAndSetter()
   {
       Set<Activity> activities2 = new HashSet<>();
       Activity activity1 = new Activity(ActType.vegetarian_meal,10,"0");
       Activity activity2 = new Activity(ActType.solar_panel,20,"0");
       activities2.add(activity1);
       activities2.add(activity2);
       Assert.assertEquals(activities2, user5.getActivities());
   }
  /*  @Test
    public void emailGetter() {
    
        Assert.assertEquals("email", user1.getEmail());
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
    */
    
}
