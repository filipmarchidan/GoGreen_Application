package database.entities;


import org.junit.Assert;
import org.junit.Test;

public class ActivityTest {
    
    String originalDate = Activity.getCurrentDateTimeString();
    Activity activity1 = new Activity(ActType.vegetarian_meal, 1, originalDate);
    Activity activity2 = new Activity(ActType.vegetarian_meal, 1, originalDate);
    Activity activity3 = new Activity(ActType.vegetarian_meal, 2, originalDate);

    Activity activity = new Activity();



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


