package API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.print.attribute.standard.Media;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class MainControllerTest {

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
    public void getUserByEmail() throws Exception{
        user.setEmail("erik@doe.rustig");
        String testEmail = user.getEmail();
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("email",gson.toJson(testEmail));     //not sure if testEmail needs to be converted to json.

        String requestBody = buildRequestBody(params);
        String responseBody = mvc.perform(
                post("/followFriend")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(requestBody).session(sessionCookie).params(params)
        )
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        User user2 = gson.fromJson(responseBody,User.class);
        Assert.assertEquals(user,user2);
    }

    /*
    @Test
    public void getAllUsers() throws Exception {

        Iterable<User> allUsers = userRepository.findAll();

        String responseBody =
            mvc.perform(
                get("/allUsers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .session(sessionCookie)
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
    */
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

        User user2 = gson.fromJson(responseBody,User.class);
        Assert.assertEquals(user.isSolarPanel(),user2.isSolarPanel());
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
        Achievement achievement = achievementRepository.findById(0).get();
        User dataUser = userRepository.findByEmail(user.getEmail());
        dataUser.setAchievements(new HashSet<Achievement>());
        dataUser.getAchievements().add(achievement);
        userRepository.save(dataUser);

        String responseBody =
                mvc.perform(
                        get("/getachievements")
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
        Set<Achievement> achievements = new HashSet<>(Arrays.asList(gson.fromJson(responseBody,Achievement[].class)));
        System.out.println(achievements.size());
        Assert.assertTrue(achievements.contains(achievement));



    }



    /*
    @Test
    public void deleteAll() throws Exception {

        userRepository.deleteAll();

        Iterable<User> allUsers = userRepository.findAll();

        String responseBody =
            mvc.perform(
                get("/allUsers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("sessionCookie","")
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
    */




}