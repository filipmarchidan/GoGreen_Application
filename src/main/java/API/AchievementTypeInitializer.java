package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import database.AchievementRepository;
import database.entities.Achievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.annotation.PostConstruct;

/**
 * This class is a Component for spring, because of that it automatically gets run by spring when the server boots up.
 * This class is responsible for loading/updating the achievement information from a JSON file into the database every time the server boots up.
 */
@Component
public class AchievementTypeInitializer {
    
    @Autowired
    private AchievementRepository achievementRepository;
    
    @PostConstruct
    private void init() {
        Gson gson = new Gson();
        System.out.println("LOADING ACHIEVEMENT DATA");
        try {
            JsonReader jsonReader = new JsonReader(new FileReader("ACHIEVEMENT_DATA.json"));
            Achievement[] achievements = gson.fromJson(jsonReader, Achievement[].class);
            System.out.println("HELLO?!");
            
            for (Achievement achievement : achievements) {
                achievementRepository.save(achievement);
            }
            
        } catch (FileNotFoundException e) {
            //do nothing
            System.out.println("COULD NOT FIND CO2 DATA FILE!");
        }
    }
}
