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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@AutoConfigureMockRestServiceServer
public class ClientTest {
    
    Gson gson = new Gson();
    
    
    private RestTemplate restTemplate;
    
    private MockRestServiceServer mockServer;
    User user1 = new User("test1","testpass");
    Set<User> users = new HashSet<>();

    Activity activity = new Activity(ActType.vegetarian_meal,1,Activity.getCurrentDateTimeString());
    Activity solarActivity = new Activity(ActType.solar_panel,1,Activity.getCurrentDateTimeString());
    Set<Activity> activities = new HashSet<>();

    Achievement achievement = new Achievement(1,"testach",500);
    Set<Achievement> achievements = new HashSet<>();

    List<Integer> integerList = new ArrayList<>();

    
    
    @Before
    public void setup() throws  Exception {
        mockServer = MockRestServiceServer.bindTo(Client.getRestTemplate()).build();
        users.add(user1);
        activities.add(activity);
        achievements.add(achievement);
        integerList.add(1);
        integerList.add(2);
        /*
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
        */
    
    }
    
    
    @Test
    public void getAchievementsTest() {
        mockServer.expect(requestTo("http://localhost:8080/getAchievements")).andRespond(withSuccess(gson.toJson(achievements), MediaType.APPLICATION_JSON));
        
        Achievement[] achievements = Client.getAchievements("test@email.com");
        Assert.assertTrue(achievements[0].equals(achievement));
    }
    
    @Test
    public void getFriendsTest() {
        mockServer.expect(requestTo("http://localhost:8080/getFriends")).andRespond(withSuccess(gson.toJson(users), MediaType.APPLICATION_JSON));
    
        User[] users = Client.getFriends();
        System.out.println(users);
        Assert.assertTrue(users[0].equals(user1));
    }
    
    @Test
    public void getAllActivitiesTest() {
        mockServer.expect(requestTo("http://localhost:8080/activities")).andRespond(withSuccess(gson.toJson(activities), MediaType.APPLICATION_JSON));
    
        Activity[] activities = Client.getActivities();
        Set<Activity> activitySet = new HashSet<>(Arrays.asList(activities));
        Assert.assertEquals(activitySet,this.activities);
    }
    @Test
    public void findUserTest() {
        mockServer.expect(requestTo("http://localhost:8080/finduser")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        
        User user = Client.findCurrentUser();
        Assert.assertTrue(user.equals(user1));
    }
    
    @Test
    public void addUserTest() {
        mockServer.expect(requestTo("http://localhost:8080/addUser")).andRespond(withSuccess(gson.toJson(true), MediaType.APPLICATION_JSON));
        boolean bool = Client.addUser(user1);
        Assert.assertTrue(bool);
    }
    @Test
    public void addFalseUserTest() {
        mockServer.expect(requestTo("http://localhost:8080/addUser")).andRespond(withServerError());
        boolean bool = Client.addUser(user1);
        Assert.assertFalse(bool);
    }
    
    
    @Test
    public void updateSolarTest() {
        mockServer.expect(requestTo("http://localhost:8080/updateSolar")).andRespond(withSuccess(gson.toJson(solarActivity), MediaType.APPLICATION_JSON));
        Activity activity = Client.updateSolar(user1);
        Assert.assertEquals(ActType.solar_panel,activity.getActivity_type());
    }

    @Test
    public void getUserByEmailTest() {
        mockServer.expect(requestTo("http://localhost:8080/findByEmail")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        User user = Client.getUserByEmail(user1.getEmail());
        Assert.assertEquals(user,user1);
    }
    
    @Test
    public void getSessionCookieTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,"blahblahsession");
        mockServer.expect(requestTo("http://localhost:8080/login")).andRespond(withSuccess(gson.toJson(users), MediaType.APPLICATION_JSON).headers(headers));
    
        String sessionCookie = Client.getSessionCookie(user1.getEmail(),user1.getPassword());
        Assert.assertEquals(sessionCookie,headers.getFirst(HttpHeaders.SET_COOKIE));

    }
    
    @Test
    public void getFalseSessionCookieTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,"blahblahsession");
        mockServer.expect(requestTo("http://localhost:8080/login")).andRespond(withServerError());
        
        String sessionCookie = Client.getSessionCookie(user1.getEmail(),user1.getPassword());
        Assert.assertEquals(sessionCookie,null);
        
    }
    
    @Test
    public void getAllUsersTest() {
        mockServer.expect(requestTo("http://localhost:8080/allUsers")).andRespond(withSuccess(gson.toJson(users), MediaType.APPLICATION_JSON));
    
        User[] users = Client.getUsers();
        Set<User> userSet = new HashSet<>(Arrays.asList(users));
        Assert.assertEquals(this.users,userSet);
    }
    
    @Test
    public void followUserTest() {
        mockServer.expect(requestTo("http://localhost:8080/followFriend")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        
        User user = Client.followUser(user1);
        Assert.assertEquals(user,user1);
    }
    
    @Test
    public void unfollowUserTest() {
        mockServer.expect(requestTo("http://localhost:8080/unfollowFriend")).andRespond(withSuccess(gson.toJson(user1), MediaType.APPLICATION_JSON));
        
        User user = Client.unfollowUser(user1);
        Assert.assertEquals(user,user1);
    }
    
    
    
    
    



}