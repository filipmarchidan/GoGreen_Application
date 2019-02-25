package hello;

import org.junit.Assert;
import org.junit.Test;

public class HelloClientTest {


    @Test
    public void client_retrieves_message(){
        ServerApplication serverApplication = new ServerApplication();
        serverApplication.main(new String[0]);
        String s = HelloClient.GETRequest();
        String other = "{\"id\":1,\"content\":\"Hello, World!\"}";
        Assert.assertEquals(s,other);
    }
}
