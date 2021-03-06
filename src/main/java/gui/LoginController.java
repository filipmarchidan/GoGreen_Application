package gui;

import client.Client;
import database.entities.User;

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
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    //private Client client = Client.getInstance();
    
    public static String sessionCookie;
    
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
    private  Label registrationStatus;

    @FXML
    private PasswordField newPasswordRepeat;

    @FXML
    private TextField userField;

    @FXML
    private Button regMenu;

    @FXML
    private Label pageLabel;

    @FXML
    private Label errorMessage;

    @FXML
    private Label registrationConfirmation;

    @FXML
    private Label passwordMessage;

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
        errorMessage.setVisible(false);
        passwordMessage.setVisible(false);

    }
    
    @FXML
    void createUser(ActionEvent event) {
        String newUsername = emailInput.getText();
        String password1 = newPassword.getText();
        String password2 = newPasswordRepeat.getText();
        
        if (!newUsername.isEmpty() && validate("Email", newUsername,
                "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")) {
            
            if (!password1.isEmpty() && !password2.isEmpty() && (password1.equals(password2))) {
                
                if (validatePassword(password1) == true) {
                    User user = new User();
                    user.setEmail(newUsername);
                    user.setPassword(password1);
                    boolean result = Client.addUser(user);
                    System.out.println(result);
                    if (!result) {
                        errorMessage.setText("This email is already in use.");
                        errorMessage.setVisible(true);
                    } else {
                        registrationStatus.setVisible(false);
                        registerContent.setVisible(false);
                        regMenu.setVisible(false);
                        pageLabel.setText("Go Green");
                        registrationConfirmation.setVisible(true);
                        errorMessage.setVisible(false);
                        passwordMessage.setVisible(false);
                        registrationConfirmation.setVisible(true);
                    }
                }
            }
        }
        emailInput.clear();
        newPassword.clear();
        newPasswordRepeat.clear();
    }

    @FXML
    void handle_login(ActionEvent event) throws IOException {
        String email = userField.getText().trim();
        String password = passwordInput.getText().trim();
        //String encodedPassword = passwordEncoder.encode(password);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", email);
        params.add("password", password);
        if (!email.isEmpty() && !password.isEmpty()) {
            
            HttpEntity<String> result = Client.postRequest("","http://localhost:8080/login", params);
            System.out.println(result);
            if (result == null) {
                errorMessage.setText("Your credentials are incorrect, please try again.");
                errorMessage.setVisible(true);
            } else {
                System.out.println("User has signed in");
                this.sessionCookie = Client.getSessionCookie(email, password);
                Parent menu = FXMLLoader.load(getClass().getResource("/theApp.fxml"));
                parent.getChildren().removeAll();
                parent.getChildren().setAll(menu);
                System.out.println(sessionCookie);
                
            }
            
        } else {
            errorMessage.setText("Please fill in both fields.");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    void handle_register(ActionEvent event) throws IOException {
        registerContent.setVisible(true);
        regMenu.setVisible(true);
        pageLabel.setText("Sign-up");
        errorMessage.setVisible(false);
        registrationConfirmation.setVisible(false);
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
    
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern pattern1 = Pattern.compile(pattern);
            Matcher matcher = pattern1.matcher(value);
            if (matcher.find() && matcher.group().equals(value)) {
                return true;
            } else {
                passwordMessage.setVisible(false);
                errorMessage.setText("This email address is invalid, please try again.");
                errorMessage.setVisible(true);
                return false;
            }
        } else {
            passwordMessage.setVisible(false);
            errorMessage.setText("Please fill in all fields.");
            errorMessage.setVisible(true);
            return false;
        }
    }
    
    private boolean validatePassword(String pass) {
        if (!pass.isEmpty()) {
            
            if (pass.length() >= 8) {
                
                if (pass.matches("^[a-zA-Z0-9]+$")) {
                    return true;
                } else {
                    errorMessage.setText("Your password must contain at least 8");
                    errorMessage.setVisible(true);
                    passwordMessage.setVisible(true);
                    return false;
                }
            } else {
                errorMessage.setText("Your password must contain at least 8");
                errorMessage.setVisible(true);
                passwordMessage.setVisible(true);
                return false;
            }
        }
        passwordMessage.setVisible(false);
        errorMessage.setText("Please fill in all fields.");
        errorMessage.setVisible(true);
        return false;
    }
}
