package API.messages;

import java.sql.Date;

//quick class change for making a preparation on the client side
// feel free to use this for the server or change/remove it
public class Activity {

    private Activities activity;
    private Date date;

    public Activity(Activities act, Date date){
        this.activity = act;
        this.date = date;
    }

}

