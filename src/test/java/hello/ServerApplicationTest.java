package hello;

import hello.web.Message;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServerApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServerApplicationTest{

    @Test
    public void contextLoads() {
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    /** Tests that the Spring server is able to make use of the hello endpoint
     *
     */
    @Test
    public void Hello_Test() {
        String body = this.testRestTemplate.getForObject("/hello?name=World", String.class);
        Assert.assertEquals(body, "Hello World!");
    }

    /** Tests that the Spring server is able to make use of the message endpoint
     *
     */
    @Test
    public void Greetings_test(){
        Message greeting = new Message(1,"Hello, World!");
        Message test = this.testRestTemplate.getForObject("/message", Message.class);
        Assert.assertEquals(greeting.getContent(),test.getContent());
    }

}
