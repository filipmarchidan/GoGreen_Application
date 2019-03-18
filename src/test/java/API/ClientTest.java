package API;

import API.messages.Message;
import client.Client;
import database.entities.Activity;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ClientTest {

    ServerApplication serverApplication;
    Client client;
    @Before
    public void before(){
        serverApplication = new ServerApplication();
        client = Client.createInstance("http://localhost:8080/");
    }
    @Test
    public void client_retrieves_message(){

        serverApplication.main(new String[0]);

        Message message = new Message("Paul");
        User[] s = client.getUsers();
        System.out.println(s);
        String other = "{\"id\":1,\"content\":\"Hello, Paul!\"}";
        //Assert.assertEquals(s,other);
    }

    @Test
    public void getinstance_returns_instance(){
        Client client = Client.createInstance("http://localhost:8080/");
        Client client2 = Client.getInstance();
        Assert.assertEquals(client,client2);

    }


    @Test
    public void client_adds_activity(){
        client = Client.getInstance();
        Activity veggy = new Activity(1,"veggy_meal",50,Activity.getDateTime());
        Activity back = client.addActivity(veggy);
        Assert.assertEquals(veggy.getDate_time(),back.getDate_time());
        Assert.assertEquals(veggy.getCO2_savings(),back.getCO2_savings());
        Assert.assertEquals(veggy.getActivity_type(),back.getActivity_type());
        Assert.assertEquals(veggy.getUser_id(),back.getUser_id());
    }

    @Test
    public void activity_list_contains_activity(){

        client = Client.getInstance();
        Activity veggy = new Activity(1,"veggy_meal",50,Activity.getDateTime());
        Activity back = client.addActivity(veggy);
        Activity[] activities = client.getActivities();
        Assert.assertTrue(Arrays.asList(activities).contains(back));
    }



}
