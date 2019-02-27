package hello.web;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    /**
     *  makes sure content is correctly set and retrieved
     */
    @Test
    public void message_retrieve_content(){
        Message message = new Message(1,"Hello");
        Assert.assertEquals("Hello",message.getContent());
    }
    /**
     *  makes sure ID is correctly set and retrieved
     */
    @Test
    public void message_retrieve_id(){
        Message message = new Message(1,"Hello");
        Assert.assertEquals(1,message.getId());
    }
    /**
     *  tests if the object equals itself
     */
    @Test
    public void message_equals_itself(){
        Message greeting = new Message(1, "Paul");
        Assert.assertEquals(greeting,greeting);
    }
    /**
     *  tests if the object equals a similar object
     */
    @Test
    public void message_equals_same(){
        Message greeting = new Message(1, "Paul");
        Message greeting2 = new Message(1, "Paul");
        Assert.assertEquals(greeting,greeting2);
    }

    /**
     *  tests if the object does not equals a different object
     */
    @Test
    public void message_doesnotequals_other(){
        Message message = new Message(1, "Paul");
        Message message2 = new Message(1, "Bert");
        Assert.assertNotEquals(message,message2);
    }

    /**
     *  tests that the object does not equal null
     */
    @Test
    public void message_doesnotequals_null(){
        Message message = new Message(1, "Paul");
        Assert.assertNotEquals(message,null);
    }

    /**
     *  tests that the object does not equal a similar object of different type
     */
    @Test
    public void message_doesnotequals_string(){
        Message message = new Message(1, "Paul");
        String string = "Hello, Paul!";
        Assert.assertNotEquals(message,string);
    }
}
