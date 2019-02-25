package hello.web;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    @Test
    public void message_retrieve_content(){
        Message message = new Message(1,"Hello");
        Assert.assertEquals("Hello",message.getContent());
    }

    @Test
    public void message_retrieve_id(){
        Message message = new Message(1,"Hello");
        Assert.assertEquals(1,message.getId());
    }
    @Test
    public void message_equals_itself(){
        Message greeting = new Message(1, "Paul");
        Assert.assertEquals(greeting,greeting);
    }
    @Test
    public void message_equals_same(){
        Message greeting = new Message(1, "Paul");
        Message greeting2 = new Message(1, "Paul");
        Assert.assertEquals(greeting,greeting2);
    }
    @Test
    public void message_doesnotequals_other(){
        Message message = new Message(1, "Paul");
        Message message2 = new Message(1, "Bert");
        Assert.assertNotEquals(message,message2);
    }
    @Test
    public void message_doesnotequals_null(){
        Message message = new Message(1, "Paul");
        Assert.assertNotEquals(message,null);
    }

    @Test
    public void message_doesnotequals_string(){
        Message message = new Message(1, "Paul");
        String string = "Hello, Paul!";
        Assert.assertNotEquals(message,string);
    }
}
