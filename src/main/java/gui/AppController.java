package gui;

import client.Client;
import com.google.gson.Gson;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import gui.entity.TableUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;


public class AppController {
    
    //TODO: JUST FOR TESTING SHOULD BE FIXED LATER
    
    Gson gson = new Gson();

    @FXML
    private AnchorPane content;

    @FXML
    private BorderPane borderpane;

    @FXML
    public Label scoreRepresentation;

    @FXML
    public Label theScore;

    @FXML
    private Button exit;
    
    @FXML
    private CheckBox solar;
    
    @FXML
    private Pane homeScreen;
    
    @FXML
    private Slider transportslider;
    
    @FXML
    private Text transporttext;
    
    @FXML
    private Slider bikeslider;
    
    @FXML
    private Text biketext;

    @FXML
    private void switchScreen(ActionEvent event) {
        Button variable = (Button) event.getSource();
        String fxmlName = variable.getId();
        System.out.println(fxmlName);
        if (fxmlName.equals("home")) {
            borderpane.getChildren().removeAll();
            borderpane.setCenter(homeScreen);
        } else if (fxmlName.equals("history")) {
            displayActivities();
        } else if(fxmlName.equals("leaderboard")) {
            displayLeaderboard();
        } else if (fxmlName.equals("findfriends")) {
            displayUsers();
        }
        else {
            try {
                borderpane.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlName + ".fxml"));
                borderpane.setCenter(root);
                
                if(fxmlName.equals("activities")) {
                    User user = Client.findCurrentUser();
                    solar = (CheckBox) exit.getScene().lookup("#solar");
                    solar.setSelected(user.isSolarPanel());
                }

            } catch (IOException ex) {
                System.out.println("File " + fxmlName + ".fxml not found");
            }

        }
    }

    @FXML
    void closeProgram(ActionEvent event) {
        
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addActivity(ActionEvent event) {
        if(event.getSource() instanceof  Button) {
            Button button = (Button) event.getSource();
            ActType actType = null;
            int amount = 1;
            switch(button.getId()){
                case "vegetarian" :
                    actType = ActType.vegetarian_meal;
                    break;
                case "bike" :
                    actType = ActType.bike;
                    amount = (int)bikeslider.getValue();
                    if(amount == 0) {
                        //TODO: add buzzer sound or something?
                        return;
                    }
                    break;
                case "local" :
                    actType = ActType.local_produce;
                    break;
                case "transport" :
                    actType = ActType.public_transport;
                    amount = (int)transportslider.getValue();
                    if(amount == 0) {
                        //TODO: add buzzer sound or something?
                        return;
                    }
                    break;
                case "temp" :
                    actType = ActType.lower_temperature;
                    break;
            }
    
            MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
            Activity activity = new Activity(actType, amount,Activity.getCurrentDateTimeString());
            params.add("activity",gson.toJson(activity));
            HttpEntity<String> result = Client.postRequest(LoginController.sessionCookie,"http://localhost:8080/addactivity",params);
            Activity activity1 = gson.fromJson(result.getBody(),Activity.class);
            
            //TODO: Add response to user

        }

        else if(event.getSource() instanceof CheckBox) {
            CheckBox checkbox = (CheckBox) event.getSource();
            if(checkbox.isSelected()) {
                
                MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
                Activity activity = new Activity(ActType.solar_panel, 1,Activity.getCurrentDateTimeString());
                params.add("activity",gson.toJson(activity));
                HttpEntity<String> result = Client.postRequest(LoginController.sessionCookie,"http://localhost:8080/addactivity",params);
                
            } else {
                User user = Client.findCurrentUser();
                user.setSolarPanel(false);
                Client.updateSolar(user);
            }
            

    
            //TODO: Add response to user

        }
    }


    @FXML
    void refreshTotal(ActionEvent event){
        scoreRepresentation.setVisible(true);
        setTotal();
    }

    @FXML
    void setTotal(){
        try{
            User user = Client.findCurrentUser();
            int total = user.getTotalscore();
            String score = ((Integer) total).toString();
            theScore.setText(score);} catch(Exception e){
            theScore.setText("No score");
        }
    }

    @FXML
    public void updateBikeValue(Event event) {
        Slider slider = (Slider) event.getSource();
        int value = (int)slider.getValue();
        biketext.setText(Integer.toString(value));
    }
    
    @FXML
    public void updateTransportValue(Event event) {
        Slider slider = (Slider) event.getSource();
        int value = (int)slider.getValue();
        transporttext.setText(Integer.toString(value));
    }

    private void displayActivities() {
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(600, 560);
        scroll.setFitToWidth(true);
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-background-color: #8ee4af");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);
        vbox.setMinHeight(560);


        //TODO: FIX SESSIONCOOKIE LOCATION
        Activity[] activities = Client.getActivities();

        /*
        HttpEntity<String> rep = Client.getRequest(LoginController.sessionCookie,"http://localhost:8080/allActType");
        List<Double> activityTypes = gson.fromJson(rep.getBody(), List.class);
        */
        HttpEntity<String> co2Values = Client.getRequest(LoginController.sessionCookie, "http://localhost:8080/allActType");
        List<Double> co2List = gson.fromJson(co2Values.getBody(), List.class);

        for (Activity a : activities) {
            HBox active = new HBox(0);
            active.setStyle("-fx-border-color:  #05386B;"
                    + "-fx-border-width: 3;"
                    + "-fx-border-radius: 10 10 10 10;");
            active.setPrefSize(600, 50);
            active.setPadding(new Insets(0,10,0,10));
            active.setAlignment(Pos.CENTER);

            Label activity = new Label("Type: " + a.getActivity_type());
            activity.setStyle("-fx-font-size:15px;");
            activity.setPrefWidth(180);

            Double co2 = co2List.get(a.getActivity_type().ordinal());
            Label co2Label = new Label("Co2 Saved: " + co2.intValue()* a.getActivity_amount());
            co2Label.setStyle("-fx-font-size:15px;");
            co2Label.setPrefWidth(150);

            Label date = new Label("Date: " + a.getDate_time());
            date.setStyle("-fx-font-size:15px");
            date.setPrefWidth(200);

            Image image = new Image(getClass().getResource("/trash.png").toExternalForm());
            ImageView compressed = new ImageView(image);
            compressed.setPreserveRatio(true);
            compressed.setFitHeight(20);

            Button but = new Button();
            but.setGraphic(compressed);
            but.setStyle("-fx-background-color: #f23a3a;" +
                    "-fx-border-radius: 3 3 3 3;");
            but.setOnAction(event -> {
                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
                params.add("activity",gson.toJson(a));
                HttpEntity<String> response = Client.postRequest(LoginController.sessionCookie,"http://localhost:8080/removeactivity", params);
                boolean result = gson.fromJson(response.getBody(), boolean.class);
    
                vbox.getChildren().remove(active);
            });


            active.getChildren().addAll(activity, co2Label, date, but);
            System.out.println(a.getActivity_type() + a.getDate_time() + "!!!!!!");
            vbox.getChildren().add(active);
        }
        scroll.setContent(vbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(scroll);
    }

    private User[] InsertUser(User[] friends) {
        
        User user =  gson.fromJson(Client.getRequest(LoginController.sessionCookie,"http://localhost:8080/finduser").getBody(),User.class);
        System.out.println(user.getId());
        User[] friends2 = new User[friends.length + 1];
        int i = 0;
        while (i < friends.length && friends[i].getTotalscore() > user.getTotalscore() ) {
            System.out.println(i + " = " + i);
            friends2[i] = friends[i];
            i++;
        }
        friends2[i] = user;
        i++;
        while (i <= friends.length) {

            System.out.println(i+1 + " = " + i);
            friends2[i] = friends[i-1];
            i++;
        }
        return friends2;
    }

    private void displayLeaderboard() {
        ScrollPane pane = new ScrollPane();
        pane.setPrefSize(600, 560);
        pane.setFitToWidth(true);
        
        
        User[] friends = Client.getFriends();
        friends = InsertUser(friends);
        
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-background-color: #8ee4af");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);
        vbox.setMinHeight(560);
        
    
        TableView<TableUser> tableView = loadTable(friends.length);
        ObservableList<TableUser> imgList = fillTable(friends);
        tableView.setItems(imgList);
        vbox.getChildren().add(tableView);
        pane.setContent(vbox);
        pane.setStyle("-fx-font-size:15px");
        borderpane.getChildren().removeAll();
        borderpane.setCenter(pane);
    }
    
    private TableView<TableUser> loadTable(int size) {
        TableView<TableUser> tableView = new TableView<TableUser>();
        TableColumn<TableUser, String> rank = new TableColumn<>();
        TableColumn<TableUser, String> email = new TableColumn<>();
        TableColumn<TableUser, ImageView> achievementscolumn = new TableColumn<>();
        TableColumn<TableUser, String> score = new TableColumn<>();
        rank.setCellValueFactory(new PropertyValueFactory<TableUser, String>("rank"));
        rank.setPrefWidth(20);
        rank.setMaxWidth(20);
        rank.setMinWidth(20);
        rank.setText("#");
        email.setCellValueFactory(new PropertyValueFactory<TableUser, String>("email"));
        email.setMinWidth(190);
        email.setMaxWidth(190);
        email.setPrefWidth(190);
        email.setText("email");
        achievementscolumn.setCellValueFactory(new PropertyValueFactory<TableUser, ImageView>("achievements"));
        achievementscolumn.setPrefWidth(210);
        achievementscolumn.setMaxWidth(210);
        achievementscolumn.setMaxWidth(210);
        achievementscolumn.setText("achievements");
        score.setCellValueFactory(new PropertyValueFactory<TableUser, String>("score"));
        score.setPrefWidth(130);
        score.setMaxWidth(130);
        score.setMaxWidth(130);
        score.setText("score");
        tableView.getColumns().addAll(rank, email, achievementscolumn, score);
        tableView.setFixedCellSize(35);
        tableView.setPrefHeight(35 * size + 43);
        tableView.setStyle("-fx-border-color:  #05386B;"
                + "-fx-border-width: 3;"
                + "-fx-");
        return tableView;
    }
    
    private ObservableList<TableUser> fillTable(User[] friends) {
        ObservableList<TableUser> imgList = FXCollections.observableArrayList();
        
        for (int i = 0; i < Math.min(friends.length, 10); i++) {
            User user = friends[i];
            System.out.println(user);
            HBox hbox = new HBox();
            ImageView images = new ImageView(new Image(getClass().getResource("/images/path815.png").toExternalForm()));
            images.setFitHeight(30);
            images.setFitWidth(30);
            Tooltip.install(images, new Tooltip("This is an achievement"));
            ImageView images3 = new ImageView(new Image(getClass().getResource("/images/path815.png").toExternalForm()));
            images3.setFitHeight(30);
            images3.setFitWidth(30);
            ImageView images2 = new ImageView(new Image(getClass().getResource("/images/path817.png").toExternalForm()));
            images2.setFitHeight(30);
            images2.setFitWidth(30);
            ImageView images4 = new ImageView(new Image(getClass().getResource("/images/path817.png").toExternalForm()));
            images4.setFitHeight(30);
            images4.setFitWidth(30);
            Tooltip.install(images, new Tooltip("This is an achievement"));
            Tooltip.install(images2, new Tooltip("This is an achievement"));
            Tooltip.install(images3, new Tooltip("This is an achievement"));
            Tooltip.install(images4, new Tooltip("This is an achievement"));
            hbox.getChildren().addAll(images, images2, images3, images4);
            hbox.setSpacing(4);
            hbox.setFillHeight(true);
            TableUser tableUser = new TableUser(i + 1, user.getEmail(), hbox, user.getTotalscore());
            imgList.add(tableUser);
        }
        return imgList;
    }
    
    private void displayUsers() {
        ScrollPane pane = new ScrollPane();
        pane.setPrefSize(600, 560);
        pane.setFitToWidth(true);
    
        VBox vbox = new VBox();
    }
    



        /*
        for (int i = 0; i < Math.min(friends.length,10); i++) {

            HBox active = new HBox();
            active.setStyle("-fx-border-color:  #05386B;"
                    + "-fx-border-width: 3;"
                    + "-fx-border-radius: 10 10 10 10;");
            active.setPrefSize(600, 50);
            active.setPadding(new Insets(5,5,5,5));
            active.setAlignment(Pos.CENTER);
            Label activity = new Label("Activity: " + a.getActivity_type());
            activity.setStyle("-fx-font-size:18px;");
            activity.setPrefWidth(200);
            Label co2 = new Label("Co2 Saved: " + a.getCO2_savings());
            co2.setStyle("-fx-font-size:18px");
            co2.setPrefWidth(130);
            String[] formatedDate = a.getDate_time().split(" ");
            Label date = new Label("Date: " + formatedDate[0]);
            date.setStyle("-fx-font-size:18px");
            date.setPrefWidth(180);
            active.getChildren().addAll(activity, co2, date);
            System.out.println(a.getActivity_type() + a.getCO2_savings() + a.getDate_time());
            vbox.getChildren().add(active);
        }
        scroll.setContent(vbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(scroll);
    }
    */


    @FXML
    void handle_logout(ActionEvent event) throws IOException {

        Client.getRequest(LoginController.sessionCookie, "http://localhost:8080//logout");
        Parent login = FXMLLoader.load(getClass().getResource("/login.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(login);
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage)content.getScene().getWindow();
        stage.setIconified(true);
    }



}
