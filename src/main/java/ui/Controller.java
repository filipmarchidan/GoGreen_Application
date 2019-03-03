package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private AnchorPane stats;

    @FXML
    private AnchorPane menu;

    @FXML
    private AnchorPane signinScreen;

    @FXML
    private Button exit;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private TextArea passwordInput;

    @FXML
    private TextArea userField;


    @FXML
    void login(ActionEvent event) {
        AnchorPane pane = (AnchorPane) login.getParent();
        String username = userField.getText();
        String password = passwordInput.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            System.out.println("User has signed in");

            //Send request to server

            pane.setVisible(false);
        } else {
            System.out.println("User did not enter all fields");
        }




    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void register(ActionEvent event){

    }

}
