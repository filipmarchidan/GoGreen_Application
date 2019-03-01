package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Main extends Application implements EventHandler<ActionEvent> {

    Button button;
    @Override
    public void start(Stage primaryStage) throws Exception{

        button = new Button();
        button.setText("Vegetarian meal");

        button.setOnAction(this);
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("GoGreen");
        primaryStage.setScene(new Scene(layout, 300, 275));
        primaryStage.show();


    }


    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()==button){
            System.out.println("You're sure you didn't eat spareribs");

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
