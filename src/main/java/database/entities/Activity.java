package database.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activities")

public class Activity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "activity_type", nullable = false)
    
    private String activity_type;

    @Column(name = "CO2_savings", nullable = false)
    
    private int co2_savings;

    @Column(name = "date_time", nullable = false)
    
    private String date_time;
    
    public Activity() {}
    
    
    public Integer getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getActivity_type() {
        return activity_type;
    }
    
    public int getCo2_savings() {
        return co2_savings;
    }
    
    public String getDate_time() {
        return date_time;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }
    
    public void setCo2_savings(int co2_savings) {
        this.co2_savings = co2_savings;
    }
    
    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
    
    /**
     * returns the date and time.
     *
     * @return date
     */
    public static String getCurrentDateTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return getUser().getId() == activity.getUser().getId()
                && co2_savings == activity.co2_savings
                && Objects.equals(id, activity.id)
                && Objects.equals(activity_type, activity.activity_type)
                && Objects.equals(date_time, activity.date_time);
    }
    
   
}
