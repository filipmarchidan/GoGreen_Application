package client;


import com.google.gson.Gson;

import database.entities.Achievement;
import database.entities.Activity;
import database.entities.User;
import gui.LoginController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


public class Client {

    static Gson gson = new Gson();
    //private  String address;
    private static RestTemplate restTemplate = new RestTemplate();
    private HttpHeaders headers;

    /**
     * set the headers.
     * @param sessionCookie sessionCookie
     * @return httpHeaders
     */
    public static HttpHeaders setHeaders(String sessionCookie) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie", sessionCookie);
        
        return headers;

    }
    
    /**
     * make a getRequest.
     * @param sessionCookie sessionCookie
     * @param address address
     * @return httpEntity
     */
    public static HttpEntity<String> getRequest(String sessionCookie, String address) {

        HttpHeaders headers = setHeaders(sessionCookie);
        //RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate.exchange(address, HttpMethod.GET, request, String.class);

    }

    /**
     * make a postRequest.
     * @param sessionCookie sessionCookie
     * @param address address
     * @param params params
     * @return httpEntity
     */
    public static HttpEntity<String> postRequest(String sessionCookie, String address,
                                                 MultiValueMap<String, Object> params) {
        
        HttpHeaders headers = setHeaders(sessionCookie);
        //RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        try {
            return restTemplate.exchange(address, HttpMethod.POST, request, String.class);
        } catch (HttpServerErrorException exception) {
            System.out.println(exception.getClass());
            return null;
        } catch (HttpClientErrorException exception) {
            System.out.println(exception.getClass());
            return null;
        }

    }

    /**
     * getUsers.
     * @return all users
     */
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

    /**
     * getActivities.
     * @return all activities
     */
    public static Activity[] getActivities() {


        HttpEntity<String> result = getRequest(LoginController.sessionCookie,"http://localhost:8080/activities");

        Activity[] activities = gson.fromJson(result.getBody(), Activity[].class);
        return activities;

    }

    /**
     *getAchievements.
     * @param email String
     * @return all achievements from a user
     */
    public static Achievement[] getAchievements(String email) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("email",email);
        HttpEntity<String> result = postRequest(LoginController.sessionCookie, "http://localhost:8080/getAchievements", params);
        Achievement[] achievements = gson.fromJson(result.getBody(), Achievement[].class);
        return achievements;
    }

    /**
     * getFriends.
     * @return all friends
     */
    public static User[] getFriends() {

        HttpEntity<String> result = getRequest(LoginController.sessionCookie,"http://localhost:8080/getFriends");
        User[] friends = gson.fromJson(result.getBody(), User[].class);

        return friends;
    }

    /**
     * getUserByEmail.
     *@param email String
     *@return user
     */
    public static User getUserByEmail(String email) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("email",gson.toJson(email));
        User friend = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/findByEmail",params).getBody(),User.class);
        return friend;
    }
    
    /**
     * find current user.
     * @return user
     */
    public static  User findCurrentUser() {
        HttpEntity<String> response = getRequest(LoginController.sessionCookie,"http://localhost:8080/finduser");
        return gson.fromJson(response.getBody(),User.class);
    }

    /**
     * update solar panels.
     * @param user user
     * @return user
     */
    public static Activity updateSolar(User user) {
        
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        Activity activity = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/updateSolar",params).getBody(),Activity.class);
        
        return activity;
    }
    
    /**
     * add a new user.
     * @param user user
     * @return new user
     */
    public static boolean addUser(User user) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", user.getEmail());
        params.add("password", user.getPassword());
        //params.add("user", user);
        HttpEntity<String> result = postRequest("", "http://localhost:8080/addUser", params);
        if (result != null) {
            return gson.fromJson(result.getBody(),boolean.class);
        }
        return false;
    }

    /**
     * return the sessionCookie.
     * @param email email
     * @param password password
     * @return sessionCookie
     */
    public static String getSessionCookie(String email, String password) {

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", email);
        params.add("password", password);
        HttpEntity<String> string = postRequest("", "http://localhost:8080/login", params);
        if (string != null) {
            return string.getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        }
        return null;
    }
    
    /**
     * follow a user.
     * @param user user
     * @return user
     */
    public static User followUser(User user) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        User user1 = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/followFriend",params).getBody(), User.class);
        return user1;
    }
    
    /**
     * unfolow a user.
     * @param user user
     * @return user
     */
    public static User unfollowUser(User user) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        User user1 = gson.fromJson(postRequest(LoginController.sessionCookie,"http://localhost:8080/unfollowFriend",params).getBody(),User.class);
        return user1;
    }
    
    
    /**
     * get the restTemplate.
     * @return restTemplate
     */
    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
