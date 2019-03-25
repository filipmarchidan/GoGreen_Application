package API;
/*
import API.messages.Message;
import client.ClientOld;
import database.entities.Activity;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ClientTest {

    ServerApplication serverApplication;
    ClientOld client;
    @Before
    public void before(){
        serverApplication = new ServerApplication();
        client = ClientOld.createInstance("http://localhost:8080/");
    }
    @Test
    public void client_retrieves_message(){

        ServerApplication.main(new String[0]);

        Message message = new Message("Paul");
        User[] s = client.getUsers();
        System.out.println(s);
        String other = "{\"id\":1,\"content\":\"Hello, Paul!\"}";
        //Assert.assertEquals(s,other);
    }

    @Test
    public void getinstance_returns_instance(){
        ClientOld client = ClientOld.createInstance("http://localhost:8080/");
        ClientOld client2 = ClientOld.getInstance();
        Assert.assertEquals(client,client2);

    }


    @Test
    public void client_adds_activity(){
        client = ClientOld.getInstance();
        Activity activity = new Activity();
        activity.setCo2_savings(50);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        activity.setActivity_type("veggy_meal");
        
        Activity back = client.addActivity(activity);
        Assert.assertEquals(activity.getDate_time(),back.getDate_time());
        Assert.assertEquals(activity.getCo2_savings(),back.getCo2_savings());
        Assert.assertEquals(activity.getActivity_type(),back.getActivity_type());
        Assert.assertEquals(activity.getUser().getId(),back.getUser().getId());
    }

    @Test
    public void activity_list_contains_activity(){

        client = ClientOld.getInstance();
        Activity activity = new Activity();
        activity.setCo2_savings(50);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        activity.setActivity_type("veggy_meal");
        
        Activity back = client.addActivity(activity);
        Activity[] activities = client.getActivities();
        Assert.assertTrue(Arrays.asList(activities).contains(back));
    }



}
*/