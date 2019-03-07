package API;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Activity {
    
    
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    private int user_id;
    
    private String activity_type;
    
    private int CO2_savings;
    
    private Date date_time;
    
    
    public Activity(Integer id, int user_id, String activity_type, int CO2_savings, Date date_time) {
        this.id = id;
        this.user_id = user_id;
        this.activity_type = activity_type;
        this.CO2_savings = CO2_savings;
        this.date_time = date_time;
    }
    
    private static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    
    public static void main(String[] args) {
    
    
        System.out.println(getDateTime());
    
    }

}
