package gui;

import com.google.gson.Gson;

import client.Client;
import database.entities.Achievement;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;
import gui.entity.TableUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AppController {
    
    
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
    private CheckBox solarPanel;
    
    @FXML
    private Pane homeScreen;
    
    @FXML
    private ImageView bronze;

    @FXML
    private ImageView silver;

    @FXML
    private ImageView gold;

    @FXML
    private ImageView bike;
    
    @FXML
    private ImageView local;

    @FXML
    private ImageView solar;

    @FXML
    private ImageView vegetarian;

    @FXML
    private ImageView transport;

    @FXML
    private ImageView temperature;

    /** set the current screen.
     *
     * @param event button trigger
     */
    @FXML
    private void switchScreen(ActionEvent event) {
        Button variable = (Button) event.getSource();
        String fxmlName = variable.getId();
        System.out.println(fxmlName);
        switch (fxmlName) {
            case "home":
                borderpane.getChildren().removeAll();
                borderpane.setCenter(homeScreen);
                break;
            case "history":
                displayActivities();
                refreshTotal();
                break;
            case "leaderboard":
                displayLeaderboard();
                refreshTotal();
                break;
            case "findfriends":
                displayUsers();
                refreshTotal();
                break;
            case "achievements":
                Parent achieve;
                try {
                    borderpane.getChildren().removeAll();
                    achieve = FXMLLoader.load(getClass().getResource("/achievements.fxml"));
                    borderpane.setCenter(achieve);
                    displayAchievements();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "activities":
                borderpane.getChildren().removeAll();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/activities.fxml"));
                    borderpane.setCenter(root);
                    User user = Client.findCurrentUser();
                    solarPanel = (CheckBox) exit.getScene().lookup("#solarPanel");
                    solarPanel.setSelected(user.isSolarPanel());
                    setDaysOfSolarPanels();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;

        }
        refreshTotal();
    }
    
    @FXML
    void setDaysOfSolarPanels() {
        
        String sessionCookie = LoginController.sessionCookie;
        
        String numberOfDays = Client.getRequest(sessionCookie, "http://localhost:8080/getDaysOfSolarPanel").getBody();
        
        //System.out.println(label);
        //Since we are technically in the wrong scene we can find the label you created like this:
        Label daysOfSolarPanel = (Label)exit.getScene().lookup("#daysOfSolarPanel");
        daysOfSolarPanel.setText(numberOfDays);
    }
    
    
    
    /** Makes sure the total score gets updated.
     *
     */
    @FXML
    void refreshTotal() {
        User user = Client.findCurrentUser();
        scoreRepresentation.setText(Integer.toString(user.getTotalscore()));
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

    void displayAchievements() {
        Achievement[] achievements = Client.getAchievements(Client.findCurrentUser().getEmail());
        for (Achievement a : achievements) {
            System.out.println(a.getAchievement_name());
            String achievementname = a.getAchievement_name();
            switchBadge(achievementname);

        }
    }

    /** Display badge.
     *
     * @param achievementname achievement to show
     */
    private void switchBadge(String achievementname) {
        switch (achievementname) {
            case "Bronze Badge":
                bronze = (ImageView) exit.getScene().lookup("#bronze");
                bronze.setOpacity(1);
                break;
            case "Silver Badge":
                silver = (ImageView) exit.getScene().lookup("#silver");
                silver.setOpacity(1);
                break;
            case "Golden Badge":
                gold = (ImageView) exit.getScene().lookup("#gold");
                gold.setOpacity(1);
                break;
            case "Solar Panel":
                solar = (ImageView) exit.getScene().lookup("#solar");
                solar.setOpacity(1);
                break;
            case "Bike":
                bike = (ImageView) exit.getScene().lookup("#bike");
                bike.setOpacity(1);
                break;
            case "Buying Local":
                local = (ImageView) exit.getScene().lookup("#local");
                local.setOpacity(1);
                break;
            case "Temperature":
                temperature = (ImageView) exit.getScene().lookup("#temperature");
                temperature.setOpacity(1);
                break;
            case "Public Transport":
                transport = (ImageView) exit.getScene().lookup("#transport");
                transport.setOpacity(1);
                break;
            case "Vegetarian Meal":
                vegetarian = (ImageView) exit.getScene().lookup("#vegetarian");
                vegetarian.setOpacity(1);
                break;
            default: break;

        }
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


        Activity[] activities = Client.getActivities();

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
                if (result) {
                    vbox.getChildren().remove(active);
                }
            });
            
            if (a.getActivity_type() == ActType.solar_panel) {
                but.setVisible(false);
            }


            active.getChildren().addAll(activity, co2Label, date, but);
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
            

            HBox hbox = initializeAchievement(user.getEmail());
            hbox.setSpacing(4);
            hbox.setFillHeight(true);
            
            TableUser tableUser =
                    new TableUser(i + 1, user.getEmail(), hbox, user.getTotalscore());
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

    /** gets the achievements and returns hbox with achievements.
     *
     * @param email email of the user
     * @return HBox to display in leaderboard
     */
    private HBox initializeAchievement(String email) {
        Achievement[] achievements = Client.getAchievements(email);
        HBox hbox = new HBox();
        for (Achievement a : achievements) {
            hbox = switchBadgeLeaderboard(a, hbox);
        }
        return hbox;
    }

    /** updates the HBox to contain the achievement.
     *
     * @param a Achievement to add
     * @param hBox HBox to update
     * @return HBox
     */
    private HBox switchBadgeLeaderboard(Achievement a, HBox hBox) {
        switch (a.getAchievement_name()) {
            case "Bronze Badge":
                ImageView bronze = new ImageView(
                        new Image(getClass().getResource("/images/bronze.png").toExternalForm()));
                bronze.setFitHeight(30);
                bronze.setFitWidth(30);
                hBox.getChildren().add(bronze);
                break;
            case "Silver Badge":
                ImageView silver = new ImageView(
                        new Image(getClass().getResource("/images/silver.png").toExternalForm()));
                silver.setFitHeight(30);
                silver.setFitWidth(30);
                hBox.getChildren().add(silver);
                break;
            case "Golden Badge":
                ImageView gold = new ImageView(
                        new Image(getClass().getResource("/images/gold.png").toExternalForm()));
                gold.setFitHeight(30);
                gold.setFitWidth(30);
                hBox.getChildren().add(gold);
                break;
            case "Solar Panel":
                ImageView solar = new ImageView(
                        new Image(getClass().getResource("/images/solar.png").toExternalForm()));
                solar.setFitHeight(30);
                solar.setFitWidth(30);
                hBox.getChildren().add(solar);
                break;
            case "Vegetarian Meal":
                ImageView vegetarian = new ImageView(
                        new Image(getClass().getResource("/images/vegmeal.png").toExternalForm()));
                vegetarian.setFitHeight(30);
                vegetarian.setFitWidth(30);
                hBox.getChildren().add(vegetarian);
                break;
            case "Bike":
                ImageView bike = new ImageView(
                        new Image(getClass().getResource("/images/bike.png").toExternalForm()));
                bike.setFitHeight(30);
                bike.setFitWidth(30);
                hBox.getChildren().add(bike);
                break;
            case"Public Transport":
                ImageView publicTransport = new ImageView(
                        new Image(getClass().getResource("/images/trans.png").toExternalForm()));
                publicTransport.setFitHeight(30);
                publicTransport.setFitWidth(30);
                hBox.getChildren().add(publicTransport);
                break;
            case"Temperature":
                ImageView temp = new ImageView(
                        new Image(getClass().getResource("/images/temp.png").toExternalForm()));
                temp.setFitHeight(30);
                temp.setFitWidth(30);
                hBox.getChildren().add(temp);
                break;
            case"Buying Local":
                ImageView buyLocal = new ImageView(
                        new Image(getClass().getResource("/images/local.png").toExternalForm()));
                buyLocal.setFitHeight(30);
                buyLocal.setFitWidth(30);
                hBox.getChildren().add(buyLocal);
                break;
            default:
                break;
        }
        return hBox;
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
        vbox.setPrefHeight(560);
        vbox.setStyle("-fx-background-color: #8ee4af");
        vbox.setPadding(new Insets(10, 10, 10, 20));
        vbox.setSpacing(10);

        Label error = new Label();
        error.setMinSize(225,30);
        error.setText("  There exists no user under this email.");
        error.setStyle("-fx-background-radius: 10 10 10 10;"
                + "-fx-background-color: #DC143C;"
                + "-fx-font-size:13px;"
                + "-fx-text-fill: #edf5e1;");
        error.setVisible(false);
        TextField findFriends = new TextField();
        findFriends.setMinSize(230, 30);
        findFriends.setPromptText("Find your friends by email.");
        Button lookUp = new Button();
        lookUp.setMinSize(60, 30);
        lookUp.setStyle("-fx-background-radius: 10 10 10 10;"
                + "-fx-background-color: #05386B;"
                + "-fx-font-size: 13px;"
                + "-fx-text-fill: #edf5e1;");
        lookUp.setText("Search");
        HBox search = new HBox();
        search.setSpacing(20);
        search.setPrefHeight(40);

        search.getChildren().addAll(findFriends,lookUp, error);
        vbox.getChildren().add(search);

        User[] allusers = Client.getUsers();
        System.out.println(allusers.length + "AMOUNT OF USERS");
        VBox updatedVbox = populateVbox(allusers, vbox);
        pane.setContent(updatedVbox);
        borderpane.getChildren().removeAll();
        borderpane.setCenter(pane);

        lookUp.setOnAction(event -> {
            try {
                error.setVisible(false);
                String email = findFriends.getText();
                vbox.getChildren().clear();
                vbox.getChildren().add(search);
                User[] lookedUp = searchFriend(email);
                VBox followfriend = populateVbox(lookedUp, vbox);
                pane.setContent(followfriend);
                borderpane.getChildren().removeAll();
                borderpane.setCenter(pane);
            } catch (NullPointerException e) {
                error.setVisible(true);
            }
        });

    }


    private User[] searchFriend(String email) {
        User friend = Client.getUserByEmail(email);
        User[] list = new User[1];
        list[0] = friend;
        return list;

    }

    private VBox populateVbox(User[] users, VBox vbox) {
        User currentUser = Client.findCurrentUser();
        List<User> currentFriends = Arrays.asList(Client.getFriends());
        int index = 0;
        while (index < users.length) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setFillHeight(true);
            hbox.setPrefWidth(600);
            hbox.setPrefHeight(200);
            for (int j = index; j < index + 2; j++) {
                if (j < users.length && (users[j].equals(currentUser))) {
                    System.out.println(users[j].getEmail()
                            + " equals " + currentUser.getEmail());
                    index++;
                    j++;
                }
                if (!(j < users.length)) {
                    break;
                }

                VBox innerv = new VBox();
                innerv.setPrefWidth(270);
                innerv.setPrefHeight(120);
                innerv.setFillWidth(true);
                innerv.setStyle("-fx-border-color: #05386B; "
                        + "-fx-border-width: 5; "
                        + "-fx-border-radius: 7 7 7 7;"
                        + "-fx-background-color: #379683;"
                        + "-fx-background-radius: 10 10 10 10;");
                innerv.setPadding(new Insets(10, 50, 10, 50));
                Label email = new Label(users[j].getEmail());
                email.setStyle("-fx-font-size:15px;");
                email.setPrefWidth(270);
                email.setAlignment(Pos.CENTER);
                Label score = new Label();
                String total = Integer.toString(users[j].getTotalscore());
                score.setText("Score: " + total);
                score.setStyle("-fx-font-size:15px;");
                score.setAlignment(Pos.CENTER);
                score.setPrefWidth(180);
                User user = users[j];
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

                if (!currentFriends.contains(users[j])) {
                    innerv.getChildren().add(button1);
                } else {
                    innerv.getChildren().add(button2);
                }

                innerv.getChildren().addAll(email, score);
                hbox.getChildren().add(innerv);
                System.out.println(index + " index");
                System.out.println(j + "j");

            }
            index += 2;
            vbox.getChildren().add(hbox);


        }
        return vbox;
    }

    /** logs out the current user.
     *
     * @param event button trigger
     * @throws IOException throws exception if the method cannot find
     */
    @FXML
    void handle_logout(ActionEvent event) throws IOException {

        Client.getRequest(LoginController.sessionCookie, "http://localhost:8080//logout");
        Parent login = FXMLLoader.load(getClass().getResource("/login.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(login);
    }

    /** Minimizes the program.
     *
     * @param event button trigger
     */
    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage)content.getScene().getWindow();
        stage.setIconified(true);
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
}
