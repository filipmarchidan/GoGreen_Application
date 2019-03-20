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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @JsonIgnore
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    @JsonIgnore
    private User user;

    @Column(name = "activity_type", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private String activity_type;

    @Column(name = "CO2_savings", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private int co2_savings;

    @Column(name = "date_time", nullable = false)
    @Getter
    @Setter
    @JsonIgnore
    private String date_time;
    
    

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
