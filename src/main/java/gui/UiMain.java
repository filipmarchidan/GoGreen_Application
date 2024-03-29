package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UiMain extends Application {
    public static Stage stage = null;

    /** main method of the UI that instantiates the client and launches the UI elements.
     *
     * @param args list of arguments (not used)
     */
    public static void main(String[] args) {
        //client = Client.createInstance("","http://localhost:8080");
        //defaultUser = new User("user1@user1.com","user1");
        //defaultUser.setId(1);
        //defaultUser = client.addUser();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Load fxml file
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

        //Set style to not show border
        primaryStage.initStyle(StageStyle.UNDECORATED);

        //Load scene
        Scene login = new Scene(root);

        //Set stage to login scene
        primaryStage.setScene(login);

        //Set stage
        this.stage = primaryStage;

        //Show stage
        primaryStage.show();
    }



}
