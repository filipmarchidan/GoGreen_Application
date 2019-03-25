package database.entities;


import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {
    
    String originalDate = Activity.getDateTime();
    Activity activity1 = new Activity(1,ActType.vegetarian_meal, 1, originalDate);
    Activity activity2 = new Activity(1,ActType.vegetarian_meal, 1, originalDate);
    Activity activity3 = new Activity(1,ActType.vegetarian_meal, 2, originalDate);
    
    Activity activityNull = null;

    @Test
    public void equalsTestTrue(){
        
        Assert.assertTrue(activity1.equals(activity2));
    }
    
    @Test
    public void equalsTestFalse() {
    
        Assert.assertFalse(activity1.equals(activity3));
    }
    
    @Test
    public void equalsTestNullObject(){
    
        Assert.assertFalse(activity1.equals(activityNull));
    }
    
    @Test
    public void equalsTestSameObject(){
    
        Assert.assertTrue(activity1.equals(activity1));
    }
    
    @Test
    public void getUserID() {
    
        Assert.assertEquals(1, activity1.getUserId());
    }
    
    @Test
    public void setUserID() {
        
        activity1.setUser_id(5);
        Assert.assertEquals(5, activity1.getUserId());
    }
    
    @Test
    public void getType() {
    
        Assert.assertEquals(ActType.vegetarian_meal,activity1.getActivity_type());
    }
    
    @Test
    public void setType() {
        activity1.setActivity_type(ActType.bike);
        Assert.assertEquals(ActType.bike, activity1.getActivity_type());
    }

    
    @Test
    public void getDateTime() {
        Assert.assertEquals(originalDate, activity1.getDateTime());
    }
    
    @Test
    public void setDateTime() {
        String date = Activity.getDateTime();
        activity1.setDate_time(date);
        Assert.assertEquals(date, activity1.getDate_time());
    }

    @Test
    public void Activity_does_not_equal_string() {
        String string = "not an activity";
        Assert.assertFalse(activity1.equals(string));
    }
    
}

