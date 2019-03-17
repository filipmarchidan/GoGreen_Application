package API.entities;

import API.Activity;


import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ActivityTest {
    
    String originalDate = Activity.getDateTime();
    Activity activity1 = new Activity(1,"vegetarian_meal", 15, originalDate);
    Activity activity2 = new Activity(1,"vegetarian_meal", 15, originalDate);
    Activity activity3 = new Activity(1,"vegetarian_meal", 20, originalDate);
    
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
    
        Assert.assertEquals(1, activity1.getUser_id());
    }
    
    @Test
    public void setUserID() {
        
        activity1.setUser_id(5);
        Assert.assertEquals(5, activity1.getUser_id());
    }
    
    @Test
    public void getType() {
    
        Assert.assertEquals("vegetarian_meal",activity1.getActivity_type());
    }
    
    @Test
    public void setType() {
        activity1.setActivity_type("bike");
        Assert.assertEquals("bike", activity1.getActivity_type());
    }
    
    
    @Test
    public void getCO2Savings() {
        Assert.assertEquals(15, activity1.getCO2_savings());
    }
    
    @Test
    public void setCO2Savings() {
        activity1.setCO2_savings(50);
        Assert.assertEquals(50, activity1.getCO2_savings());
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


