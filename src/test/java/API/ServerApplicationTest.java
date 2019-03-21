package API;

import com.google.gson.Gson;
import database.entities.ActType;
import database.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServerApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ServerApplicationTest{

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void all_returns_array() throws Exception {
        this.mockMvc.perform(get("/allUsers")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[")))
                .andExpect(content().string(containsString("]")));
    }

    @Test
    public void activities_returns_array() throws Exception {
        this.mockMvc.perform(get("/activities")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[")))
                .andExpect(content().string(containsString("]")));
    }

    @Test
    public void add_activity_returns_activity() throws Exception {

        Activity activity = new Activity(1, ActType.vegetarian_meal,1,Activity.getDateTime());
        String activityString = gson.toJson(activity);
        this.mockMvc.perform(post("/addactivity").contentType(APPLICATION_JSON_UTF8).content(activityString)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("activity_type")));
    }




}
