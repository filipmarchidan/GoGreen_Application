package API;

import database.ActivityRepository;
import database.UserRepository;
import database.entities.Activity;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MainController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserService userService;
    
    
    /** adds a new user to the database
     *
     * @param user user to be added
     * @return copy of user actually added (proper ID etc..)
     */
    @PostMapping(path = "/addUser")
    public @ResponseBody User addNewUser(@RequestBody User user) {
//        User user = new User();
//        user.setPassword(password);
//        user.setEmail(email);
        return userService.createUser(user);
    }
    @GetMapping(path = "/findByEmail")
    public @ResponseBody List<User> findByEmail(@RequestBody String email){
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
    public @ResponseBody List<Activity> getAllActivities(String sessionCookie) {

        return activityRepository.findAll();
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
