package gui;

import com.google.gson.Gson;

import client.Client;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class AppController {
    
    //TODO: JUST FOR TESTING SHOULD BE FIXED LATER
    
    
    @FXML
    public Label scoreRepresentation;
    
    @FXML
    public Label theScore;
    
    private Gson gson = new Gson();
    
    @FXML
    private AnchorPane content;

    @FXML
    private BorderPane borderpane;


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
    
    /** Oversees the switching of scenes.
     *
     * @param event button trigger
     */
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
        } else if (fxmlName.equals("leaderboard")) {
            displayLeaderboard();
        } else if (fxmlName.equals("findfriends")) {
            displayUsers();
        } else {
            try {
                borderpane.getChildren().removeAll();
                Parent root = FXMLLoader.load(getClass().getResource("/" + fxmlName + ".fxml"));
                borderpane.setCenter(root);
                
                if (fxmlName.equals("activities")) {
                    User user = Client.findCurrentUser();
                    solar = (CheckBox) exit.getScene().lookup("#solar");
                    solar.setSelected(user.isSolarPanel());
                }

            } catch (IOException ex) {
                System.out.println("File " + fxmlName + ".fxml not found");
            }

        }
    }
    
    /** Closes the program.
     *
     * @param event button trigger
     */
    @FXML
    void closeProgram(ActionEvent event) {
        
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
    
    /** Adds an activity based on the button.
     *
     * @param event button trigger
     */
    @FXML
    void addActivity(ActionEvent event) {
        if (event.getSource() instanceof  Button) {
            Button button = (Button) event.getSource();
            ActType actType = null;
            int amount = 1;
            switch (button.getId()) {
                case "vegetarian" :
                    actType = ActType.vegetarian_meal;
                    break;
                case "bike" :
                    actType = ActType.bike;
                    amount = (int)bikeslider.getValue();
                    if (amount == 0) {
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
                    if (amount == 0) {
                        //TODO: add buzzer sound or something?
                        return;
                    }
                    break;
                case "temp" :
                    actType = ActType.lower_temperature;
                    break;
                default:
                    return;
            }
    
            MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
            Activity activity = new Activity(actType, amount,Activity.getCurrentDateTimeString());
            params.add("activity",gson.toJson(activity));
            HttpEntity<String> result = Client.postRequest(LoginController.sessionCookie,"http://localhost:8080/addactivity",params);
            Activity activity1 = gson.fromJson(result.getBody(),Activity.class);
            
            //TODO: Add response to user

        } else if (event.getSource() instanceof CheckBox) {
            handleSolarActivity(event);
        }
    }
    
    /** Makes sure the client properly deals with a solar activity.
     *
     * @param event checkbox that calls the function
     */
    private void handleSolarActivity(Event event) {
        CheckBox checkbox = (CheckBox) event.getSource();
        if (checkbox.isSelected()) {
        
            MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
            Activity activity = new Activity(ActType.solar_panel,
                    1,Activity.getCurrentDateTimeString());
            
            params.add("activity",gson.toJson(activity));
            HttpEntity<String> result = Client.postRequest(LoginController.sessionCookie,
                    "http://localhost:8080/addactivity",params);

            bw.write("The Trees Thank You");
            bw.flush();
        
        } else {
            User user = Client.findCurrentUser();
            user.setSolarPanel(false);
            Client.updateSolar(user);
        }
    
    
        
    
    }
    
    /** Makes sure the sliders always update the numerical value.
     *
     * @param event button that calls the function
     */
    @FXML
    void refreshTotal(ActionEvent event) {
        scoreRepresentation.setVisible(true);
        setTotal();
    }
    
    /** sets the current total.
     *
     */
    @FXML
    void setTotal() {
        try {
            User user = Client.findCurrentUser();
            int total = user.getTotalscore();
            String score = ((Integer) total).toString();
            theScore.setText(score);
        } catch (NullPointerException e) {
            theScore.setText("No score");
        }
    }
    
    /** Makes sure the sliders always update the numerical value.
     *
     * @param event button that calls the function
     */
    @FXML
    public void updateBikeValue(Event event) {
        Slider slider = (Slider) event.getSource();
        int value = (int)slider.getValue();
        biketext.setText(Integer.toString(value));
    }
    
    /** Makes sure the sliders always update the numerical value.
     *
     * @param event button that calls the function
     */
    @FXML
    public void updateTransportValue(Event event) {
        Slider slider = (Slider) event.getSource();
        int value = (int)slider.getValue();
        transporttext.setText(Integer.toString(value));
    }
    
    /** Retrieves and displays all the activities of the user.
     *
     */
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
            Label co2Label = new Label("Co2 Saved: " + co2.intValue() * a.getActivity_amount());
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
            but.setStyle("-fx-background-color: #f23a3a;"
                    + "-fx-border-radius: 3 3 3 3;");
            but.setOnAction(event -> {
                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
                params.add("activity",gson.toJson(a));
                HttpEntity<String> response = Client.postRequest(LoginController.sessionCookie,"http://localhost:8080/removeactivity", params);
                boolean result = gson.fromJson(response.getBody(), boolean.class);
    
                vbox.getChildren().remove(active);
            });


            active.getChildren().addAll(activity, co2Label, date, but);
            //System.out.println(a.getActivity_type() + a.getDate_time() + "!!!!!!");
            vbox.getChildren().add(active);
        }
        scroll.setContent(vbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(scroll);
    }
    
    /** Finds the current user and inserts it in the right place in the array of friends.
     *
     * @param friends the users friends.
     * @return Array of the users friends including the user
     */
    private User[] insertUser(User[] friends) {
        
        User user =  gson.fromJson(Client.getRequest(LoginController.sessionCookie,"http://localhost:8080/finduser").getBody(),User.class);
        System.out.println(user.getId());
        User[] friends2 = new User[friends.length + 1];
        int index = 0;
        while (index < friends.length && friends[index].getTotalscore() > user.getTotalscore() ) {
            System.out.println(index + " = " + index);
            friends2[index] = friends[index];
            index++;
        }
        friends2[index] = user;
        index++;
        while (index <= friends.length) {

            //System.out.println(index + 1 + " = " + index);
            friends2[index] = friends[index - 1];
            index++;
        }
        return friends2;
    }
    
    /** Main function that collects friends and displays the leaderboard to the user.
     *
     */
    private void displayLeaderboard() {
        ScrollPane pane = new ScrollPane();
        pane.setPrefSize(600, 560);
        pane.setFitToWidth(true);
        
        
        User[] friends = Client.getFriends();
        friends = insertUser(friends);
        
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-background-color: #8ee4af");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setFillWidth(true);
        vbox.setMinHeight(560);
        
    

        ObservableList<TableUser> imgList = fillTable(friends);
        TableView<TableUser> tableView = loadTable(Math.min(friends.length,11));
        tableView.setItems(imgList);
        vbox.getChildren().add(tableView);
        pane.setContent(vbox);
        pane.setStyle("-fx-font-size:15px");
        borderpane.getChildren().removeAll();
        borderpane.setCenter(pane);
    }
    
    /** Prepares the leaderboard table based on the amount of friends.
     *
     * @param size amount of friends (including user)
     * @return TableView that can display the scores
     */
    private TableView<TableUser> loadTable(int size) {
        
        TableColumn<TableUser, String> rank = new TableColumn<>();
        
        
        
        rank.setCellValueFactory(new PropertyValueFactory<TableUser, String>("rank"));
        rank.setPrefWidth(25);
        rank.setMaxWidth(25);
        rank.setMinWidth(25);
        rank.setText("#");
        
        TableColumn<TableUser, String> email = new TableColumn<>();
        email.setCellValueFactory(new PropertyValueFactory<TableUser, String>("email"));
        email.setMinWidth(190);
        email.setMaxWidth(190);
        email.setPrefWidth(190);
        email.setText("email");
        
        TableColumn<TableUser, ImageView> achievementscolumn = new TableColumn<>();
        achievementscolumn.setCellValueFactory(
                new PropertyValueFactory<TableUser, ImageView>("achievements"));
        achievementscolumn.setPrefWidth(210);
        achievementscolumn.setMaxWidth(210);
        achievementscolumn.setMaxWidth(210);
        achievementscolumn.setText("achievements");
        
        TableColumn<TableUser, String> score = new TableColumn<>();
        score.setCellValueFactory(new PropertyValueFactory<TableUser, String>("score"));
        score.setPrefWidth(118);
        score.setMaxWidth(118);
        score.setMaxWidth(118);
        score.setText("score");
        
        TableView<TableUser> tableView = new TableView<TableUser>();
        tableView.getColumns().addAll(rank, email, achievementscolumn, score);
        tableView.setFixedCellSize(35);
        tableView.setPrefHeight(35.8f * size + 38);
        tableView.setStyle("-fx-border-color:  #05386B;"
                + "-fx-border-width: 3;");
        
        return tableView;
    }
    
    /** fills a List of TableUsers for display in the leaderboards.
     *
     * @param friends list of friends for the leaderboard.
     * @return List of tabluUsers
     */
    private ObservableList<TableUser> fillTable(User[] friends) {
        ObservableList<TableUser> imgList = FXCollections.observableArrayList();
        User currentUser = Client.findCurrentUser();
        Boolean seen = false;
        for (int i = 0; i < Math.min(friends.length, 10); i++) {
            User user = friends[i];
            if (user.equals(currentUser)) {
                seen = true;
            }
            System.out.println(user);
            
            /*
            HBox hbox = new HBox();
            ImageView images = new ImageView(
            new Image(getClass().getResource("/images/path815.png").toExternalForm()));
            images.setFitHeight(30);
            images.setFitWidth(30);
            Tooltip.install(images, new Tooltip("This is an achievement"));
            ImageView images3 = new ImageView(
            new Image(getClass().getResource("/images/path815.png").toExternalForm()));
            images3.setFitHeight(30);
            images3.setFitWidth(30);
            ImageView images2 = new ImageView(
            new Image(getClass().getResource("/images/path817.png").toExternalForm()));
            images2.setFitHeight(30);
            images2.setFitWidth(30);
            ImageView images4 = new ImageView(
            new Image(getClass().getResource("/images/path817.png").toExternalForm()));
            images4.setFitHeight(30);
            images4.setFitWidth(30);
            Tooltip.install(images, new Tooltip("This is an achievement"));
            Tooltip.install(images2, new Tooltip("This is an achievement"));
            Tooltip.install(images3, new Tooltip("This is an achievement"));
            Tooltip.install(images4, new Tooltip("This is an achievement"));
            hbox.getChildren().addAll(images, images2, images3, images4);
            hbox.setSpacing(4);
            hbox.setFillHeight(true);
            */
            TableUser tableUser =
                    new TableUser(i + 1, user.getEmail(), null/*hbox*/, user.getTotalscore());
            imgList.add(tableUser);
        }
        if (!seen) {
            
            int rank = Arrays.asList(friends).indexOf(currentUser);
            TableUser tableUser = new TableUser(rank + 1,
                    currentUser.getEmail(),null,currentUser.getTotalscore());
            imgList.add(tableUser);
        }
        return imgList;
    }
    
    /** Displays the array of all users so the current user can friend/unfriend them.
     *
     */
    private void displayUsers() {
        
        ScrollPane pane = new ScrollPane();
        pane.setPrefSize(600, 560);
        pane.setFitToWidth(true);
        
        VBox vbox = new VBox();
        vbox.setFillWidth(true);

        vbox.setStyle("-fx-background-color: #8ee4af");
        vbox.setPadding(new Insets(10, 20, 10, 20));
        vbox.setSpacing(10);

        User currentUser = Client.findCurrentUser();
        List<User> currentFriends = Arrays.asList(Client.getFriends());
        User[] allusers = Client.getUsers();
        System.out.println(allusers.length + "AMOUNT OF USERS");
        
        int index = 0;
        while (index < allusers.length) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setFillHeight(true);
            hbox.setPrefWidth(600);
            //hbox.setPrefHeight(200);
            for (int j = index; j < index + 2; j++) {
                if (j < allusers.length && (allusers[j].equals(currentUser))) {
                    System.out.println(allusers[j].getEmail()
                            + " equals " + currentUser.getEmail() );
                    index++;
                    j++;
                }
                if (!(j < allusers.length)) {
                    break;
                }
                VBox innerv = new VBox();
                innerv.setPrefWidth(270);
                innerv.setPrefHeight(120);
                innerv.setFillWidth(true);
                innerv.setStyle("-fx-border-color: #05386B; "
                        + "-fx-border-width: 3; "
                        + "-fx-border-radius: 10 10 10 10;" );
                innerv.setPadding(new Insets(10, 50, 10, 50));
                Label email = new Label(allusers[j].getEmail());
                email.setStyle("-fx-font-size:15px;");
                email.setPrefWidth(270);
                email.setAlignment(Pos.CENTER);
                Label score = new Label("Score: " + Integer.toString(allusers[j].getTotalscore()));
                score.setStyle("-fx-font-size:15px;");
                score.setAlignment(Pos.CENTER);
                score.setPrefWidth(180);
    
                User user = allusers[j];
                innerv.getChildren().addAll(email,score);
                Button button1 = new Button();
                button1.setAlignment(Pos.BOTTOM_CENTER);
                button1.setPrefWidth(160);
                button1.setPadding(new Insets(10, 10, 10, 10));
                button1.setPrefHeight(50);
                button1.setStyle("-fx-background-radius: 15 15 15 15;"
                        + "-fx-background-color: #05386B;"
                        + "-fx-font-size:19px;"
                        + "-fx-text-fill: #edf5e1;");
    
                button1.setText("Follow");
    
                Button button2 = new Button();
                button2.setAlignment(Pos.BOTTOM_CENTER);
                button2.setPrefWidth(160);
                button2.setPadding(new Insets(10, 10, 10, 10));
                button2.setPrefHeight(50);
                button2.setStyle("-fx-background-radius: 15 15 15 15;"
                        + "-fx-background-color: #05386B;"
                        + "-fx-font-size:19px;"
                        + "-fx-text-fill: #edf5e1;");
    
                button2.setText("Unfollow");
                
                button1.setOnAction(event -> {
                    Client.followUser(user);
                    innerv.getChildren().remove(button1);
                    innerv.getChildren().add(button2);
                });
                button2.setOnAction(event -> {
                    Client.unfollowUser(user);
                    innerv.getChildren().remove(button2);
                    innerv.getChildren().add(button1);
                
                });
                
                if (!currentFriends.contains(allusers[j])) {
                    innerv.getChildren().add(button1);
                } else {
                    innerv.getChildren().add(button2);
                }
                
                //innerv.getChildren().addAll(email,score,button1);
                hbox.getChildren().add(innerv);
            }
            index += 2;
            vbox.getChildren().add(hbox);
        }
        pane.setContent(vbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(pane);
    }

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
