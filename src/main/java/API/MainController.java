package API;

import com.google.gson.Gson;

import API.security.SecurityService;
import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;
import database.UserServiceImpl;
import database.entities.Achievement;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.ActivityType;
import database.entities.User;

import javafx.fxml.FXML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.awt.Label;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;







@RestController
public class MainController {
    
    
    Gson gson = new Gson();
    
    
    @FXML
    private Label registrationStatus;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @Bean
    public BCryptPasswordEncoder appPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    /**
     * This pass allows to add a new User.
     * @param params multiValueMap that contains the attributes of the user.
     * @return a response message.
     */
    @PostMapping(path = "/addUser")
    public @ResponseBody boolean addNewUser(@RequestBody MultiValueMap<String, Object> params) {
        System.out.println(params);
        User user = new User();
        user.setEmail((String)params.getFirst("username"));
        String password = (String)params.getFirst("password");
        System.out.println(user.getEmail());
        String hashedPassword = encoder.encode(password);
        user.setPassword(hashedPassword);
       
        if (userServiceImpl.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        userService.createUser(user);
        return true;
    }
    
    /**
     * find a user by theirs email.
     * @param email email
     * @return the user.
     */
    @GetMapping(path = "/findByEmail")
    public @ResponseBody User findByEmail(@RequestBody String email) {
        return userService.getUserByEmail(email);
    }
    
    /**
     * find the logged in  user.
     * @return the logged in user.
     */
    @GetMapping(path = "/finduser")
    public @ResponseBody User findUser() {
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        return  user;
    }
    
    
    /**
     * retrieves a list of all users.
     * @return a list of all users.
     */
    
    @GetMapping("/allUsers")
    public @ResponseBody Set<User> getAllUsers() {
        
        return userRepository.findUsersSimple();
        
    }
    
    /** Finds the user and removes if present.
     *
     * @param params User to be removed.
     * @return the removed user OR null if no user was found
     */
    @PostMapping(path = "/removeUser")
    public @ResponseBody User removeUser(@RequestBody MultiValueMap<String, Object> params) {
        User user = gson.fromJson((String)params.getFirst("user"),User.class);
        System.out.println(user.getEmail());
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User user1 = optionalUser.get();
            for (Activity activity : user1.getActivities()) {
                activityRepository.deleteAll(user1.getActivities());
            }
            System.out.println("removing user");
            user1.setActivities(new HashSet<Activity>());
            userRepository.delete(user1);
            return optionalUser.get();
        }
        return null;
    }

    /** Finds the friends of a user.
     *
     */
    @GetMapping(path = "/getFriends")
    public @ResponseBody Set<User> getFriends() {
        
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        return userRepository.getFriendsfromUser(user.getId());
        
    }
    
