
package ui;

import API.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class appController {
    private ClientApplication clientApplication = ClientApplication.getInstance();
    @FXML
    private AnchorPane content;

    @FXML
    private AnchorPane sideBar1;

    @FXML
    private Button food;

    @FXML
    private AnchorPane stats1;

    @FXML
    private AnchorPane foodMenu;

    @FXML
    private Button vegMeal;

    @FXML
    private Button exit;

    @FXML
    private Label score;


    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handle_eatVeg(ActionEvent event) {

    }

    @FXML
    void handle_logout(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("/login.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(login);
    }

}
