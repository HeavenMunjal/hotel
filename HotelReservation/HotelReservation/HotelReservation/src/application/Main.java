package application;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;




public class Main extends Application {
	private Socket socket;

	private DataOutputStream dout;

	private DataInputStream din;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("/application/MainPage.fxml"));
			primaryStage.setTitle("Holiday Inn");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		  try {

	            socket = new Socket("localhost", 13000);

	            dout = new DataOutputStream(socket.getOutputStream());

	            din = new DataInputStream(socket.getInputStream());

	            new Thread(()->run()).start();

	        }catch(IOException ex) {

	            ex.printStackTrace();

	        }
		
	}
	public void run() {

        try {

            while(true) {

                String text = din.readUTF();

//                ta.appendText(text + "\n");

            }

        }catch(IOException ex) {

            ex.printStackTrace();

        }

    }
	public static void main(String[] args) {
		launch(args);
	}
}

