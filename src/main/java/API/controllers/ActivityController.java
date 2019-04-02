package API.controllers;


import API.UserService;
import com.google.gson.Gson;

import API.security.SecurityService;
import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;
import database.UserServiceImpl;

import database.entities.ActType;

import database.entities.Activity;

import database.entities.User;

import javafx.fxml.FXML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;

import org.springframework.web.bind.annotation.*;


import java.awt.Label;
import java.util.List;
import java.util.Set;


@RestController
public class ActivityController {
    
    
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
     * return the activities of a user.
     * @param sessionCookie sessionCookie.
     * @return set of activities.
     */
    @GetMapping("/activities")
    public @ResponseBody
    Set<Activity> getAllActivities(String sessionCookie) {
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        return activityRepository.findByUserIdSorted(user.getId());
    }
    
    
    
    /** adds an activity to the database.
     *
     * @param params parameter passed by client
     * @return activity actually added (proper id)
     */
    @PostMapping(path = "/addactivity")
    public @ResponseBody
    Activity addNewActivity(@RequestBody MultiValueMap<String,
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
            updateScoreAdd(act);
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
            updateScoreRemove(activity);
            activityRepository.delete(activity);
            return true;
        }
        
        return false;
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
     * returns the actTypes.
     * @return a list.
     */
    @GetMapping("/allActType")
    public @ResponseBody
    List getAllActType() {
        
        List co2Values = activityTypeRepository.findAllCo2SavingsSorted();
        return co2Values;
        
    }
    
    
}
