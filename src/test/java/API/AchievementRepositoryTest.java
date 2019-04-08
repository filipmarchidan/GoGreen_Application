package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import database.AchievementRepository;
import database.UserRepository;
import database.entities.Achievement;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AchievementRepositoryTest {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AchievementRepository achievementRepository;
    
    @Before
    public void before(){
        
        Gson gson = new Gson();
        System.out.println("LOADING ACHIEVEMENT DATA");
        try {
            JsonReader jsonReader = new JsonReader(new FileReader("src/main/resources/jsonFiles/ACHIEVEMENT_DATA.json"));
            Achievement[] achievements = gson.fromJson(jsonReader, Achievement[].class);
            System.out.println("HELLO?!");
            for(Achievement achievement : achievements) {
                achievementRepository.save(achievement);
            }
        }
        catch(FileNotFoundException e){
            //do nothing
            System.out.println("COULD NOT FIND CO2 DATA FILE!");
        }
    }
    
    @Test
    public void getAchievementsFromUser() {
        User user = new User("blah@blah.com","supersecretpassword");
        user.setId(1);
        User user1 = userRepository.save(user);
        Set<Achievement> achievements = new HashSet<>();
        achievements.add(achievementRepository.findById(0).get());
        user1.setAchievements(achievements);
        Achievement achievement = achievementRepository.findById(0).get();
        achievement.getUsers().add(user1);
        achievementRepository.save(achievement);
        userRepository.save(user1);
        Set<Achievement> achievements1 = achievementRepository.getAchievementsFromUserId(user1.getId());
        Assert.assertTrue(achievements1.size() == 1);
        
    }
}
