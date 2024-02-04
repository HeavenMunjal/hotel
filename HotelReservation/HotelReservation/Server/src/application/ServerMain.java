package application;

 

import java.io.DataInputStream;

import java.io.DataOutputStream;

import java.io.IOException;

import java.net.ServerSocket;

import java.net.Socket;

import java.util.Date;

import java.util.Enumeration;

import java.util.Hashtable;

 

import javafx.application.Application;

import javafx.application.Platform;

import javafx.scene.Scene;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.TextArea;

import javafx.stage.Stage;

 

public class ServerMain extends Application{

 

    private TextArea ta = new TextArea();

     private ServerSocket serverSocket;

     

    // Mapping of sockets to output streams

     private Hashtable<Socket,DataOutputStream> outputStreams = new Hashtable<>();

     

     

    @Override

    public void start(Stage ps) throws Exception {

         ta.setWrapText(true);

           

            // Create a scene and place it in the stage

            Scene scene = new Scene(new ScrollPane(ta), 400, 200);

            ps.setTitle("Server"); // Set the stage title

            ps.setScene(scene); // Place the scene in the stage

            ps.show(); // Display the stage

            

            new Thread(() -> listen()).start();

          }

    

     private void listen() {

          try {

            // Create a server socket

            serverSocket = new ServerSocket(13000);

            Platform.runLater(() ->

              ta.appendText("MultiThreadServer started at " + new Date() + '\n'));

 

          while (true) {

            // Listen for a new connection request

            Socket socket = serverSocket.accept();

 

            // Display the client number

            Platform.runLater(() ->

              ta.appendText("Connection from " + socket + " at " + new Date() + '\n'));

 

            // Create output stream

            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

 

            

            outputStreams.put(socket, dout);

 

            // Create a new thread for the connection

            new ServerThread(this, socket);

          }

        }

        catch(IOException ex) {

          ex.printStackTrace();

        }

      }

     

     

    // Used to get the output streams

      Enumeration<DataOutputStream> getOutputStreams(){

          return outputStreams.elements();

      }

 

      // Used to send message to all clients

      void sendToAll(String message){

          

          for (Enumeration<DataOutputStream> e = getOutputStreams(); e.hasMoreElements();){

              DataOutputStream dout = (DataOutputStream)e.nextElement();

              try {

                  // Write message

                  dout.writeUTF(message);

              } catch (IOException ex) {

                  ex.printStackTrace();

              }

          }

      

}

 

     class ServerThread extends Thread {

            private ServerMain server;

            private Socket socket;

 

            /** Construct a thread */

            public ServerThread(ServerMain server, Socket socket) {

                this.socket = socket;

                this.server = server;

                start();

            }

 

            /** Run a thread */

            public void run() {

                try {

                    // Create data input and output streams

                    DataInputStream din = new DataInputStream(socket.getInputStream());

 

                    // Continuously serve the client

                    while (true) {

                        String string = din.readUTF();

 

                        // Send text back to the clients

                        server.sendToAll(string);

 

                        // Add chat to the server jta

                        ta.appendText(string + '\n');

                    }

                }

                catch(IOException ex) {

                    ex.printStackTrace();

                }

            }

        }

        

    public static void main(String[] args) {

        launch(args);

    }

 

}

 