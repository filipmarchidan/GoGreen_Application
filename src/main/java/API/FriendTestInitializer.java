package API;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import database.UserRepository;
import database.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.annotation.PostConstruct;

@Component
public class FriendTestInitializer {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void init() {
        Gson gson = new Gson();
        System.out.println("LOADING FRIEND DATA");
        try {
            FileReader fileReader = new FileReader("src/main/resources/jsonFiles/FRIEND_INFO.json");
            JsonReader jsonReader = new JsonReader(fileReader);
            User[] users = gson.fromJson(jsonReader,User[].class);

            User user = userRepository.findByEmail(users[2].getEmail());
            if (user != null) {
                userRepository.delete(user);
            }
            
            for (int i = 0; i < users.length;i++) {
                System.out.println(users[i].getEmail());
                if (userRepository.findByEmail(users[i].getEmail()) == null) {
                    users[i] = userRepository.save(users[i]);
                }
                

            }


        } catch (FileNotFoundException e) {
            //do nothing
            System.out.println("COULD NOT FIND USER FRIEND DATA FILE!");
        }
    }
}
