package gui;

import API.UserService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    private Client client = Client.getInstance();
    
    public static String sessionCookie;
    @Autowired
    private AuthenticationManager authenticationManager;
    
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

    @Autowired
    UserService userService;
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
    private PasswordEncoder passwordEncoder;
    @FXML

    void createUser(ActionEvent event) {
        String newUsername = emailInput.getText();
        String password1 = newPassword.getText();
        String password2 = newPasswordRepeat.getText();
        if (!newUsername.isEmpty() && !password1.isEmpty()
                && !password2.isEmpty() && (password1.equals(password2)) && validate("Email", newUsername, "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")) {
            if(checkAvailability(newUsername) == true){
            registerContent.setVisible(false);
            regMenu.setVisible(false);
            pageLabel.setText("Go Green");
            User user = new User();
            user.setEmail(newUsername);
            user.setPassword(password1);
            client.addUser(user);
        }}
        emailInput.clear();
        newPassword.clear();
        newPasswordRepeat.clear();
    }
    public boolean checkAvailability(String email)
    {
        User user = new User();
        user.setEmail(email);
        try{
            client.findByEmail(email);
            System.out.println("Already exists");
            return false;
        }
        catch(Exception e){

        }
        finally
        {
            return true;
        }
    }
    /*
    @FXML
    public void handle_login(ActionEvent event) throws IOException {
        Authentication authToken = new UsernamePasswordAuthenticationToken(emailInput.getText(), passwordInput.getText());
        try {
            authToken = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (AuthenticationException e) {
            System.out.println(("Login failure, please try again:"));
            
            return;
        }
        System.out.println("User has signed in");
        Parent menu = FXMLLoader.load(getClass().getResource("/theApp.fxml"));
        parent.getChildren().removeAll();
        parent.getChildren().setAll(menu);
    }
    */
    

    @FXML
    void handle_login(ActionEvent event) throws IOException {
        String email = userField.getText().trim();
        String password = passwordInput.getText().trim();
       // String encodedPassword = passwordEncoder.encode(password);
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", email);
        params.add("password", password);
        if (!email.isEmpty() && !password.isEmpty()) {
            
            HttpEntity<String> result = client.postRequest("","http://localhost:8080/login", params);
            System.out.println(result.getBody());
            if(result.getBody().equals("not authenticated")){
                System.out.println("wrong credentials");
                
            } else {
                System.out.println("User has signed in");
                this.sessionCookie = client.getSessionCookie(email, password);
                Parent menu = FXMLLoader.load(getClass().getResource("/theApp.fxml"));
                parent.getChildren().removeAll();
                parent.getChildren().setAll(menu);
                System.out.println(sessionCookie);
                
            }
           
            
        } else {
            System.out.println("empty credentials");
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
    private boolean validate(String field, String value, String pattern){
        if(!value.isEmpty()){
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if(m.find() && m.group().equals(value)){
                return true;
            }else{
                validationAlert(field, false);
                return false;
            }
        }else{
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if(field.equals("Role")) alert.setContentText("Please Select "+ field);
        else{
            if(empty) alert.setContentText("Please Enter "+ field);
            else alert.setContentText("Please Enter Valid "+ field);
        }
        alert.showAndWait();
    }

}
