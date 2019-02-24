package hello.web;

import org.junit.Assert;
import org.junit.Test;


public class MessageControllerTest {

    @Test
    public void MessageController_returns_correct_message() {
        MessageController mc = new MessageController();
        Message a = mc.message("Paul");
        Message b = new Message(1, "Hello, Paul!");
        Assert.assertEquals(a,b);
    }
}
