package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class RoomController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text numOfRooms;
    
    @FXML
    private TableView<Room> roomTable;

    @FXML
    void initialize() {
        assert numOfRooms != null : "fx:id=\"numOfRooms\" was not injected: check your FXML file 'availableRooms.fxml'.";
        
        TableColumn<Room, Integer> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<Room, String> roomTypeCol = new TableColumn<>("Room Type");
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, Double> roomPriceCol = new TableColumn<>("Room Price");
        roomPriceCol.setCellValueFactory(new PropertyValueFactory<>("rate"));

        roomTable.getColumns().addAll(roomNumberCol, roomTypeCol, roomPriceCol);
        
        database db = new database();
        
        List<Room> rooms = db.fetchRooms();
        int availableRoomsCount = db.countAvailableRooms();

        roomTable.getItems().addAll(rooms);
        numOfRooms.setText(String.valueOf(availableRoomsCount));
    }

}
