package database.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "activities")
@NoArgsConstructor
public class Activity {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @Getter
    @Setter
    private User user;

    @Column(name = "activity_type", nullable = false)
    @Getter
    @Setter
    private ActType activity_type;

    @Column(name = "activity_amount", nullable = false)
    @Getter
    @Setter
    private int activity_amount;

    @Column(name = "date_time", nullable = false)
    @Setter
    @Getter
    private String date_time;


    /**
     * creates an activity.
     * @param activity_type activity_type
     * @param date_time date_time
     */
    public Activity(ActType activity_type,int activity_amount, String date_time) {
        this.activity_type = activity_type;
        this.activity_amount = activity_amount;
        this.date_time = date_time;
    }
    public Activity(int id ,ActType activity_type,int activity_amount, String date_time) {
        this.id = id;
        this.activity_type = activity_type;
        this.activity_amount = activity_amount;
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
        return Objects.equals(getUser(),activity.getUser())
                && Objects.equals(id, activity.id)
                && Objects.equals(activity_amount,activity.activity_amount)
                && Objects.equals(activity_type, activity.activity_type)
                && Objects.equals(date_time, activity.date_time);
    }

    @Override
    public int hashCode() {
        return activity_amount*activity_type.ordinal()*date_time.toString().hashCode();
    }
    
   
}
