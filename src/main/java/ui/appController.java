
package ui;

import API.Activity;
import API.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Button theVeg;

    @FXML
    private void switchScreen(ActionEvent event){
        Button variable = (Button) event.getSource();
        String fxmlName = variable.getId();
        System.out.println(fxmlName);
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlName + ".fxml"));
            borderPane.setCenter(root);
        } catch(IOException ex) {
            System.out.println("File is not found");
        }
    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addMeal(ActionEvent event) {
        clientApplication.addActivity(new Activity(1,"vegetarian_meal",50,Activity.getDateTime()));
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
