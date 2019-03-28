package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.ActivityRepository;
import database.UserRepository;
import database.entities.Achievement;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class MainControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    ActivityRepository activityRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    
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
        
        User user = new User();
        user.setEmail("alice@gmail.com");
        user.setPassword("password");

    
        String requestBody = buildRequestBody(
            user
        );
        
        Object responseBody = getResponseBody(
            
            mvc.perform(
                    post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            requestBody
                        )
                )
            .andExpect(
                status().isOk()
            ),
            
            User.class
            
        );
        
        if (responseBody instanceof User) {
            
            User returnedUser = (User) responseBody;
        
            assertEquals(returnedUser.getEmail(), user.getEmail());
            assertEquals(returnedUser.getPassword(), user.getPassword());
    
        
        } else {
            fail();
        }
        
        
    
    }
    
    @Test
    public void getAllUsers() throws Exception {
        
        Iterable<User> allUsers = userRepository.findAll();
    
        String responseBody =
            mvc.perform(
                get("/allUsers")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                status().isOk()
            )
            .andReturn()
            .getResponse()
            .getContentAsString();
        
        Iterable<User> retrievedUsers = objectMapper.readValue(responseBody, new TypeReference<Iterable<User>>(){});
        
        assertEquals(
            allUsers,
            retrievedUsers
        );
      
    }
    
    @Test
    public void addNewActivity() throws Exception {
    
        Activity activity = new Activity(ActType.vegetarian_meal,1,Activity.getCurrentDateTimeString());
        activity.setActivity_type(ActType.vegetarian_meal);
        activity.setDate_time(Activity.getCurrentDateTimeString());

        String requestBody = buildRequestBody(activity);
        
        Object responseBody = getResponseBody(
            
            mvc.perform(
                
                post("/addactivity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
            )
            .andExpect(status().isOk()),
            
            Activity.class
        );
    
    
        if (responseBody instanceof Activity) {
        
            Activity returnedActivity = (Activity) responseBody;
        
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            assertEquals(returnedActivity.getUser().getId(), activity.getUser().getId());

        
        } else {
            fail();
        }
    
    }
    

    @Test
    public void getAllActivities() throws Exception {
    
    
        Iterable<Activity> allActivities = activityRepository.findAll();
    
        String responseBody =
            mvc.perform(
                get("/activities")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(
                    status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString();
    
        Iterable<Activity> retrievedActivities = objectMapper.readValue(responseBody, new TypeReference<Iterable<Activity>>(){});
    
        assertEquals(
            allActivities,
            retrievedActivities
        );
        
    }

    
    @Test
    public void deleteAll() throws Exception {
        
        userRepository.deleteAll();
    
        Iterable<User> allUsers = userRepository.findAll();
    
        String responseBody =
            mvc.perform(
                get("/allUsers")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(
                    status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString();
    
        Iterable<User> retrievedUsers = objectMapper.readValue(responseBody, new TypeReference<Iterable<User>>(){});
    
        assertEquals(
            allUsers,
            retrievedUsers
        );
    }
}