    /** adds an activity to the database.
     *
     * @param params parameter passed by client
     * @return activity actually added (proper id)
     */
    @PostMapping(path = "/addactivity")
    public @ResponseBody Activity addNewActivity(@RequestBody MultiValueMap<String,
        Object> params) {
        
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        
        //System.out.println((String)params.getFirst("activity"));
        Activity activity = gson.fromJson((String)params.getFirst("activity"),Activity.class);
        System.out.println(activity.getActivity_type());
        activity.setUser(user);
        
        if (activity.getActivity_type() != ActType.solar_panel) {
            //System.out.println("HELLO");
            Activity act = activityRepository.save(activity);
            checkAchievements(act);
            return act;

        } else {

            if (!user.isSolarPanel()) {

                user.setSolarPanel(true);
                Activity act = activityRepository.save(activity);
                return act;
            }
            try {
                return (Activity) activityRepository.findSolarActivityFromUserId(user.getId())
                    .toArray()[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Testing descepancy");
                return activity;
            }
        }
    }

    private void checkAchievements(Activity act) {
        List<Activity> activityList = activityRepository.findByUserId(act.getUser().getId());
        
        Set<Achievement> achievements =
                    achievementRepository.getAchievementsFromUserId(act.getUser().getId());

        Achievement achievement = achievementRepository.findById(0).get();
        
        User user = userRepository.findById(act.getUser().getId()).get();
        user.getAchievements().add(achievement);
        achievement.getUsers().add(user);
        achievementRepository.save(achievement);
        userRepository.save(user);
    }
    
    
    /**
     * updates solar panels.
     * @param params params.
     * @return User.
     */
    @PostMapping(path = "/updateSolar")
    public @ResponseBody User updateSolar(@RequestBody MultiValueMap<String, Object> params) {
        
        User user = gson.fromJson((String)params.getFirst("user"),User.class);
        String email = SecurityService.findLoggedInEmail();
        User user1 = userRepository.findByEmail(email);
        user1.setSolarPanel(user.isSolarPanel());
        return userRepository.save(user1);
    
    }
    
    /**
     * removes activit.
     * @param params params.
     * @return boolean.
     */
    @PostMapping(path = "/removeactivity")
    public @ResponseBody boolean removeActivity(@RequestBody MultiValueMap<String, Object> params) {
        
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
    
        System.out.println((String)params.getFirst("activity"));
        Activity activity = gson.fromJson((String)params.getFirst("activity"),Activity.class);
        if (activity.getActivity_type() == ActType.solar_panel) {
            return false;
        }
        activity.setUser(user);
        
        if (activityRepository.existsById(activity.getId())) {
            activityRepository.delete(activity);
            return true;
        }

        return false;
    }

    /** This is what the client can connect to, to retrieve a user's achievements.
     *
     * @return  a set of all the achievement that user earned
     */
    @GetMapping(path = "/getachievements")
    public @ResponseBody Set<Achievement> getAchievements() {
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        Set<Achievement> achievements =
                achievementRepository.getAchievementsFromUserId(user.getId());
        return achievements;

    }
    
    
    /**
     * return the activities of a user.
     * @param sessionCookie sessionCookie.
     * @return set of activities.
     */
    @GetMapping("/activities")
    public @ResponseBody Set<Activity> getAllActivities(String sessionCookie) {
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        return activityRepository.findByUserIdSorted(user.getId());
    }
    
    
    /**
     * returns the actTypes.
     * @return a list.
     */
    @GetMapping("/allActType")
    public @ResponseBody List getAllActType() {

        List co2Values = activityTypeRepository.findAllCo2SavingsSorted();
        return co2Values;

    }
    /*
        The next methods are created via UserServiceDAO
        The user service has the following methods
        createUser - creates a new user
        getUserById - retrieves the user by id
        getAllUsers - retrieves all users from database
        deleteUser - deletes an user in the database
        updateSolar - updates a current user in the database
        */
    /*
    //creates new user
    @CrossOrigin
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    //gets the user by id
    @CrossOrigin
    @GetMapping(value = "/userId/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable("ticketId")Integer userId) {
        return userService.getUserById(userId);
    }
    */
    
    /*
    //gets all users
    @CrossOrigin
    @GetMapping(value = "/allUsers",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
    */

    /*
    //gets user by email
    @CrossOrigin
    @GetMapping(value = "/email/{email:.+}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserByEmail(@PathVariable("email")String email) {
        return userService.getUserByEmail(email);
    }
    
    //deletes user by id
    @CrossOrigin
    @DeleteMapping(value = "/userId/{userId}")
    public void deleteUser(@PathVariable("userId")Integer userId) {
        userService.deleteUser(userId);
    }
    
    //updates the user
    @CrossOrigin
    @PutMapping(value = "/userId/{userId}/email/{newEmail:.+}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateSolar(@PathVariable("userId")Integer userId,
                           @PathVariable("newEmail")String newEmail,
                           @PathVariable("newPassword") String newPassword) {
        
        return userService.updateSolar(newEmail, newPassword, userId);
    }
    */

    /**
     * adds a friend to the user.
     *
     * @param params user to be friended
     * @return the user who followed a friend
     */
    @PostMapping(path = "/followFriend")
    public @ResponseBody User followFriend(@RequestBody MultiValueMap<String, Object> params) {
        
        User other = gson.fromJson((String)params.getFirst("user"),User.class);
        other = userRepository.findByEmail(other.getEmail());
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        user.getFriends().add(other);
        userService.createUser(user);
        return other;
    }

    /**
     * removes a friend from the user.
     *
     * @param params friend to be removed
     * @return User friend friend of the user that was removed
     */
    @PostMapping(path = "/unfollowFriend")
    public @ResponseBody User unfollowFriend(@RequestBody MultiValueMap<String, Object> params) {
        
        User other = gson.fromJson((String)params.getFirst("user"),User.class);
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        if (user.getFriends().contains(other)) {
            user.getFriends().remove(other);
            userService.createUser(user);
            return other;
        }
        System.out.println("Defaultuser doesn't follow this user");
        return null;
    }


}