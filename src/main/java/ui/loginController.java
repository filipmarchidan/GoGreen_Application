package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {


    @FXML
    private AnchorPane signinScreen;

    @FXML
    private Button exit;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextArea userField;


    @FXML
    void login(ActionEvent event) {
        AnchorPane pane = (AnchorPane) login.getParent();
        String username = userField.getText();
        String password = passwordInput.getText();
        Scene loginScreen = login.getScene();
        if (!username.isEmpty() && !password.isEmpty()) {
                System.out.println("User has signed in");

            //Send request to server
          changeScene(loginScreen);
            pane.setVisible(false);
        } else {
            System.out.println("User did not enter all fields");
        }


    }
    void changeScene(Scene current) {
        try {
            Parent appView = FXMLLoader.load(getClass().getResource("/theApp.fxml"));
            Scene appScene = new Scene(appView);

            Stage window = (Stage) current.getWindow();
            window.setScene(appScene);
            window.show();
        } catch (IOException e) {
            System.out.println("FXMl file not found");
            System.out.println(e);
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
