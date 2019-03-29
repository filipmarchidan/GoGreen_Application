package API;

import API.security.SecurityService;
import database.ActivityRepository;
import database.UserRepository;
import database.UserServiceImpl;
import database.entities.Activity;
import database.entities.User;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Set;



@RestController
public class MainController {
    @FXML
    private Label registrationStatus;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Bean
    public BCryptPasswordEncoder appPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping(path = "/addUser")
    public @ResponseBody String addNewUser(@RequestBody MultiValueMap<String, Object> params) {

        User user = new User();
        user.setEmail((String)params.getFirst("username"));
        String password = (String)params.getFirst("password");
        String hashedPassword = encoder.encode(password);
        user.setPassword(hashedPassword);
        String result = "User added successfully User[" + user.getEmail() + "] with password [" + password +"]";
        if(userServiceImpl.getUserByEmail(user.getEmail()) != null){
            System.out.println("User already exists and it couldn't be added");
            return "Failed to add user";}
        userService.createUser(user);
        return result;
    }
    @GetMapping(path = "/findByEmail")
    public @ResponseBody User findByEmail(@RequestBody String email){
        return userService.getUserByEmail(email);
    }
    
    @Secured("ROLE_USER")
    @GetMapping("/allUsers")
    public @ResponseBody List<User> getAllUsers(){
        
        return userRepository.findAll();
        
    }
    
    
    
    /** adds an activity to the database.
     *
     * @param activity activity to be added
     * @return activity actually added (proper id)
     */
    @PostMapping(path = "/addactivity")
    public @ResponseBody Activity addNewActivity(@RequestBody Activity activity) {
        Activity act = activityRepository.save(activity);
        return act;

    }
    
    @GetMapping("/activities")
    public @ResponseBody Set<Activity> getAllActivities(String sessionCookie) {

        
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        return user.getActivities();
    }
    /*
        The next methods are created via UserServiceDAO
        The user service has the following methods
        createUser - creates a new user
        getUserById - retrieves the user by id
        getAllUsers - retrieves all users from database
        deleteUser - deletes an user in the database
        updateUser - updates a current user in the database
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
    public User updateUser(@PathVariable("userId")Integer userId,
                           @PathVariable("newEmail")String newEmail,
                           @PathVariable("newPassword") String newPassword) {
        
        return userService.updateUser(newEmail, newPassword, userId);
    }
    */
}
