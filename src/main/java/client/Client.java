package client;

import API.UserService;
import com.google.gson.Gson;

import database.entities.Activity;
import database.entities.ActivityType;
import database.entities.User;

import gui.LoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class Client {
    
    //private static Client client = new Client("");
    
    static Gson gson = new Gson();
    //private  String address;
    private static RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers;
    
    /*
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    */
    

    public static HttpHeaders setHeaders(String sessionCookie) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie", sessionCookie);
        
        return headers;

    }
    /*
    public Client(String sessionCookie) {
        
        this.gson = new Gson();
        this.restTemplate = new RestTemplate();
        this.headers = setHeaders(sessionCookie);

    }
    */
    
    /*
    public static String getSessionCookie(String username, String password) {
        
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        
        HttpEntity<Response> response = HttpRequests.postRequest("", url_login, params);
        HttpHeaders responseHeaders = response.getHeaders();
        if (responseHeaders.getFirst(HttpHeaders.SET_COOKIE) != null) {
            return responseHeaders.getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        } else {
            return "No cookie found.";
        }

    }*/

    public static HttpEntity<String> getRequest(String sessionCookie, String address) {

        HttpHeaders headers = setHeaders(sessionCookie);
        //RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(address, HttpMethod.GET, request, String.class);
    
    }


    public static HttpEntity<String> postRequest(String sessionCookie, String address, MultiValueMap<String, Object> params) {
        
        HttpHeaders headers = setHeaders(sessionCookie);
        //RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        try {
            return restTemplate.exchange(address, HttpMethod.POST, request, String.class);
        }
        catch (Exception e) {
            return null;
        }

    }


    public static User[] getUsers() {


        HttpEntity<String> result = getRequest(LoginController.sessionCookie,"http://localhost:8080/allUsers");
        
        //this getRequest returns an Iterable<User>
        //but in JSON that is basically equal to an array.
        //String result = getRequest("allUsers",null);
        
        //System.out.println(result);
        
        //so we can convert the JSON string to a User Array like this.
        //User[] users = gson.fromJson(result, User[].class);
        
        User[] users = gson.fromJson(result.getBody(), User[].class);
        return users;
    }
    
    public static Activity[] getActivities() {


        HttpEntity<String> result = getRequest(LoginController.sessionCookie,"http://localhost:8080/activities");

        Activity[] activities = gson.fromJson(result.getBody(), Activity[].class);
        return activities;

    }

    public static User[] getFriends() {

        HttpEntity<String> result = getRequest(LoginController.sessionCookie,"http://localhost:8080/getFriends");
        User[] friends = gson.fromJson(result.getBody(), User[].class);

        return friends;
    }

    /*
    public int getScore(Activity a) {
        String result = postRequest("getscore", a);
        int score = gson.fromJson(result, int.class);
        return score;
    }
    */

    
    /*
    public static void main(String[] args) {

        Client client = new Client("");

        String sessionCookie = client.getSessionCookie("test", "test");

        System.out.println(getUsers(sessionCookie)[0].getEmail());
       // System.out.println(getActivities(sessionCookie)[0]);

    }
    */
    
    public static  User findCurrentUser() {
        HttpEntity<String> response = getRequest(LoginController.sessionCookie,"http://localhost:8080/finduser");
        return gson.fromJson(response.getBody(),User.class);
    }
    
    public static User updateSolar(User user) {
        
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        User user1 = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/updatesolar",params).getBody(),User.class);
        
        return user1;
    }
    
    public static String addUser(User user)
    {
       MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
       params.add("username", user.getEmail());
       params.add("password", user.getPassword());
        //params.add("user", user);
       HttpEntity<String> result = postRequest("", "http://localhost:8080/addUser", params);
       return gson.fromJson(result.getBody(),String.class);
    }


    public static String getSessionCookie(String email, String password) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", email);
        params.add("password", password);
        HttpEntity<String> s = postRequest("", "http://localhost:8080/login", params);
        if (s != null) {
            return s.getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        }
        return null;
    }
    
    public static User followUser(User user) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        User user1 = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/followFriend",params).getBody(),User.class);
        return user1;
    }
    
    
    public static User unfollowUser(User user) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        User user1 = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/unfollowFriend",params).getBody(),User.class);
        return user1;
    }
    
    
    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
