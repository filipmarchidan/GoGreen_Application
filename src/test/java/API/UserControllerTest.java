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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    
    
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
    
    
    @Test
    public void addNewUser() throws Exception {
        
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("username","alice@gmail.com");
        map.add("password","test");
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
        System.out.println(responseBody);
        userRepository.delete(userRepository.findByEmail("alice@gmail.com"));
        if (responseBody.equals(responseBody)) {
            
            boolean bool = gson.fromJson(responseBody,boolean.class);
            Assert.assertEquals(bool,true);
            
        } else {
            fail();
        }
        
        
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
    
    @Test
    public void removeUser() throws  Exception {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(user));
        user.setAchievements(null);
        user.setPassword("" +
            "");
        userRepository.saveAndFlush(user);
        String requestBody = buildRequestBody(params);
        
        String responseBody =
            
            mvc.perform(
                
                post("/removeUser")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        User user = gson.fromJson(responseBody,User.class);
        
        assertNotNull(user);
        
        
    }
    @Test
    public void removeFakeUser() throws  Exception {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("user",gson.toJson(new User("random","stuff")));
        
        String requestBody = buildRequestBody(params);
        
        String responseBody =
            
            mvc.perform(
                
                post("/removeUser")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        User user = gson.fromJson(responseBody,User.class);
        assertNull(user);
        
        
    }
    
}
