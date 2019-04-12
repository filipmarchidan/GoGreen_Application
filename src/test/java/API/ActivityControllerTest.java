package API;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;
import database.entities.Achievement;
import database.entities.ActType;
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
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc

public class ActivityControllerTest {
    
    
    
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
    
    @Autowired
    UserService userService;
    
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
    public void addNewActivity() throws Exception {
        
        Activity activity = new Activity(ActType.vegetarian_meal,1,date);
        activity.setActivity_type(ActType.vegetarian_meal);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
            
            mvc.perform(
                
                post("/addactivity")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()),
            
            Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void addNewBikeActivity() throws Exception {
        
        Activity activity = new Activity(ActType.bike,1,date);
        activity.setActivity_type(ActType.bike);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void addNewLocalActivity() throws Exception {
        
        Activity activity = new Activity(ActType.local_produce,1,date);
        activity.setActivity_type(ActType.local_produce);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void addNewTransportActivity() throws Exception {
        
        Activity activity = new Activity(ActType.public_transport,1,date);
        activity.setActivity_type(ActType.public_transport);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void addNewTemperatureActivity() throws Exception {
        
        Activity activity = new Activity(ActType.lower_temperature,1,date);
        activity.setActivity_type(ActType.lower_temperature);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void addNewSolarPanel() throws Exception {
        
        Activity activity = new Activity(ActType.solar_panel,1,date);
        activity.setActivity_type(ActType.solar_panel);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        String responseBody =
            
            mvc.perform(
                
                post("/addactivity")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        
        System.out.println(responseBody +  " HELLO");
        Activity returnedActivity = gson.fromJson(responseBody,Activity.class);
        assertEquals(null,returnedActivity);
        
    }
    
    @Test
    public void addNewTestActivity() throws Exception {
        
        Activity activity = new Activity(ActType.TEST_DEFAULT,1,date);
        activity.setActivity_type(ActType.TEST_DEFAULT);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        String responseBody =
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        
        System.out.println(responseBody +  " HELLO");
        Activity returnedActivity = gson.fromJson(responseBody,Activity.class);
        assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
        assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
        
    }
    
    
    @Test
    public void getAllActivities() throws Exception {
        Set<Activity> activities = activityRepository.findByUserIdSorted(user.getId());
        
        String responseBody =
            mvc.perform(
                get("/activities")
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
        Set<Activity> activities2 = new HashSet<>(Arrays.asList(gson.fromJson(responseBody,Activity[].class)));
        
        Assert.assertEquals(activities,activities2);
        
    }
    
    @Test
    public void checkScoreAchievementBronze() throws Exception {
        User user1 = userRepository.findByEmail("testaccount@test.com");
        user1.setTotalscore(99999);
        userService.createUser(user1);
        Activity activity = new Activity(ActType.lower_temperature,1,date);
        activity.setActivity_type(ActType.lower_temperature);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void checkScoreAchievementSilver() throws Exception {
        User user1 = userRepository.findByEmail("testaccount@test.com");
        user1.setTotalscore(499999);
        userService.createUser(user1);
        Activity activity = new Activity(ActType.lower_temperature,1,date);
        activity.setActivity_type(ActType.lower_temperature);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    @Test
    public void checkScoreAchievementGold() throws Exception {
        User user1 = userRepository.findByEmail("testaccount@test.com");
        user1.setTotalscore(999999);
        userService.createUser(user1);
        Activity activity = new Activity(ActType.lower_temperature,1,date);
        activity.setActivity_type(ActType.lower_temperature);
        activity.setDate_time(Activity.getCurrentDateTimeString());
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/addactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Activity.class
        );
        
        
        if (responseBody instanceof Activity) {
            
            Activity returnedActivity = (Activity) responseBody;
            assertEquals(returnedActivity.getActivity_type(), activity.getActivity_type());
            assertEquals(returnedActivity.getDate_time(), activity.getDate_time());
            
        } else {
            fail();
        }
    }
    
    
    
    @Test
    public void switchSolar() throws Exception {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        user.setSolarPanel(!user.isSolarPanel());
        params.add("user",gson.toJson(user));
        
        String requestBody = buildRequestBody(params);
        
        String responseBody =
            
            mvc.perform(
                
                post("/updateSolar")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        
        Activity activity = gson.fromJson(responseBody,Activity.class);
        Assert.assertEquals(ActType.solar_panel,activity.getActivity_type());
    }
    
    
    
    
    @Test
    public void removeActivity() throws Exception {
        Activity activity = new Activity(ActType.vegetarian_meal,1,Activity.getCurrentDateTimeString());
        activity.setUser(user);
        activity = activityRepository.save(activity);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
            
            mvc.perform(
                
                post("/removeactivity")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()),
            
            Boolean.class
        );
        
        
        if (responseBody instanceof Boolean) {
            Assert.assertTrue((boolean)responseBody);
            
        } else {
            fail();
        }
        
    }
    
    @Test
    public void removeFalseActivity() throws Exception {
        Activity activity = new Activity(ActType.vegetarian_meal,1,Activity.getCurrentDateTimeString());
        activity.setUser(user);
        activity.setId(-1);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
                
                mvc.perform(
                        
                        post("/removeactivity")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(requestBody).session(sessionCookie).params(params)
                )
                        .andExpect(status().isOk()),
                
                Boolean.class
        );
        
        
        if (responseBody instanceof Boolean) {
            Assert.assertFalse((boolean)responseBody);
            
        } else {
            fail();
        }
        
    }
    
    @Test
    public void removeSolarPanel() throws Exception {
        Activity activity = new Activity(ActType.solar_panel,1,Activity.getCurrentDateTimeString());
        activity.setUser(user);
        activity = activityRepository.save(activity);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("activity",gson.toJson(activity));
        String requestBody = buildRequestBody(params);
        
        Object responseBody = getResponseBody(
            
            mvc.perform(
                
                post("/removeactivity")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(requestBody).session(sessionCookie).params(params)
            )
                .andExpect(status().isOk()),
            
            Boolean.class
        );
        
        
        if (responseBody instanceof Boolean) {
            Assert.assertFalse((boolean)responseBody);
            
        } else {
            fail();
        }
        
    }
    
    @Test
    public void getActTypeValues() throws Exception {
        List<Integer> list = activityTypeRepository.findAllCo2SavingsSorted();
        Set<Integer> set = new HashSet<>(list);
        MvcResult responseBody2 = mvc.perform(
            get("/allActType")
                .contentType(MediaType.ALL)
                .session(sessionCookie)
        )
            .andExpect(
                status().isOk()
            ).andReturn();
        List<Integer> co2Values = Arrays.asList(gson.fromJson(responseBody2.getResponse().getContentAsString(),Integer[].class));
        Set<Integer> co2set = new HashSet<>(co2Values);
        Assert.assertTrue(set.equals(co2set));
    }
    
    @Test
    public void getUserAchievements() throws Exception {
        Achievement achievement = achievementRepository.findById(1).get();
        User dataUser = userRepository.findByEmail(user.getEmail());
        dataUser.setAchievements(new HashSet<Achievement>());
        dataUser.getAchievements().add(achievement);

        String responseBody =
                mvc.perform(
                        get("/getAchievements")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .session(sessionCookie)
                )
                        .andExpect(
                    status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        System.out.println(responseBody);
        Set<Achievement> achievements = new HashSet<>(Arrays.asList(gson.fromJson(responseBody,Achievement[].class)));
        System.out.println(achievements.size());
        Assert.assertTrue(achievements.contains(achievement));
    }
    
    @Test
    public void getDaysOfSolarPanelTest() throws Exception {
        String responseBody =
                mvc.perform(
                        get("/getDaysOfSolarPanel")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .session(sessionCookie)
                )
                        .andExpect(
                                status().isOk()
                        )
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        
    }
    
    @Test
    public void getDaysOfSolarPanelTestNotNull() throws Exception {
        
        String responseBody =
                mvc.perform(
                        get("/getDaysOfSolarPanel")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .session(sessionCookie)
                )
                        .andExpect(
                                status().isOk()
                        )
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        
    }
    
}
