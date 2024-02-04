package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class BillServiceController {

    @FXML
    private TextField bookingIDField;

    @FXML
    private TextField discountField;

    @FXML
    private Text finalAmount;

    @FXML
    private Text guestName;

    @FXML
    private Text numOfRoomsBooked;

    @FXML
    private Text ratePerNight;

    @FXML
    private Text typeOfRooms;
    
    @FXML
    private Text numOfDaysBooked;

    @FXML
    void calculateFinal(ActionEvent event) {
        String discountText = discountField.getText();
        
        if (discountText.isEmpty()) {
            showAlert(AlertType.ERROR, discountField.getScene().getWindow(), "Missing Discount", "Please enter a discount value.");
            return;
        }
        
        double discount = Double.parseDouble(discountText);
        
        if (discount < 0 || discount > 25) {
            showAlert(AlertType.ERROR, discountField.getScene().getWindow(), "Invalid Discount", "Discount must be between 0 and 25.");
            return;
        }
        
        double rate = Double.parseDouble(ratePerNight.getText());
        int numOfDays = Integer.parseInt(numOfDaysBooked.getText());
        
        double totalAmount = rate * numOfDays;
        double discountAmount = (discount / 100) * totalAmount;
        
        if (discountAmount > (0.25 * totalAmount)) {
            showAlert(AlertType.ERROR, discountField.getScene().getWindow(), "Exceeded Discount Limit", "Discount cannot exceed 25%.");
            return;
        }
        
        double finalAmountValue = totalAmount - discountAmount;
        
        finalAmount.setText("Final Amount : " + String.valueOf(finalAmountValue));
    }


    @FXML
    void fetchBill(ActionEvent event) {
    	Window owner = bookingIDField.getScene().getWindow();
    	
    	int bookingId = Integer.parseInt(bookingIDField.getText());
    	
    	database db = new database();
        Reservation booking = db.fetchBookingDetails(bookingId);

        if (booking != null) {
            guestName.setText(booking.getGuestEmail());
            numOfRoomsBooked.setText(String.valueOf(booking.getNumOfRoomsBooked()));
            ratePerNight.setText(String.valueOf(booking.getRatePerNight()));
            typeOfRooms.setText(booking.getRoomType());
            numOfDaysBooked.setText(String.valueOf(booking.getNumOfDaysBooked()));
        } else {
        	 showAlert(AlertType.ERROR, owner, "Booking Not Found", "Please enter a valid booking ID.");
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
    
    

}

