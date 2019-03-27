package API;

import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;
import database.entities.Achievement;
import database.entities.Activity;
import database.entities.ActivityType;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller    // This means that this class is a Controller
@RestController
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data

    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private UserService userService;
    
    
    /** adds a new user to the database
     *
     * @param user user to be added
     * @return copy of user actually added (proper ID etc..)
     */
    @PostMapping(path = "/add") // Map ONLY GET Requests
    public @ResponseBody User addNewUser(@RequestBody User user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        if (userRepository.findByEmail(user.getEmail()) != user) {
            return userRepository.save(user);
        }
        return userRepository.findByEmail(user.getEmail());
        // if(userRepository.findByEmail(user.getEmail()) != null) {
        
        // }
        //  throw new ResponseStatusException(HttpStatus.BAD_REQUEST);


    }
    
    /** Finds the user and removes if present.
     *
     * @param user User to be removed.
     * @return the removed user OR null if no user was found
     */
    @PostMapping(path = "/removeUser")
    public @ResponseBody User removeUser(@RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            
            userRepository.delete(optionalUser.get());
            return optionalUser.get();
        }
        return null;
    }

    /** Finds the friends of a user
     *
     */
    @PostMapping(path = "/getFriends")
    public @ResponseBody Set<User> getFriends(@RequestBody int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return userRepository.getFriendsfromUser(id);
        }
        return null;
    }

    /** adds an activity to the database.
     *
     * @param activity activity to be added
     * @return activity actually added (proper id)
     */
    @PostMapping(path = "/addactivity")
    public @ResponseBody Activity addNewActivity(@RequestBody Activity activity) {

        Activity act = activityRepository.save(activity);
        checkAchievements(act);
        updateScore(act);
        return act;

    }

    private void checkAchievements(Activity act) {
        List<Activity> activityList = activityRepository.findByUserId(act.getUserId());

        if (activityList.size() >= 1 ) {

            Set<Achievement> achievements =
                    achievementRepository.getAchievementsFromUserId(act.getUserId());

            Optional<Achievement> optionalAchievement = achievementRepository.findById(0);

            if (optionalAchievement.isPresent()) {

                Achievement achievement = optionalAchievement.get();
                Optional<User> user = userRepository.findById(act.getUserId());

                if (user.isPresent()) {

                    User user1 = user.get();
                    user1.getAchievements().add(achievement);
                    achievement.getUsers().add(user1);
                    achievementRepository.save(achievement);
                    userRepository.save(user1);
                }
            }



        }
    }

    private void updateScore (Activity activity) {
        ActivityType activityType = activityTypeRepository.findById(activity.getActivity_type().ordinal()).get();
        User user = userRepository.findById(activity.getUserId()).get();
        user.setTotalscore(user.getTotalscore()+ activityType.getCo2_savings()*activity.getActivity_amount());
        userRepository.save(user);
    }

    @PostMapping(path = "/removeactivity")
    public @ResponseBody boolean removeActivity(@RequestBody Activity activity) {
        int id = activity.getId();
        if (activityRepository.existsById(id)){
            activityRepository.delete(activity);
            return true;
        }

        return false;
    }
    
    /** This is what the client can connect to, to retrieve a user's achievements.
     *
     * @param user the user whose achievements you want
     * @return  a set of all the achievement that user earned
     */
    @PostMapping(path = "/getachievements")
    public @ResponseBody Achievement[] getAchievements(@RequestBody User user) {

        Set<Achievement> achievements =
                achievementRepository.getAchievementsFromUserId(user.getId());
        
        return (Achievement[])achievements.toArray();

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

    /** adds a friend to the user
     *
     * @param user the user who adds a friend
     * @param email the email of the friend
     * @return the user who followed a friend
     */
    @PostMapping(path = "/followFriend")
    public @ResponseBody User followFriend(@RequestBody User user, String email) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User optionalfriend = userService.getUserByEmail(email);

        if (optionalfriend != null) {
            User friend = optionalfriend;
            Set<User> friends = userRepository.getFriendsfromUser(user.getId());
            friends.add(friend);
            return friend;
        }
        return null;
    }

    /** removes a friend from the user
     *
     * @param user the user who removes a friend
     * @param email email of the friend to remove
     * @return User friend friend of the user that was removed
     * @return null when user doesn't follow this friend
     */
    @PostMapping(path="/unfollowFriend")
    public @ResponseBody User unfollowFriend(@RequestBody User user, String email) {
        //Optional<User> optionalUser = userRepository.findById(user.getId());
        User optionalfriend = userService.getUserByEmail(email);

        if (optionalfriend != null) {

            Set<User> friends = userRepository.getFriendsfromUser(user.getId());

            if (friends.contains(optionalfriend)) {

                friends.remove(optionalfriend);
                return optionalfriend;
            }

            System.out.println("Defaultuser doesn't follow this user");
        }
        return null;
    }


}