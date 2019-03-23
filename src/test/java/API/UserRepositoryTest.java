package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import database.AchievementRepository;
import database.ActivityRepository;
import database.UserRepository;
import database.entities.Achievement;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
        private static final String email1 = "Spongebob";
        private static final String pass1 = "12345";
        private static final String email2 = "Patrick";
        private static final String pass2 = "1234";
        
        
        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private AchievementRepository achievementRepository;
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ActivityRepository activityRepository;
        
        @Before
        public void before() {
            Gson gson = new Gson();
            System.out.println("LOADING ACHIEVEMENT DATA");
            try {
                JsonReader jsonReader = new JsonReader(new FileReader("ACHIEVEMENT_DATA.json"));
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
        public void checkDatabaseFunctionality(){
           User user = getOneUser();
           User saveData = entityManager.persist(user);
           User getData = userRepository.findById(saveData.getId()).get();
           assertThat(getData).isEqualTo(saveData);
        }

        @Test
        public void checkFindByEmailFunctionality()
        {
            User user = getOneUser();
            User saveData = entityManager.persist(user);
            List<User> getUser = userRepository.findByEmail(email1);
            assertEquals(saveData.getEmail(), getUser.get(0).getEmail());
        }
        private User getOneUser(){
            User spongeBob = new User();
            spongeBob.setEmail(email1);
            spongeBob.setPassword(pass1);
            return spongeBob;
        }
    @Test
    public void getAchievementsFromUser() {
        User user = new User("blah@blah.com","supersecretpassword");
        User user1 = userRepository.save(user);
        Set<Achievement> achievements = new HashSet<>();
        achievements.add(achievementRepository.findById(0).get());
        user1.setAchievements(achievements);
        userRepository.save(user1);
        Set<Achievement> achievements1 = userRepository.getAchievementsFromUserId(user1.getId());
        Assert.assertTrue(achievements1.size() == 1);
        
        
    }
}
