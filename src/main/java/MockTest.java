import API.messages.*;

import com.google.gson.Gson;
import API.ClientApplication;
import API.messages.Activity;
import API.messages.LogInRequest;

import java.util.Scanner;

public class MockTest {

    private static ClientApplication client;
    private static Scanner scanner;
    private static Gson gson;
    public static void main(String[] args){

        client = new ClientApplication("http://localhost:8080");
        scanner = new Scanner(System.in);
        gson = new Gson();

        while(true){
            displayMenu();
            handleUserInput();
        }

    }

    private static void displayMenu(){

        System.out.println("Choose one of the options from the menu!");
        System.out.println("1 - Log in Mockup");
        System.out.println("2 - Add activity Mockup");
        System.out.println("3 - View Leaderboard Mockup");

    }

    private static void handleUserInput(){
        String input = scanner.next();
        switch(input){
            case "1":
                logIn();
                break;
            case "2":
                addActivity();
                break;

            case "3":
                seeLeaderboard();
                break;

            default:
                System.out.println("please enter a valid choice!");
                break;
        }
    }

    private static void logIn(){

        System.out.println("Please enter your username");
        String user = scanner.next();

        System.out.println("please enter your password");
        String password = scanner.next();

        LogInRequest logInRequest = new LogInRequest(user,password);

        String result = client.postRequest("/login",logInRequest);
        System.out.println(result);
    }

    private static void addActivity(){
        System.out.println("Adding: 'Eat a vegetarian meal' ");
        Activity activity = Activity.VeggyMeal;

        String result = client.postRequest("/addactivity",activity);
        Message message = gson.fromJson(result,Message.class);
        System.out.println(message.getContent());
    }

    private static void seeLeaderboard(){
        System.out.println("fetching leaderboard from server");

        String result = client.getRequest("/leaderboard",null);
        Message message = gson.fromJson(result,Message.class);
        System.out.println(message.getContent());
    }
}
