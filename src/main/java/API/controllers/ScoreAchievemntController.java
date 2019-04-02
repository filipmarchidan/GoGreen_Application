package API.controllers;

import API.security.SecurityService;
import database.AchievementRepository;
import database.ActivityRepository;
import database.ActivityTypeRepository;
import database.UserRepository;
import database.entities.Achievement;
import database.entities.Activity;
import database.entities.ActivityType;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ScoreAchievemntController {
    
    
    
    @Autowired
    private ActivityTypeRepository activityTypeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private AchievementRepository achievementRepository;
    
    
    
    
    
    private void updateScoreAdd(Activity activity) {
        //System.out.println(activity.getActivity_amount());
        //System.out.println("we get here");
        ActivityType activityType = activityTypeRepository.findById(activity.getActivity_type()
            .ordinal()).get();
        User user = userRepository.findByEmail(activity.getUser().getEmail());
        user.setTotalscore(user.getTotalscore() + activityType.getCo2_savings() * activity
            .getActivity_amount());
        userRepository.save(user);
    }
    
    private void updateScoreRemove(Activity activity) {
        ActivityType activityType = activityTypeRepository.findById(activity.getActivity_type()
            .ordinal()).get();
        User user = userRepository.findById(activity.getUser().getId()).get();
        user.setTotalscore(user.getTotalscore() - activityType.getCo2_savings() * activity
            .getActivity_amount());
        userRepository.save(user);
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
