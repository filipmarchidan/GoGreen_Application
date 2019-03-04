package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class appController {

    @FXML
    private Button exitProgram;

    @FXML
    void closeProgram(ActionEvent event) {
        Stage stage = (Stage) exitProgram.getScene().getWindow();
        stage.close();
    }

}
