package gui;

import client.Client;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private Client client = Client.getInstance();
    //TODO: JUST FOR TESTING SHOULD BE FIXED LATER
    private int id = 1;
    //private int friendId = client.addUser(new User("test2@blah2.com","hellopassword2")).getId();

    @FXML
    private AnchorPane content;

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button exit;

    @FXML
    private Pane homeScreen;

    @FXML
    private TableColumn rank;

    @FXML
    private TableColumn email;

    @FXML
    private TableColumn achievementscolumn;

    @FXML
    private TableColumn score;

    @FXML
    private TableView leaderboardTable;

    @FXML
    private void switchScreen(ActionEvent event) {
        Button variable = (Button) event.getSource();
        String fxmlName = variable.getId();
        System.out.println(fxmlName);
        if (fxmlName.equals("home")) {
            borderpane.getChildren().removeAll();
            borderpane.setCenter(homeScreen);
        } else if (fxmlName.equals("history")) {
            displayActivities();
        } else if(fxmlName.equals("leaderboard")) {
            displayLeaderboard();
        }
        else {
            try {
                borderpane.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlName + ".fxml"));
                borderpane.setCenter(root);
            } catch (IOException ex) {
                System.out.println("File " + fxmlName + ".fxml not found");
            }
        }
    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addMeal(ActionEvent event) {
        client.addActivity(new Activity(id, ActType.vegetarian_meal,1,Activity.getDateTime()));
    }

    private void displayActivities() {
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(600, 560);
        scroll.setFitToWidth(true);
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-background-color: #8ee4af;");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);

        Activity[] activities = client.getActivities();
        for (Activity a : activities) {
            HBox active = new HBox();
            active.setStyle("-fx-border-color:  #05386B;"
                    + "-fx-border-width: 3;"
                    + "-fx-border-radius: 10 10 10 10;");
            active.setPrefSize(600, 50);
            active.setPadding(new Insets(5,5,5,5));
            active.setAlignment(Pos.CENTER);
            Label activity = new Label("Activity: " + a.getActivity_type());
            activity.setStyle("-fx-font-size:18px;");
            activity.setPrefWidth(200);
            //TODO:Label co2 = new Label("Co2 Saved: " + a.getCO2_savings())
            //co2.setStyle("-fx-font-size:18px");
            //co2.setPrefWidth(130);
            String[] formatedDate = a.getDate_time().split(" ");
            Label date = new Label("Date: " + formatedDate[0]);
            date.setStyle("-fx-font-size:18px");
            date.setPrefWidth(180);
            active.getChildren().addAll(activity, date);
            //System.out.println(a.getActivity_type() + a.getCO2_savings() + a.getDate_time());
            vbox.getChildren().add(active);
        }
        scroll.setContent(vbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(scroll);
    }
    
    private void displayLeaderboard() {

        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(600, 560);
        scroll.setFitToWidth(true);
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-background-color: #8ee4af;");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);


        User[] friends = client.getFriends(id);
        leaderboardTable.setItems(friends.to);
        for(int i = 0; i < Math.min(friends.length,10);i++){
            rank.add
        }
        leaderboardTable.getColumns().add(friends);
        /*
        for (int i = 0; i < Math.min(friends.length,10); i++) {

            HBox active = new HBox();
            active.setStyle("-fx-border-color:  #05386B;"
                    + "-fx-border-width: 3;"
                    + "-fx-border-radius: 10 10 10 10;");
            active.setPrefSize(600, 50);
            active.setPadding(new Insets(5,5,5,5));
            active.setAlignment(Pos.CENTER);
            Label position = new Label("position: " + i);
            Label email = new Label("email: " + user.getEmail());
            email.setStyle("-fx-font-size:18px;");
            email.setPrefWidth(200);
            Label score = new Label("score: " + user.getTotalscore());
            active.getChildren().addAll(position, email, score);
            //System.out.println(a.getActivity_type() + a.getCO2_savings() + a.getDate_time());
            vbox.getChildren().add(active);
        }
        scroll.setContent(vbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(scroll);
        */
    }


    @FXML
    void handle_logout(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("/login.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(login);
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage)content.getScene().getWindow();
        stage.setIconified(true);
    }



}
