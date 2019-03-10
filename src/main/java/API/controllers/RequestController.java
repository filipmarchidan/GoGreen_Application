package API.controllers;

import API.messages.Activity;
import API.messages.LogInRequest;
import API.messages.Message;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RequestController {

    private final Gson gson = new Gson();
    private final Message message  = new Message("hello!");
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hello")
    public String sayHello(@RequestParam(value = "name") String name)
    {
        return "Hello " + name + "!";
    }


    @RequestMapping(value ="/leaderboard", method = RequestMethod.GET)
    public Message showLeaderboard(@RequestHeader HttpHeaders headers) {

        Message leaderboard = new Message("The leadboard is: \n 1 - YOU \n 2 - Everybody else!");
        return leaderboard;
    }

    @RequestMapping("/login")
    public Message logIn(@RequestBody LogInRequest logInRequest, @RequestHeader HttpHeaders headers) {
        Message message = new Message(logInRequest.getUserName() + " tried to connect with password " + logInRequest.getPassWord());
        return message;
    }

}
