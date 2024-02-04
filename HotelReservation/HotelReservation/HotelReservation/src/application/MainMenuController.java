package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button exitBtn;

    @FXML
    void onAvailableRooms(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("availableRooms.fxml"));
            Parent roomRoot = loader.load();

            Stage roomStage = new Stage();
            roomStage.setTitle("Available Rooms");
            roomStage.setScene(new Scene(roomRoot));
            roomStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBillService(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BillService.fxml"));
            Parent billRoot = loader.load();

            Stage billStage = new Stage();
            billStage.setTitle("Billing Service");
            billStage.setScene(new Scene(billRoot));
            billStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onBookARoom(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookRoom.fxml"));
            Parent bookRoot = loader.load();

            Stage bookStage = new Stage();
            bookStage.setTitle("Book A Room");
            bookStage.setScene(new Scene(bookRoot));
            bookStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onCurrentBookings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("currentBookings.fxml"));
            Parent bookingsRoot = loader.load();

            Stage bookingsStage = new Stage();
            bookingsStage.setTitle("Available Bookings");
            bookingsStage.setScene(new Scene(bookingsRoot));
            bookingsStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onExit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void initialize() {

    }

}
