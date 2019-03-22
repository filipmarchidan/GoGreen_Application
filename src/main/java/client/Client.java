package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import database.entities.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class Client {
    
    private static Client client;
    
    
    Gson gson;
    private String address;
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    
    
    public HttpHeaders setHeaders(String sessionCookie) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Cookie", sessionCookie);
        
        return headers;
        
    }
    
    public Client(String sessionCookie, String address) {
        
        this.gson = new Gson();
        this.address = address;
        this.restTemplate = new RestTemplate();
        this.headers = setHeaders(sessionCookie);
        
    }
    
    
    public static Client createInstance(String sessionCookie, String address) {
        client = new Client(sessionCookie, address);
        return client;
    }
    
    public static Client getInstance() {
        return client;
        
    }
    
    
    public HttpEntity<String> getRequest(String sessionCookie, String address) {
    
        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();
    
        // Data attached to the request.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
    
        return restTemplate.exchange(address, HttpMethod.GET, request, String.class);
    
    }
    
    
    public HttpEntity<String> postRequest(String sessionCookie, String address, MultiValueMap<String, Object> params) {
        
        HttpHeaders headers = setHeaders(sessionCookie);
        RestTemplate restTemplate = new RestTemplate();
        
        // Data attached to the request.
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        
        return restTemplate.exchange(address, HttpMethod.POST, request, String.class);
        
    }
    
    
    public User[] getUsers(String sessionCookie) {
        
        
        HttpEntity<String> result = getRequest(sessionCookie,"http://localhost:8080/allUsers");
        
        //System.out.println(result);
        
        ObjectMapper reader = new ObjectMapper();
        reader.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //List<User> a = reader.convertValue(result.getBody().replace("[", "").replace("]", ""), new TypeReference<List<User>>() {});
        
        //List<User> users = gson.fromJson(json, new TypeToken<List<User>>(){}.getType());
        
        User[] users = gson.fromJson(result.getBody(), User[].class);
        return users;
    }
    
    public static void main(String[] args) {
        
        Client client = new Client("", "http://localhost:8080");
        
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", "test");
        params.add("password", "test");
        
        String sessionCookie = client.postRequest("", "http://localhost:8080/login", params).getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
        
        System.out.println(client.getUsers(sessionCookie)[0].getEmail());
        
        
    }
    
}
