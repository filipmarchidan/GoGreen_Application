package API;

import API.messages.Message;
import client.Client;
import com.google.gson.Gson;
import database.entities.Achievement;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@AutoConfigureMockRestServiceServer
public class ClientTest {
    
    Gson gson = new Gson();
    
    
    private RestTemplate restTemplate;
    
    private MockRestServiceServer mockServer;
    
    @Before
    public void setup() throws  Exception {
        
        mockServer = MockRestServiceServer.bindTo(Client.getRestTemplate()).build();
        User user1 = new User("test1","testpass");
        Set<User> users = new HashSet<>();
        users.add(user1);
        Activity activity = new Activity(ActType.vegetarian_meal,1,Activity.getCurrentDateTimeString());
        Set<Activity> activities = new HashSet<>();
        activities.add(activity);
        Achievement achievement = new Achievement(1,"testach",500);
        Set<Achievement> achievements = new HashSet<>();
        achievements.add(achievement);
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        
        mockServer.expect(requestTo("http://localhost:8080/getFriends")).andRespond(withSuccess(gson.toJson(users), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/addactivity")).andRespond(withSuccess(gson.toJson(activity), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/removeactivity")).andRespond(withSuccess(gson.toJson(true), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/getachievements")).andRespond(withSuccess(gson.toJson(achievements), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/activities")).andRespond(withSuccess(gson.toJson(activities), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/allActType")).andRespond(withSuccess(gson.toJson(integerList), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/followFriend")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/unfollowFriend")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/updateSolar")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo("http://localhost:8080/findUser")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
    
    
    }
    
    @Test
    public void getFriendsTest() {
        User[] users = Client.getFriends();
        System.out.println(users);
        Assert.assertTrue(users.length == 1);
        Assert.assertTrue(users[0].getEmail().equals("test1"));
    }
    
    @Test
    public void getAllActivitiesTest() {
        Activity[] activities = Client.getActivities();
    }
    
    
    
    



}