package API.controllers;

import API.UserService;
import API.security.SecurityService;
import com.google.gson.Gson;
import database.UserRepository;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {
    
    
    private Gson gson;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    
    
    
    /**
     * adds a friend to the user.
     *
     * @param params user to be friended
     * @return the user who followed a friend
     */
    @PostMapping(path = "/followFriend")
    public @ResponseBody
    User followFriend(@RequestBody MultiValueMap<String, Object> params) {
        
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
