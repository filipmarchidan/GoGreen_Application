package API;

import API.messages.Message;
import client.Client;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class ClientTest {
    
    Client client;
    User user;
    RestTemplate restTemplate;
    ServerApplication serverApplication;
    @Before
    public void before(){
        user = new User("test@test.com","supersecretpassword");
        user.setId(1);
        client = Client.createInstance("http://localhost:8080/");
        serverApplication = new ServerApplication();
    }

    @Test
    public void client_retrieves_message(){
        serverApplication.main(new String[0]);
        Message message = new Message("Paul");
        User[] s = client.getUsers();
        System.out.println(s);
        String other = "{\"id\":1,\"content\":\"Hello, Paul!\"}";
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
        Activity veggy = new Activity(1, ActType.vegetarian_meal,1,Activity.getDateTime());
        Activity back = client.addActivity(veggy);
        Assert.assertEquals(veggy.getDate_time(),back.getDate_time());
        Assert.assertEquals(veggy.getActivity_type(),back.getActivity_type());
        Assert.assertEquals(veggy.getUserId(),back.getUserId());
    }

    @Test
    public void activity_list_contains_activity(){

        client = Client.getInstance();
        Activity veggy = new Activity(1,ActType.vegetarian_meal,1,Activity.getDateTime());
        Activity back = client.addActivity(veggy);
        Activity[] activities = client.getActivities();
        Assert.assertTrue(Arrays.asList(activities).contains(back));
    }


}
