package hello;

import hello.web.Greeting;
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

    @Test
    public void Hello_Test() throws Exception {
        String body = this.testRestTemplate.getForObject("/hello?name=world", String.class);
        Assert.assertEquals(body, "Hello world!");
    }

    @Test
    public void Greetings_test() throws Exception {
        Greeting greeting = new Greeting(1,"Hello, World!");
        Greeting test = this.testRestTemplate.getForObject("/greetings", Greeting.class);
        Assert.assertEquals(greeting.getContent(),test.getContent());
    }



}
