package database.entities;


import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.Date;

public class ActivityTest {
    
    String originalDate = Activity.getCurrentDateTimeString();
    
    Activity activity1 = new Activity(ActType.vegetarian_meal, 1, originalDate);
    Activity activity2 = new Activity(ActType.vegetarian_meal, 1, originalDate);
    Activity activity3 = new Activity(ActType.vegetarian_meal, 2, originalDate);
    Activity activity4 = new Activity(ActType.local_produce, 1, originalDate);
    



    Activity activityNull = null;
    
    Activity activityNull2 = null;
    


    @Test
    public void equalsTestSameObject(){
        Assert.assertTrue(activity1.equals(activity1));
    }
    
    
    @Test
    public void equalsTestNullObject(){
        Assert.assertFalse(activity1.equals(null));
    }
    
    @Test
    public void equalsOneObjectNull(){
        Assert.assertFalse(activity1.equals(activityNull));
    }
    
    @Test
    public void Test2DifferentObjects(){
        int test = 3;
        Assert.assertFalse(activity1.equals(test));
        
    }
    
    
    
    @Test
    public void equalsTestTrue(){
        
        Assert.assertTrue(activity1.equals(activity2));
    }
    
    @Test
    public void hashCodeEquelas(){
        Assert.assertEquals(activity1.hashCode(), activity1.hashCode());
    }
    
    @Test
    public void hashCodeNotEqual(){
        Assert.assertNotEquals(activity1.hashCode(), activity4.hashCode());
    }
    
    @Test
    public void equalsTestFalseForActivityAmount() {
        
        Assert.assertFalse(activity1.equals(activity3));
    }
    
    @Test
    public void equalsFalseForActType(){
        
        Assert.assertFalse(activity1.equals(activity4));
    }
    
    @Test
    public void equalsFalseForDate() throws InterruptedException {
        Thread.sleep(1000);
        String originalDate2 = Activity.getCurrentDateTimeString();
        Activity activity5 = new Activity(ActType.vegetarian_meal, 1, originalDate2);
        System.out.println(originalDate);
        System.out.println(originalDate2);
        Assert.assertFalse(activity1.equals(activity5));
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
        Assert.assertEquals(originalDate, activity1.getDate_time());
    }
    
    @Test
    public void setDateTime() {
        String date = Activity.getCurrentDateTimeString();
        activity1.setDate_time(date);
        Assert.assertEquals(date, activity1.getDate_time());
    }
    
    @Test
    public void Activity_does_not_equal_string() {
        String string = "not an activity";
        Assert.assertFalse(activity1.equals(string));
    }
    
}


