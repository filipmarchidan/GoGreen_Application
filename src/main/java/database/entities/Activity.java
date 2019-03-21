package database.entities;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activities")
public class Activity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "userId", nullable = false)
    private int userId;

    @Column(name = "activity_type", nullable = false)
    private ActType activity_type;

    @Column(name ="activity_amount", nullable = false)
    private int activity_amount;

    @Column(name = "date_time", nullable = false)
    private String date_time;


    /**
     * creates an activity.
     * @param user_id user_id
     * @param activity_type activity_type
     * @param date_time date_time
     */
    public Activity(int user_id, ActType activity_type,int activity_amount, String date_time) {
        this.userId = user_id;
        this.activity_type = activity_type;
        this.activity_amount = activity_amount;
        this.date_time = date_time;
    }

    public Activity(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getActivity_amount() {
        return activity_amount;
    }

    public void setActivity_amount(int activity_amount) {
        this.activity_amount = activity_amount;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public ActType getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(ActType activity_type) {
        this.activity_type = activity_type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    /**
     * returns the date and time.
     * @return date
     */
    public static String getDateTime() {
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
        return userId == activity.userId
                && Objects.equals(id, activity.id)
                && Objects.equals(activity_amount,activity.activity_amount)
                && Objects.equals(activity_type, activity.activity_type)
                && Objects.equals(date_time, activity.date_time);
    }
    
   
}
