package gui;

import com.google.gson.Gson;

import client.Client;
import database.entities.ActType;
import database.entities.Activity;
import database.entities.User;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ActivityController {
    Gson gson = new Gson();

    @FXML
    private Slider transportslider;

    @FXML
    private Text transporttext;

    @FXML
    private Slider bikeslider;

    @FXML
    private Text biketext;

    @FXML
    private Label response;
    
    @FXML
    private Label daysOfSolarPanel;

    
    @FXML
    private CheckBox solar;
    
    @FXML
    void setDaysOfSolarPanels(){
        
        String sessionCookie = LoginController.sessionCookie;
        
        String numberOfDays = Client.getRequest(sessionCookie, "/getDaysOfSolarPanel").getBody();
        
        //System.out.println(label);
    
        daysOfSolarPanel.setText(numberOfDays);
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
                        response.setText("Please specify traveled cycling distance");
                        response.setTextFill(Color.RED);
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
                        response.setText("Please specify traveled distance");
                        response.setTextFill(Color.RED);
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
            HttpEntity<String> result = Client.postRequest(LoginController.sessionCookie,"http://localhost:8080/addactivity", params);
            Activity activity1 = gson.fromJson(result.getBody(),Activity.class);
            displayResponse(activity1);

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
        User user = Client.findCurrentUser();
        user.setSolarPanel(checkbox.isSelected());
        Activity activity = Client.updateSolar(user);
        System.out.println("handleSolarActivityLives");
        
        
        
        
//        if (checkbox.isSelected()) {
//            MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
//            Activity activity = new Activity(ActType.solar_panel,
//                    1,Activity.getCurrentDateTimeString());
//
//            params.add("activity",gson.toJson(activity));
//            HttpEntity<String> result = Client.postRequest(LoginController.sessionCookie,
//                    "http://localhost:8080/addactivity",params);
//            displayResponse(activity);
//
//        } else {
//            User user = Client.findCurrentUser();
//            user.setSolarPanel(false);
//            Client.updateSolar(user);
//        }

    }

    /** Displays a response when an activity gets added.
     *
     * @param activity @param activity the activity added
     */
    private void displayResponse(Activity activity) {
        String actType = activity.getActivity_type().toString();
        System.out.print(actType);
        switch (actType) {
            case "vegetarian_meal":
                response.setText("The animals thank you!");
                response.setTextFill(Color.rgb(237,245,225));
                break;
            case "local_produce":
                response.setText("Good Job!");
                response.setTextFill(Color.rgb(237,245,225));
                break;
            case "bike":
                response.setText("Good for the environment and your health!");
                response.setTextFill(Color.rgb(237,245,225));
                break;
            case "public_transport":
                response.setText("Good Job!");
                response.setTextFill(Color.rgb(237,245,225));
                break;
            case "lower_temperature":
                response.setText("Don't get cold");
                response.setTextFill(Color.rgb(237,245,225));
                break;
            case "solar_panel":
                response.setText("Your total co2 saved will increase overtime!");
                response.setTextFill(Color.rgb(237,245,225));
                break;
            default:
                return;
        }
        return;
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

}
