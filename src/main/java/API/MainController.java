package API;

import database.ActivityRepository;
import database.UserRepository;
import database.entities.Activity;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller    // This means that this class is a Controller
@RestController
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data

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
    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(@RequestBody User user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        // if(userRepository.findByEmail(user.getEmail()) != null) {
        return userService.createUser(user);
        // }
        //  throw new ResponseStatusException(HttpStatus.BAD_REQUEST);


    }
    @PostMapping(path = "/findByEmail")
    public @ResponseBody User findByEmail(@RequestBody String email)
    {
        return userService.getUserByEmail(email);
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
    
    @GetMapping(path = "/activities")
    public @ResponseBody Iterable<Activity> getAllActivities() {

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
    //gets all users
    @CrossOrigin
    @GetMapping(value = "/allUsers",produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

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
