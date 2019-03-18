package API.messages;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    /**
     *  makes sure content is correctly set and retrieved
     */
    @Test
    public void message_retrieve_content(){
        Message message = new Message("Hello");
        Assert.assertEquals("Hello",message.getContent());
    }


    /**
     *  tests if the object equals itself
     */
    @Test
    public void message_equals_itself(){
        Message greeting = new Message( "Paul");
        Assert.assertEquals(greeting,greeting);
    }
    /**
     *  tests if the object equals a similar object
     */
    @Test
    public void message_equals_same(){
        Message greeting = new Message( "Paul");
        Message greeting2 = new Message( "Paul");
        Assert.assertEquals(greeting,greeting2);
    }

    /**
     *  tests if the object does not equals a different object
     */
    @Test
    public void message_doesnotequals_other(){
        Message message = new Message( "Paul");
        Message message2 = new Message( "Bert");
        Assert.assertNotEquals(message,message2);
    }

    /**
     *  tests that the object does not equal null
     */
    @Test
    public void message_doesnotequals_null(){
        Message message = new Message( "Paul");
        Assert.assertNotEquals(message,null);
    }

    /**
     *  tests that the object does not equal a similar object of different type
     */
    @Test
    public void message_doesnotequals_string(){
        Message message = new Message( "Paul");
        String string = "Hello, Paul!";
        Assert.assertNotEquals(message,string);
    }
}
