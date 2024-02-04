package application;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class BookingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField numOfDaysField;

    @FXML
    private TextField numOfGuestsField;

    @FXML
    private Text priceDisplay;

    @FXML
    private Text roomSuggested;

    @FXML
    private ChoiceBox<String> roomTypeSelector;
    



    private Random random = new Random();

    // ...

    private int generateBookingId() {
        // Generate a random integer between 1000 and 9999
        return 1000 + random.nextInt(9000);
    }

    
    @FXML
    void onProceed(ActionEvent event) {
        try {
        	Window owner = numOfDaysField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GuestInfo.fxml"));
            Parent guestRoot = loader.load();

            // Get the controller of the loaded FXML
            GuestInfoController guestInfoController = loader.getController();
            
            int bookingId = generateBookingId();

            // Pass the booking-related data to the GuestInfoController
            guestInfoController.setBookingData(numOfDaysField.getText(), bookingId,
            priceDisplay.getText(), roomSuggested.getText(), roomTypeSelector.getValue());

            Stage guestStage = new Stage();
            guestStage.setTitle("Guest Information");
            guestStage.setScene(new Scene(guestRoot));
            guestStage.show();
            owner.hide();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    


    @FXML
    void initialize() {
        assert numOfDaysField != null : "fx:id=\"numOfDaysField\" was not injected: check your FXML file 'bookRoom.fxml'.";
        assert numOfGuestsField != null : "fx:id=\"numOfGuestsField\" was not injected: check your FXML file 'bookRoom.fxml'.";
        assert priceDisplay != null : "fx:id=\"priceDisplay\" was not injected: check your FXML file 'bookRoom.fxml'.";
        assert roomSuggested != null : "fx:id=\"roomSuggested\" was not injected: check your FXML file 'bookRoom.fxml'.";
        assert roomTypeSelector != null : "fx:id=\"roomTypeSelector\" was not injected: check your FXML file 'bookRoom.fxml'.";
        
        
        ObservableList<String> roomTypes = FXCollections.observableArrayList(
                "Single Room", "Double Room", "Deluxe Room", "Penthouse"
        );
        roomTypeSelector.setItems(roomTypes);
        
//        numOfGuestsField.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateRoomSuggestionAndPrice();
//        });

        // Set up listener for roomTypeSelector
        roomTypeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateRoomSuggestionAndPrice();
        });
    }
    
    private void updateRoomSuggestionAndPrice() {
        // Get the number of guests
    	String numOfGuestsText = numOfGuestsField.getText();
        int numOfGuests = Integer.parseInt(numOfGuestsField.getText());
        
        if (numOfGuestsText.isEmpty()) {
            return;
        }

        // Get the selected room type
        String selectedRoomType = roomTypeSelector.getValue();
        int numOfRooms = 0;  // Set this based on the logic
        double roomRate = 0;  // Set this based on the selected room type

        if ("Single Room".equals(selectedRoomType)) {
            numOfRooms = (numOfGuests + 1) / 2;  // 1 or 2 guests per room
            roomRate = 1000;
        } else if ("Double Room".equals(selectedRoomType)) {
            numOfRooms = (numOfGuests + 3) / 4;  // Up to 4 guests per room
            roomRate = 1500;
        } else if ("Deluxe Room".equals(selectedRoomType)) {
            numOfRooms = (numOfGuests + 5) / 6;  // Up to 6 guests per room
            roomRate = 2000;
        } else if ("Penthouse".equals(selectedRoomType)) {
            numOfRooms = (numOfGuests + 7) / 8;  // Up to 8 guests per room
            roomRate = 5000;
        }

        // Update the roomSuggested Text
        roomSuggested.setText(String.valueOf(numOfRooms));

        double totalPrice = numOfRooms * roomRate;
        priceDisplay.setText(String.valueOf(totalPrice));
    }
    
    


}
