package database.entities;

import org.junit.Assert;
import org.junit.Test;

public class ActivityTypeTest {
    
    ActivityType activityType = new ActivityType();
    
    @Test
    public void getSetIdTest() {
        activityType.setId(1);
        Assert.assertTrue(1 == activityType.getId());
    }
    
    @Test
    public void getSetNameTest() {
        activityType.setActivity_name("testname");
        Assert.assertEquals(activityType.getActivity_name(),"testname");
    }
    
    @Test
    public void getSetSavingsTest() {
        activityType.setCo2_savings(500);
        Assert.assertTrue(activityType.getCo2_savings() == 500);
    }
    
    
}
