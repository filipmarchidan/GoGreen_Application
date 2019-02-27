package API.controllers;

import API.messages.Message;
import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    @Test
    public void message_retrieve_content(){
        Message message = new Message("Hello");
        Assert.assertEquals("Hello",message.getContent());
    }

    @Test
    public void message_equals_itself(){
        Message greeting = new Message( "Paul");
        Assert.assertEquals(greeting,greeting);
    }
    @Test
    public void message_equals_same(){
        Message greeting = new Message( "Paul");
        Message greeting2 = new Message( "Paul");
        Assert.assertEquals(greeting,greeting2);
    }
    @Test
    public void message_doesnotequals_other(){
        Message message = new Message( "Paul");
        Message message2 = new Message( "Bert");
        Assert.assertNotEquals(message,message2);
    }
    @Test
    public void message_doesnotequals_null(){
        Message message = new Message( "Paul");
        Assert.assertNotEquals(message,null);
    }

    @Test
    public void message_doesnotequals_string(){
        Message message = new Message( "Paul");
        String string = "Hello, Paul!";
        Assert.assertNotEquals(message,string);
    }
}
