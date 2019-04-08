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


/**
 * This class is a Component for spring, because of that it automatically gets run by spring when the server boots up.
 * This class is responsible for loading test user data whenever the server loads up, it will be removed/disabled before
 * production.
 */
@Component
public class FriendTestInitializer {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    private void init() {
        Gson gson = new Gson();
        System.out.println("LOADING FRIEND DATA");
        try {

            //FileReader fileReader = new FileReader("FRIEND_INFO.json");
            //fileReader.toString();
            JsonReader jsonReader = new JsonReader(new FileReader("FRIEND_INFO.json"));
            User[] users = gson.fromJson(jsonReader,User[].class);


            for (int i = 0; i < users.length;i++) {
                System.out.println(users[i].getEmail());
                userRepository.save(users[i]);

            }
            User user = userRepository.findByEmail(users[0].getEmail());
            user.getFriends().add(users[1]);
            user.getFriends().add(users[2]);
            userRepository.save(user);


        } catch (FileNotFoundException e) {
            //do nothing
            System.out.println("COULD NOT FIND USER FRIEND DATA FILE!");
        }
    }
}
