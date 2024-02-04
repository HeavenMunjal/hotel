package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class GuestInfoController {
	
    @FXML
    private TextField addressBox;

    @FXML
    private TextField emailBox;

    @FXML
    private TextField firstNameBox;

    @FXML
    private TextField lastNameBox;

    @FXML
    private TextField phoneBox;

    @FXML
    private TextField titleBox;
    
    @FXML
    private Text bookingIdDisplay;
    
    private int numOfDays;
    private double price;
    private int roomSuggested;
    private String roomType;
    private int bookingId;
    

    
    public void setBookingData(String numOfDays, int id, String price, String roomSuggested, String roomType) {
        this.numOfDays = Integer.parseInt(numOfDays);
        this.bookingId = id;
        this.price = Double.parseDouble(price);
        this.roomSuggested = Integer.parseInt(roomSuggested);
        this.roomType = roomType;
        
        bookingIdDisplay.setText(String.valueOf(bookingId));
    }

    @FXML
    void onConfirmBooking(ActionEvent event) {
    	
        Window owner = emailBox.getScene().getWindow();
        String title = titleBox.getText();
        String firstName = firstNameBox.getText();
        String lastName = lastNameBox.getText();
        String address = addressBox.getText();
        int phone = Integer.parseInt(phoneBox.getText()); 
        String email = emailBox.getText();
        
        if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, owner, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        Guest guest = new Guest(title, firstName, lastName, address, phone, email);
        
        database db = new database();
        
        boolean roomsAvailable = db.checkRoomsAvailability(roomType, roomSuggested);
        
        if (roomsAvailable) {
            boolean guestInserted = db.insertGuest(guest);

            if (guestInserted) {
            	
            }
                Reservation booking = new Reservation(bookingId, email, roomSuggested, numOfDays, price, roomType);
                boolean bookingCreated = db.insertBooking(booking);

                if (bookingCreated) {
                    boolean roomsDeleted = db.deleteRooms(roomType, roomSuggested);
                    if (roomsDeleted) {
                        showAlert(Alert.AlertType.CONFIRMATION, owner, "Booking Created", "Thank You");
                        owner.hide();
                    } else {
                        showAlert(Alert.AlertType.ERROR, owner, "Rooms Not Deleted", "Failed to delete rooms from the database.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, owner, "Booking Not Created", "Failed to create booking in the database.");
                }
            }
        else {
            showAlert(Alert.AlertType.WARNING, owner, "Rooms Not Available", "Required rooms of type " + roomType + " are not available.");
        }
        }
    
    private void showAlert(Alert.AlertType alt, Window win, String title, String msg) {
        Alert alert = new Alert(alt);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.initOwner(win);
        alert.showAndWait();
		
	}
    

    private boolean isValidEmail(String email) {
    // Define a regular expression for email validation
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return email.matches(emailRegex);
    }
    
    
}
