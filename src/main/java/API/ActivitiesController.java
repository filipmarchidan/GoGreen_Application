package API;


import com.google.gson.Gson;

import API.security.SecurityService;
import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;

import database.entities.Achievement;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.ActivityType;
import database.entities.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 *  This class is a restController that gets initialized by spring
 *  It contains endpoints that can be called by the client.
 * '@Getmapping//@Postmapping' determine if an endpoint can be connected to be a HTTP GET or POST message.
 *  This controller takes care of all the activity parts, so get activity, add activity and remove activity
 */
@RestController
public class ActivitiesController {
    
    
    Gson gson = new Gson();
    
    
    
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    
    @Autowired
    private ActivityTypeRepository activityTypeRepository;
    
    @Autowired
    private AchievementRepository achievementRepository;
    
    
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
    
    
    /**
     * update the score.
     * @param activity activity
     */
    public  void updateScoreAdd(Activity activity) {
        //System.out.println(activity.getActivity_amount());
        //System.out.println("we get here");
        ActivityType activityType = activityTypeRepository.findById(activity.getActivity_type()
            .ordinal()).get();
        User user = userRepository.findByEmail(activity.getUser().getEmail());
        user.setTotalscore(user.getTotalscore() + activityType.getCo2_savings() * activity
            .getActivity_amount());
        userRepository.save(user);
    }
    
    /**\
     * update the score when removing an activity.
     * @param activity activity
     */
    public  void updateScoreRemove(Activity activity) {
        ActivityType activityType = activityTypeRepository.findById(activity.getActivity_type()
            .ordinal()).get();
        User user = userRepository.findById(activity.getUser().getId()).get();
        user.setTotalscore(user.getTotalscore() - activityType.getCo2_savings() * activity
            .getActivity_amount());
        userRepository.save(user);
    }
    
    
    /**
     * checks if a user gained an achievement.
     * @param act activity
     */
    public  void checkAchievements(Activity act) {
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
    
    /** This is what the client can connect to, to retrieve a user's achievements.
     *
     * @return  a set of all the achievement that user earned
     */
    @GetMapping(path = "/getachievements")
    public @ResponseBody
        Set<Achievement> getAchievements() {
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        Set<Achievement> achievements =
            achievementRepository.getAchievementsFromUserId(user.getId());
        return achievements;
        
    }
}