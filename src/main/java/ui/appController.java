
package ui;

import API.Activity;
import API.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class appController{

    private ClientApplication clientApplication = ClientApplication.getInstance();

    @FXML
    private AnchorPane content;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button exit;

    @FXML
    private Pane homeScreen;

    @FXML
    private void switchScreen(ActionEvent event){
        Button variable = (Button) event.getSource();
        String fxmlName = variable.getId();
        System.out.println(fxmlName);
        if(fxmlName.equals("home")) {
            borderPane.getChildren().removeAll();
            borderPane.setCenter(homeScreen);
        } else if (fxmlName.equals("history")) {
            displayActivities();
        }
        else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlName + ".fxml"));
                borderPane.setCenter(root);
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
        clientApplication.addActivity(new Activity(1,"veggie_meal",50,Activity.getDateTime()));
    }

    private void displayActivities(){
        Activity[] activities = clientApplication.getActivities();
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(600, 560);
        scroll.setFitToWidth(true);
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-background-color: #8ee4af;");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);
        for(Activity a : activities) {
            HBox active = new HBox();
            active.setStyle("-fx-border-color:  #05386B;" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 10 10 10 10;");
            active.setPrefSize(600, 50);
            active.setPadding(new Insets(5,5,5,5));
            active.setAlignment(Pos.CENTER);
            Label activity = new Label("Activity: " + a.getActivity_type());
            activity.setStyle("-fx-font-size:18px;");
            activity.setPrefWidth(200);
            Label co2 = new Label("Co2 Saved: "+ a.getCO2_savings());
            co2.setStyle("-fx-font-size:18px");
            co2.setPrefWidth(130);
            String[] formatedDate = a.getDate_time().split(" ");
            Label date = new Label("Date: " + formatedDate[0]);
            date.setStyle("-fx-font-size:18px");
            date.setPrefWidth(180);
            active.getChildren().addAll(activity, co2, date);
            System.out.println(a.getActivity_type() + a.getCO2_savings() + a.getDate_time());
            vbox.getChildren().add(active);
            }
        scroll.setContent(vbox);
        borderPane.getChildren().removeAll();
        borderPane.setCenter(scroll);
    }


    @FXML
    void handle_logout(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("/login.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(login);
    }

    @FXML
    void minimize(ActionEvent event){
        Stage stage = (Stage)content.getScene().getWindow();
        stage.setIconified(true);
    }



}
