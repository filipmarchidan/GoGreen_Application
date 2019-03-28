package client;

import API.UserService;
import com.google.gson.Gson;

import database.entities.Activity;
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

public class Client {
    
    private static Client client = new Client("");
    
    Gson gson;
    //private  String address;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    

    public static HttpHeaders setHeaders(String sessionCookie) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie", sessionCookie);
        
        return headers;

    }

    public Client(String sessionCookie) {
        
        this.gson = new Gson();
        this.restTemplate = new RestTemplate();
        this.headers = setHeaders(sessionCookie);

    }
    
    public static Client getInstance() {
        return client;
        
    }
    
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
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(address, HttpMethod.GET, request, String.class);
    
    }


    public static HttpEntity<String> postRequest(String sessionCookie, String address, MultiValueMap<String, Object> params) {
        
        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        return restTemplate.exchange(address, HttpMethod.POST, request, String.class);

    }


    public static User[] getUsers(String sessionCookie) {


        HttpEntity<String> result = getRequest(sessionCookie,"http://localhost:8080/allUsers");
        
        //this getRequest returns an Iterable<User>
        //but in JSON that is basically equal to an array.
        //String result = getRequest("allUsers",null);
        
        //System.out.println(result);
        
        //so we can convert the JSON string to a User Array like this.
        //User[] users = gson.fromJson(result, User[].class);
        
        User[] users = client.gson.fromJson(result.getBody(), User[].class);
        return users;
    }
    
    public static Activity[] getActivities(String sessionCookie) {


        HttpEntity<String> result = getRequest(sessionCookie,"http://localhost:8080/activities");

        Activity[] activities = client.gson.fromJson(result.getBody(), Activity[].class);
        return activities;

    }

    public User[] getFriends(int id) {

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

    
    
    public static void main(String[] args) {

        Client client = new Client("");

        String sessionCookie = client.getSessionCookie("test", "test");

        System.out.println(getUsers(sessionCookie)[0].getEmail());
       // System.out.println(getActivities(sessionCookie)[0]);

    }
    public static void addUser(User user)
    {
       MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
       params.add("username", user.getEmail());
       params.add("password", user.getPassword());
        //params.add("user", user);
       postRequest("", "http://localhost:8080/addUser", params);
    }


    public static String getSessionCookie(String email, String password) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", email);
        params.add("password", password);

        String sessionCookie = client.postRequest("", "http://localhost:8080/login", params)
            .getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[0];

        return sessionCookie;

    }

}
