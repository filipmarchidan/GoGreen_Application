package API.entities;

import API.Activity;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ActivityTest {
    
    String originalDate = Activity.getDateTime();
    Activity activity1 = new Activity(1,"vegetarian_meal", 15, originalDate);
    Activity activity2 = new Activity(1,"vegetarian_meal", 15, originalDate);
    Activity activity3 = new Activity(1,"vegetarian_meal", 20, originalDate);
    
    
    
    @Test
    void equalsTestTrue(){
        
        assertTrue(activity1.equals(activity2));
    }
    
    @Test
    void equalsTestFalse() {
    
        assertFalse(activity1.equals(activity3));
    }
    
    @Test
    void getUserID() {
        
        assertEquals(1, activity1.getUser_id());
    }
    
    @Test
    void setUserID() {
        
        activity1.setUser_id(5);
        assertEquals(5, activity1.getUser_id());
    }
    
    @Test
    void getType() {
        
        assertEquals("vegetarian_meal",activity1.getActivity_type());
    }
    
    @Test
    void setType() {
        activity1.setActivity_type("bike");
        assertEquals("bike", activity1.getActivity_type());
    }
    
    
    @Test
    void getCO2Savings() {
        assertEquals(15, activity1.getCO2_savings());
    }
    
    @Test
    void setCO2Savings() {
        activity1.setCO2_savings(50);
        assertEquals(50, activity1.getCO2_savings());
    }
    
    @Test
    void getDateTime() {
        assertEquals(originalDate, activity1.getDateTime());
    }
    
    @Test
    void setDateTime() {
        String date = Activity.getDateTime();
        activity1.setDate_time(date);
        assertEquals(date, activity1.getDate_time());
    }
    
}


