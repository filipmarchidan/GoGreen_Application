package hello.web;

import org.junit.Assert;
import org.junit.Test;

public class HelloControllerTest {

    @Test
    public void HelloController_returns_hello() {
        HelloController hc = new HelloController();
        String a = hc.sayHello("Paul");
        String b = "Hello Paul!";
        Assert.assertEquals(a,b);
    }
}
