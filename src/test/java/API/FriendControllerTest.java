package API;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;
import database.entities.Activity;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class FriendControllerTest {
    
    
    MockHttpSession sessionCookie;
    
    User user;
    
    Gson gson = new Gson();
    
    @Autowired
    private MockMvc mvc;
    
    String date = Activity.getCurrentDateTimeString();
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    ActivityRepository activityRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    ActivityTypeRepository activityTypeRepository;
    
    @Autowired
    AchievementRepository achievementRepository;
    
    
    private String buildRequestBody(Object data) throws JsonProcessingException {
        
        return objectMapper.writer().writeValueAsString(data);
        
    }
    
    private Object getResponseBody(ResultActions resultActions, Class className) throws IOException {
        
        return objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            className
        );
        
    }
    
    @Before
    public void getUser() throws Exception {
        
        MvcResult responseBody2 = mvc.perform(
            get("/finduser")
                .contentType(MediaType.ALL)
                .session(sessionCookie)
        )
            .andExpect(
                status().isOk()
            ).andReturn();
        user = gson.fromJson(responseBody2.getResponse().getContentAsString(),User.class);
        
    }
    
    @Before
    public void logInUser() throws  Exception{
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("username","testaccount@test.com");
        map.add("password","testpassword");
        
        String requestBody = buildRequestBody(
            map
        );
        
        String responseBody = mvc.perform(
            post("/addUser")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    requestBody
                )
                .header("sessionCookie","")
                .params(map)
        )
            .andExpect(
                status().isOk()
            ).andReturn().getResponse().getContentAsString();
        
        MvcResult responseBody2 = mvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(
                    requestBody
                )
                .header("sessionCookie","")
                .params(map)
        )
            .andExpect(
                status().isOk()
            ).andReturn();
        
        sessionCookie = (MockHttpSession)responseBody2.getRequest().getSession();
    }
    
    @Test
    public void getFriends() throws  Exception {
        
        Set<User> users = userRepository.getFriendsfromUser(user.getId());
        
        String responseBody =
            mvc.perform(
                get("/getFriends")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(sessionCookie)
            )
                .andExpect(
                    status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        System.out.println(responseBody);
        Set<User> users2 = new HashSet<>(Arrays.asList(gson.fromJson(responseBody,User[].class)));
        
        Assert.assertEquals(users,users2);
        
        
    }
    
    @Test
    public void followUnfollowFriend() throws  Exception {
        
        User user1 = new User("email@blah","password");
        
        user1 = userRepository.save(user1);
        //System.out.println(user);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user1));
        
        String requestBody = buildRequestBody(params);
        
        String responseBody =
            
            mvc.perform(
                
                post("/followFriend")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        User user2 = gson.fromJson(responseBody,User.class);
        //System.out.println(user1);
        Assert.assertEquals(user1,user2);
        Set<User> friendsfromUser = userRepository.getFriendsfromUser(user.getId());
        
        for(User u : friendsfromUser) {
            System.out.println(u.getEmail());
        }
        
        
        
        String responseBody2 =
            
            mvc.perform(
                
                post("/unfollowFriend")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        userRepository.delete(userRepository.findByEmail("email@blah"));
        
    }
    @Test
    public void unfollowNonFriend() throws Exception {
        User user1 = new User("email@blah","password");
        user1 = userRepository.save(user1);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user1));
        String requestBody = buildRequestBody(params);
        String responseBody =
            
                mvc.perform(
                    
                        post("/unfollowFriend")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        User user2 = gson.fromJson(responseBody,User.class);
        Assert.assertEquals(null,user2);
        userRepository.delete(userRepository.findByEmail("email@blah"));
    }
}
