package ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static Stage stage = null;


    public static void main(String[] args) {
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
