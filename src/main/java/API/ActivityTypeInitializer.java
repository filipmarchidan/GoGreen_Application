package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import database.ActivityTypeRepository;
import database.entities.ActivityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.annotation.PostConstruct;

@Component
public class ActivityTypeInitializer {

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @PostConstruct
    private void init() {
        Gson gson = new Gson();
        System.out.println("LOADING CO2 DATA");
        try {
            
            FileReader fileReader = new FileReader("src/main/resources/jsonFiles/CO2_DATA.json");
            fileReader.toString();
            JsonReader jsonReader = new JsonReader(fileReader);
            ActivityType[] activityTypes = gson.fromJson(jsonReader,ActivityType[].class);
            System.out.println("HELLO?!");
            
            for (ActivityType activityType : activityTypes) {
                activityTypeRepository.save(activityType);
            }
        } catch (FileNotFoundException e) {
            //do nothing
            System.out.println("COULD NOT FIND CO2 DATA FILE!");
        }
    }
}
