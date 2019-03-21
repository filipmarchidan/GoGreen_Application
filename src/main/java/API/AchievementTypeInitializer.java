package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import database.AchievementTypeRepository;
import database.ActivityTypeRepository;
import database.entities.AchievementType;
import database.entities.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class AchievementTypeInitializer {

    @Autowired
    private AchievementTypeRepository achievementTypeRepository;

    @PostConstruct
    private void init(){
        Gson gson = new Gson();
        System.out.println("LOADING ACHIEVEMENT DATA");
        try {
            JsonReader jsonReader = new JsonReader(new FileReader("ACHIEVEMENT_DATA.json"));
            AchievementType[] achievementTypes = gson.fromJson(jsonReader,AchievementType[].class);
            System.out.println("HELLO?!");
            for(AchievementType achievementType : achievementTypes) {
                achievementTypeRepository.save(achievementType);
            }
        }
        catch(FileNotFoundException e){
            //do nothing
            System.out.println("COULD NOT FIND CO2 DATA FILE!");
        }
    }
}
