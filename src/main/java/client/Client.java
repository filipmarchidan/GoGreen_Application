package client;

import com.google.gson.Gson;

import database.entities.Achievement;
import database.entities.Activity;
import database.entities.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/** This client class is able to send request to different routes.
 *
 */
public class Client {
    
    private static Client client;
    
    Gson gson;
    private  String address;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    
    /** constructor for the client application.
     *
     * @param serverAdress server the client links to
     */
    public Client(String serverAdress) {
        
        gson = new Gson();
        //instantiate the variables, template and headers.
        address = serverAdress;
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        
        
        headers.add("Content-Type","application/json");
        headers.add("Accept", "*/*");
        
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        
    }
    
    public static Client createInstance(String address) {
        client = new Client(address);
        return client;
    }
    
    public static Client getInstance() {
        return client;
        
    }
    
    
    private String getRequest(String endpoint,Object obj) {
        
        String json = gson.toJson(obj);
        HttpEntity<String> request = new HttpEntity<String>(json,headers);
        
        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(
                address + endpoint,HttpMethod.GET, request, String.class);
        
        String result = response.getBody();
        
        return result;
    }
    
    private String postRequest(String endpoint, Object obj) {
        
        String json = gson.toJson(obj);
        HttpEntity<String> request = new HttpEntity<String>(json,headers);
        
        // Send request with POST method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(
                address + endpoint,HttpMethod.POST, request, String.class);
        
        String result = response.getBody();
        
        return result;
    }
    
    /*
    public static void main(String[] args) {
        
        Client client = new Client("http://localhost:8080/");
        
        //System.out.println(client.getRequest("all", null));
        //System.out.println(client.getUsers());
        //System.out.println(LocalDateTime.now());
        client.addActivity(new Activity(1,"vegetarian_meal",50,Activity.getDateTime()));
        System.out.println(client.postRequest("add", new User("asjhfv", "skldfgfja")));
        //System.out.println(client.getRequest("all", null));
        //client.postRequest("add", new User("jv#2r","5wew5fs"));
        System.out.println(client.getActivities());
        
        
    }
    */
    
    
    /** Adds an Activity to the Server.
     *
     * @param activity Activity to be added
     */
    public Activity addActivity(Activity activity) {
        
        
        String result = postRequest("addactivity",activity);
        Activity activity1 = gson.fromJson(result,Activity.class);
        return activity1;
    }

    public boolean removeActivity(Activity activity) {

        String response = postRequest("removeactivity", activity);
        boolean result = gson.fromJson(response, boolean.class);
        return result;
    }
    
    /** Method that requests the leaderboard from the server.
     *
     */
    public User[] getUsers() {
        
        //this getRequest returns an Iterable<User>
        //but in JSON that is basically equal to an array.
        String result = getRequest("allUsers",null);
        
        System.out.println(result);
        
        //so we can convert the JSON string to a User Array like this.
        User[] users = gson.fromJson(result, User[].class);
        
        return users;
    }
    
    /** Sends a request to the server to add a User.
     *
     * @param user user to be added
     * @return the actual added user (correct ID)
     */
    public User addUser(User user) {
        String result = postRequest("add",user);
        User user1 = gson.fromJson(result,User.class);
        return  user1;
    }
    
    public void removeUser(User user) {
        String result = postRequest("removeUser", user);
    }
    
    /** retrieves all the activities (of a user?) from the server.
     *
     * @return an array of activities
     */
    public Activity[] getActivities() {
        
        String result = getRequest("activities", null);
        System.out.println(result);
        Activity[] activities = gson.fromJson(result, Activity[].class);
        return activities;
    }

    public Achievement[] getAchievements (User user) {

        String result = postRequest("getAchievements", user);
        Achievement[] achievement = gson.fromJson(result, Achievement[].class);
        return achievement;

    }

    public User[] getFriends(int id) {
        
        String result = postRequest("getFriends", id);
        User[] friends = gson.fromJson(result, User[].class);

        return friends;
    }

    /*
    public int getScore(Activity a) {
        String result = postRequest("getscore", a);
        int score = gson.fromJson(result, int.class);
        return score;
    }
    */
    
    
    
    
    
}



