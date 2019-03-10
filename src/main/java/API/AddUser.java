package API;


import java.util.Scanner;

public class AddUser {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
       String email = sc.next();
       String password = sc.next();
       System.out.println("e" + email + "p" + password);
       MainController mc = new MainController();
      mc.addNewUser(password, email);

    }
}
