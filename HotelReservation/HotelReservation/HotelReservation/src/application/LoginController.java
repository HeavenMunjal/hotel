package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    

    @FXML
    void validateLogin(ActionEvent event) {
        Window owner = loginButton.getScene().getWindow();
        
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error", "Please enter both username and password");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        Login login = new Login(username, password); // Create a Login object

        database db = new database();
        boolean flag = db.validateAdmin(login); // Pass the Login object to the validateAdmin function
        
        if (flag) {
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Login Successful", "Thank you");
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                Parent menuRoot = loader.load();

                Stage menuStage = new Stage();
                menuStage.setTitle("Main Menu");
                menuStage.setScene(new Scene(menuRoot));
                menuStage.show();

                // Close the current window (if needed)
               loginButton.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
        	showAlert(Alert.AlertType.ERROR, owner, "Login Failed", "Try again");
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

	@FXML
    void initialize() {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Login.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'Login.fxml'.";

    }

}
