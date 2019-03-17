package API;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activities")
public class Activity {
    
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private int user_id;
    
    @Column(name = "activity_type", nullable = false)
    private String activity_type;
    
    @Column(name = "CO2_savings", nullable = false)
    private int co2_savings;
    
    @Column(name = "date_time", nullable = false)
    private String date_time;
    

    public Activity(){

    }

    public Activity(int user_id, String activity_type, int CO2_savings, String date_time) {
        this.user_id = user_id;
        this.activity_type = activity_type;
        this.co2_savings = CO2_savings;
        this.date_time = date_time;
    }

   /* public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/

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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return user_id == activity.user_id &&
            co2_savings == activity.co2_savings &&
            Objects.equals(id, activity.id) &&
            Objects.equals(activity_type, activity.activity_type) &&
            Objects.equals(date_time, activity.date_time);
    }
    
   
}
