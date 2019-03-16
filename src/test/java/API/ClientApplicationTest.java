package API;

import API.messages.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ClientApplicationTest {

    ServerApplication serverApplication;
    ClientApplication clientApplication;
    @Before
    public void before(){
        serverApplication = new ServerApplication();
        clientApplication = ClientApplication.createInstance("http://localhost:8080/");
    }
    @Test
    public void client_retrieves_message(){

        serverApplication.main(new String[0]);

        Message message = new Message("Paul");
        User[] s = clientApplication.getUsers();
        System.out.println(s);
        String other = "{\"id\":1,\"content\":\"Hello, Paul!\"}";
        //Assert.assertEquals(s,other);
    }

    @Test
    public void getinstance_returns_instance(){
        ClientApplication client = ClientApplication.createInstance("http://localhost:8080/");
        ClientApplication client2 = ClientApplication.getInstance();
        Assert.assertEquals(client,client2);

    }


    @Test
    public void client_adds_activity(){
        clientApplication = ClientApplication.getInstance();
        Activity veggy = new Activity(1,1,"veggy_meal",50,Activity.getDateTime());
        Activity back = clientApplication.addActivity(veggy);
        Assert.assertEquals(veggy.getDate_time(),back.getDate_time());
        Assert.assertEquals(veggy.getCO2_savings(),back.getCO2_savings());
        Assert.assertEquals(veggy.getActivity_type(),back.getActivity_type());
        Assert.assertEquals(veggy.getUser_id(),back.getUser_id());
    }

    @Test
    public void client_adds_activity2(){

        clientApplication = ClientApplication.getInstance();
        Activity veggy = new Activity(1,1,"veggy_meal",50,Activity.getDateTime());
        Activity back = clientApplication.addActivity(veggy);
        Activity[] activities = clientApplication.getActivities();
        boolean found = false;
        for(Activity a : activities){
            if(a.getId().equals(back.getId())) {
                System.out.println("setting boolean to true");
                found = true;
            }
        }
        Assert.assertTrue(found);
    }



}
