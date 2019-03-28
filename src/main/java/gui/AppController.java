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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    //private int id = client.addUser(new User("test@blah","hellopassword")).getId();
    //private int friendId = client.addUser(new User("test2@blah2.com","hellopassword2")).getId();
    private int id = 1;

    @FXML
    private AnchorPane content;

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button exit;

    @FXML
    private Pane homeScreen;

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
        vbox.setStyle("-fx-background-color: #8ee4af");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);
        vbox.setMinHeight(560);

        Activity[] activities = client.getActivities();
        for (Activity a : activities) {
            HBox active = new HBox(0);
            active.setStyle("-fx-border-color:  #05386B;"
                    + "-fx-border-width: 3;"
                    + "-fx-border-radius: 10 10 10 10;");
            active.setPrefSize(600, 50);
            active.setPadding(new Insets(0,10,0,10));
            active.setAlignment(Pos.CENTER);

            Label activity = new Label("Type: " + a.getActivity_type());
            activity.setStyle("-fx-font-size:15px;");
            activity.setPrefWidth(180);

            Label co2 = new Label("Co2 Saved: " );
            //TODO: SHOW CO2 co2.setStyle("-fx-font-size:15px");
            //co2.setPrefWidth(150);

            Label date = new Label("Date: " + a.getDate_time());
            date.setStyle("-fx-font-size:15px");
            date.setPrefWidth(200);

            Image image = new Image(getClass().getResource("/trash.png").toExternalForm());
            ImageView compressed = new ImageView(image);
            compressed.setPreserveRatio(true);
            compressed.setFitHeight(20);

            Button but = new Button();
            but.setGraphic(compressed);
            but.setStyle("-fx-background-color: #f23a3a;" +
                    "-fx-border-radius: 3 3 3 3;");
            but.setOnAction(event -> {
                client.removeActivity(a);
                vbox.getChildren().remove(active);
            });


            active.getChildren().addAll(activity, co2, date, but);
            System.out.println(a.getActivity_type() + a.getDate_time());
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
        for (int i = 0; i < Math.max(friends.length,10); i++) {
            User user = friends[i];
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
