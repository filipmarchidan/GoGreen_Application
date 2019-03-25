package gui;

import client.Client;
import database.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@RestController
public class LoginController implements Initializable {
    

    private Client client = Client.getInstance();

    @FXML
    private AnchorPane parent;

    @FXML
    private Pane registerContent;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField newPasswordRepeat;

    @FXML
    private TextField userField;

    @FXML
    private Button regMenu;

    @FXML
    private Button login;

    @FXML
    private Label pageLabel;

    @FXML
    private Button exit;

    
    //x-coordinate of the mousecursor
    private double xoffset;

    //y-coordinate of the mousecursor
    private double yoffset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void backLogin(ActionEvent event)throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("/login.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(login);

    }

    @FXML
    void createUser(ActionEvent event) {
        String newUsername = emailInput.getText();
        String password = newPassword.getText();
        String passwordRepeat = newPasswordRepeat.getText();
        if(newUsername.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Username error");
            alert.setHeaderText("Error 01");
            alert.setContentText("Username cannot be empty");
            alert.showAndWait();
        }
        if(client.checkRegistration(newUsername,password) == false)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Username error");
            alert.setHeaderText("Error 02");
            alert.setContentText("Username already exists");
            alert.showAndWait();
        }
        if (!newUsername.isEmpty() && !password.isEmpty()
                && !passwordRepeat.isEmpty() && (password.equals(passwordRepeat) && client.checkRegistration(newUsername, password) == true)) {
            registerContent.setVisible(false);
            regMenu.setVisible(false);
            pageLabel.setText("Go Green");
            User newUser = new User(newUsername, password);
            client.addUser(newUser);
        }
        emailInput.clear();
        newPassword.clear();
        newPasswordRepeat.clear();
    }



    @FXML
    void handle_login(ActionEvent event) throws IOException {
        String username = userField.getText();
        String password = passwordInput.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            
            System.out.println("User has signed in");
            Parent menu = FXMLLoader.load(getClass().getResource("/theApp.fxml"));
            parent.getChildren().removeAll();
            parent.getChildren().setAll(menu);

        } else {
            System.out.println("User did not enter all fields");
        }
    }

    @FXML
    void handle_register(ActionEvent event) throws IOException {
        registerContent.setVisible(true);
        regMenu.setVisible(true);
        pageLabel.setText("Sign-up");
    }

    private void makeStageDragable() {
        parent.setOnMousePressed(event -> {
            xoffset = event.getSceneX();
            yoffset = event.getSceneY();
        });
        parent.setOnMouseDragged(event -> {
            UiMain.stage.setX(event.getScreenX() - xoffset);
            UiMain.stage.setY(event.getScreenY() - yoffset);
            UiMain.stage.setOpacity(0.8f);
        });
        parent.setOnDragDone(event -> {
            UiMain.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased(event -> {
            UiMain.stage.setOpacity(1.0f);
        });
    }

}
