package API;

import API.messages.Message;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServerApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainControllerTest {

    @Autowired
    private MainController mainController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(mainController).isNotNull();

    }
    @Test
    public void add_activity_returns_activity() throws Exception {
        Activity activity = new Activity(1,1,"veggy_meal",50,Activity.getDateTime());
        Activity activity2 = mainController.addNewActivity(activity);
        Assert.assertEquals(activity.getActivity_type(),activity2.getActivity_type());
        Assert.assertEquals(activity.getCO2_savings(),activity2.getCO2_savings());
        Assert.assertEquals(activity.getDate_time(),activity2.getDate_time());


    }



}
