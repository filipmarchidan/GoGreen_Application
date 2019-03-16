package API;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity // This tells Hibernate to make a table out of this class
public class Activity {
    
    
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    private int user_id;
    
    private String activity_type;
    
    private int co2_savings;
    
    private String date_time;
    

    public Activity(){

    }

    public Activity(Integer id, int user_id, String activity_type, int CO2_savings, String date_time) {
        this.id = id;
        this.user_id = user_id;
        this.activity_type = activity_type;
        this.co2_savings = CO2_savings;
        this.date_time = date_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public int getCO2_savings() {
        return co2_savings;
    }

    public void setCO2_savings(int CO2_savings) {
        this.co2_savings = CO2_savings;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    /*
    public static void main(String[] args) {
    
    
        System.out.println(getDateTime());
    
    }
    */

}
