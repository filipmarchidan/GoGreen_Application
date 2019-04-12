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
    
    
    @GetMapping("/getDaysOfSolarPanel")
    public @ResponseBody Integer getDaysOfSolarPanel(String sessionCookie){
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);
        int user_id = user.getId();
        Activity act =  activityRepository.findSolarActivityFromUserId(user_id);
        
        //added a check to make sure the activity itself was not null (this can happen)
        //if it happens the amount is just 0:
        if(act == null) {
            return 0;
        }
        
        Integer amount =  act.getActivity_amount();
        //removed the if amount == null check because intellij said it would never happen (it's an int, not integer so would default to 0 not null )
        if(amount <= 0){
            amount = 0;
        }
        return amount;
    }
    
    
    
    /** adds an activity to the database.
     *
     * @param params parameter passed by client
     * @return activity actually added (proper id)
     */
    @PostMapping(path = "/addactivity")
    public @ResponseBody Activity addNewActivity(
            @RequestBody MultiValueMap<String, Object> params) {
        
        String email = SecurityService.findLoggedInEmail();
        User user = userRepository.findByEmail(email);

        Activity activity = gson.fromJson((String)params.getFirst("activity"),Activity.class);
        activity.setUser(user);
        
        if (activity.getActivity_type() == ActType.solar_panel) {
            //should not add solar panel activities directly!
            return null;
        }
        Activity act = activityRepository.save(activity);
        checkAchievements(user);
        checkActivityAchievements(act, user);
        return act;
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

    /**
     * updates solar panels.
     * @param params params.
     * @return User.
     */
    @PostMapping(path = "/updateSolar")
    public @ResponseBody Activity updateSolar(@RequestBody MultiValueMap<String, Object> params) {
        
        User user = gson.fromJson((String)params.getFirst("user"),User.class);
        String email = SecurityService.findLoggedInEmail();
        User user1 = userRepository.findByEmail(email);
        user1.setSolarPanel(user.isSolarPanel());
        
        Activity activity = activityRepository.findSolarActivityFromUserId(user.getId());
        System.out.println(activity + "hi1");
        if(activity == null) {
            
            activity = new Activity(ActType.solar_panel,0,Activity.getCurrentDateTimeString());
            activity.setUser(user1);
            activityRepository.save(activity);
            user1.getActivities().add(activity);
            
        }
        userRepository.save(user1);
        System.out.println(activity + " hi");
        return activity;
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
     * checks if a user gained an achievement.
     * @param user user
     */
    private void checkAchievements(User user) {
        Achievement achievement = null;
    
        if (user.getTotalscore() >= 100000) {
            achievement = achievementRepository.findById(1).get();
        }
        if (user.getTotalscore() >= 500000) {
            achievement = achievementRepository.findById(2).get();
        }
        if (user.getTotalscore() >= 1000000) {
            achievement = achievementRepository.findById(3).get();
        }
        if (achievement != null) {
            user.getAchievements().add(achievement);
            achievement.getUsers().add(user);
            achievementRepository.save(achievement);
        }
    
        userRepository.save(user);
    }
    
    private void checkActivityAchievements(Activity activity, User user) {
        List<Activity> activityList = activityRepository.findByUserId(user.getId());
        
        Achievement achievement = null;
        
        switch (activity.getActivity_type()) {
            
            case vegetarian_meal:
                achievement = achievementRepository.findById(5).get();
                break;
            
            case bike:
                achievement = achievementRepository.findById(6).get();
                break;
            
            case solar_panel:
                achievement = achievementRepository.findById(4).get();
                break;
            
            case local_produce:
                achievement = achievementRepository.findById(9).get();
                break;
            
            case public_transport:
                achievement = achievementRepository.findById(7).get();
                break;
            
            case lower_temperature:
                achievement = achievementRepository.findById(8).get();
                break;
            default:
                return;
            
        }
        
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
