package UI;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextArea;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;

public class Controller {

    @FXML
    private AnchorPane SideBar;

    @FXML
    private AnchorPane Stats;

    @FXML
    private AnchorPane Menu;

    @FXML
    private AnchorPane Menu1;

    @FXML
    private Button Exit;

    @FXML
    private Button Login;

    @FXML
    private Button Register;

    @FXML
    private TextArea PassWordInput;

    @FXML
    private TextArea UserField;


    @FXML
    void Login(ActionEvent event) {
        AnchorPane pane = (AnchorPane) Login.getParent();
        String Username = UserField.getText();
        String Password = PassWordInput.getText();
        if (!Username.isEmpty() && !Password.isEmpty()){
            System.out.println("User has signed in");

            //Send request to server

            pane.setVisible(false);
        }
        else{
            System.out.println("User did not enter all fields");
        }




    }

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void register(ActionEvent event){

    }

}
