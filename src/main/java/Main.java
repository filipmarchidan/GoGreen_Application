import API.messages.*;

import com.google.gson.Gson;
import API.ClientApplication;

import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;

public class Main {

    private static ClientApplication client;
    private static Scanner scanner;
    private static Gson gson;
    private static Calendar calendar;

    public static void main(String[] args){

        client = new ClientApplication("http://localhost:8080");
        scanner = new Scanner(System.in);
        gson = new Gson();
        calendar = Calendar.getInstance();
        while(true){
            displayMenu();
            handleUserInput();
        }

    }

    /** Displays the commandline menu before GUI is implemented.
     *
     */
    private static void displayMenu(){

        System.out.println("Choose one of the options from the menu!");
        System.out.println("1 - Log in Mockup");
        System.out.println("2 - Add activity Mockup");
        System.out.println("3 - View Leaderboard Mockup");

    }

    /** Handles the CommandLine input before GUI is implemented.
     *
     */
    private static void handleUserInput(){
        String input = scanner.next();
        switch(input){
            case "1":
                client.logIn("blah@blah.com","asdfsdf");
                break;
            case "2":
                java.util.Date date = calendar.getTime();
                client.addActivity(Activities.VeggyMeal,new Date(date.getTime()));
                break;

            case "3":
                client.seeLeaderboard();
                break;

            default:
                System.out.println("please enter a valid choice!");
                break;
        }
    }




}
