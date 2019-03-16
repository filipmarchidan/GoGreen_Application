package API;

import API.messages.Message;
import org.junit.Assert;
import org.junit.Test;

public class ClientApplicationTest {

    @Test
    public void client_retrieves_message(){

        ServerApplication serverApplication = new ServerApplication();
        serverApplication.main(new String[0]);

        ClientApplication client = new ClientApplication("http://localhost:8080/");

        Message message = new Message("Paul");
        User[] s = client.getUsers();
        System.out.println(s);
        String other = "{\"id\":1,\"content\":\"Hello, Paul!\"}";
        //Assert.assertEquals(s,other);
    }

}
