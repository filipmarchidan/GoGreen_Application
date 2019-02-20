package hello.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;


@RestController
public class GreetingsController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /** Function mapped to the /greetings endpoints for the spring server.
     *
     * @param name Optional - name passed along so the server can say hi, name.
     * @return returns the greeting to be passed back to the client
     */
    @RequestMapping("/greetings")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.print("hello");
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}