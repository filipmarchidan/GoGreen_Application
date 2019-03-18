package ui;

import API.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private ClientApplication clientApplication = ClientApplication.getInstance();

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
        String password1 = newPassword.getText();
        String password2 = newPasswordRepeat.getText();
        if(!newUsername.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && (password1.equals(password2))){
            registerContent.setVisible(false);
            regMenu.setVisible(false);
            pageLabel.setText("Go Green");


        }
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