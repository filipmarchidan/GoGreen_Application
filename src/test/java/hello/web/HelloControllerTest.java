package hello.web;

import org.junit.Assert;
import org.junit.Test;

public class HelloControllerTest {

    /** Test that makes sure the sayhello functions returns the expected value
     *
     */
    @Test
    public void sayHello_returns_hello() {
        HelloController hc = new HelloController();
        String a = hc.sayHello("Paul");
        String b = "Hello Paul!";
        Assert.assertEquals(a,b);
    }
}
