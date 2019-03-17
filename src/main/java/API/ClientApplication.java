package API;




import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**This client class is able to send GET request to different routs;
 *
 */
public class ClientApplication {
    
    Gson gson;
    private  String address;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private static ClientApplication clientApplication;
    
    //private static String getUrlString_json = "http://localhost:8080/API.json";
    
    /** Creates an instance of ClientApplication and stores it statically
     *
     * @param address the address the clientApplication should connect to
     * @return the created ClientApplication
     */
    public static ClientApplication createInstance(String address) {
        clientApplication = new ClientApplication(address);
        return clientApplication;
    }
    
    /** gets the static client instance
     *
     * @return the static client instance (can be null)
     */
    public static ClientApplication getInstance() {
        return clientApplication;
        
    }
    
    public ClientApplication(String serverAdress) {
        
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
    
    private String getRequest(String endpoint,Object obj) {
        
        String json = gson.toJson(obj);
        HttpEntity<String> request = new HttpEntity<String>(json,headers);
        
        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(address + endpoint,HttpMethod.GET, request, String.class);
        String result = response.getBody();
        
        return result;
    }
    
    private String postRequest(String endpoint, Object obj) {
        
        String json = gson.toJson(obj);
        System.out.println(json);
        HttpEntity<String> request = new HttpEntity<String>(json,headers);
        
        // Send request with POST method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(address + endpoint,HttpMethod.POST, request, String.class);
        String result = response.getBody();
        
        return result;
    }
    
    
    /*
    public static void main(String[] args) {
        ClientApplication client = new ClientApplication("http://localhost:8080/");
        //System.out.println(client.getRequest("all", null));
        System.out.println(client.getActivities());
        //System.out.println(LocalDateTime.now());
        //client.addActivity(new Activity(1,1,"vegetarian_meal",50,Activity.getDateTime()));
        //System.out.println(client.postRequest("add", new User("asjhfv", "skldfgfja")));
        //System.out.println(client.getRequest("all", null));
        //System.out.println(client.getActivities());
        
        
    }
    */
    
    
    /** Mockup that sends log in info to server, to be replaced.
     *
     * @param email email of user
     * @param pass password of user
     */
    
    /** Adds an Activity to the Server.
     *
     * @param activity Activity to be added
     */
    public Activity addActivity(Activity activity) {
        
        String result = postRequest("addactivity",activity);
        System.out.println(result);
        Activity activity1 = gson.fromJson(result,Activity.class);
        return activity1;
    }
    
    /** Method that requests the leaderboard from the server.
     *
     */
    
    public User[] getUsers(){
        
        //this getRequest returns an Iterable<User>
        //but in JSON that is basically equal to an array.
        String result = getRequest("allUsers",null);
        
        System.out.println(result);
        
        //so we can convert the JSON string to a User Array like this.
        User[] users = gson.fromJson(result, User[].class);
        
        return users;
    }
    
    public Activity[] getActivities() {
        
        String result = getRequest("activities", null);
        System.out.println(result);
        Activity[] activities = gson.fromJson(result, Activity[].class);
        return activities;
    }
    
    
    
    
}



