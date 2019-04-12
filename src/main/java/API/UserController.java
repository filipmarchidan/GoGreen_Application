package API;

import com.google.gson.Gson;

import API.security.SecurityService;


import database.ActivityRepository;

import database.UserServiceImpl;
import database.entities.Activity;
import database.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {
    
    Gson gson = new Gson();
    
    
   
    
    @Autowired
    private database.UserRepository userRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    
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
    public @ResponseBody
        boolean addNewUser(@RequestBody MultiValueMap<String, Object> params) {
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
     * @param params email
     * @return the user.
     */
    @PostMapping(path = "/findByEmail")
    public @ResponseBody User findByEmail(@RequestBody MultiValueMap<String, Object> params) {
        String  email = gson.fromJson((String)params.getFirst("email"),String.class);
        User user = userRepository.findByEmail(email);
        return user;
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
    public @ResponseBody
        Set<User> getAllUsers() {
        
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
}
