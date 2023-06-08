package lk.ijse.socket.controller.server;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerUIFormController {
    public AnchorPane ServerSideChatContent;
    public TextArea txtMessageArea;
    public JFXButton btnClientContact;
    public JFXButton btnSetting;
    public JFXButton btnClientLogout;

    public AnchorPane ClientLoginFormContext;
    public JFXTextField txtClientID;
    public String clientName;

    static ServerSocket serverSocket;
    static Socket remoteSocket;
    static DataInputStream dataInputStream;
    static PrintWriter printWriter;
    static ArrayList<ClientHandler> clientHandlerList = new ArrayList<>();

    String message = "";

    public void initialize() {

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started..!");
                txtMessageArea.appendText("Server Started...!\n");

                while (true) {

                    remoteSocket = serverSocket.accept();
                    System.out.println("Remote Socket --> "+remoteSocket);
                    txtMessageArea.appendText("New Client Accepted..!\n");
                    System.out.println("New Client Accepted..!");

                    ClientHandler clientHandler = new ClientHandler(remoteSocket, clientHandlerList);
                    clientHandlerList.add(clientHandler);
                    System.out.println(clientHandlerList.toString());
                    clientHandler.start();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(()->{
            try{
                serverSocket = new ServerSocket(5000);
                txtMessageArea.appendText("Server Started..!");
                System.out.println("Server Started..!");
                while (true) {
                    Socket localSocket = serverSocket.accept();
                    txtMessageArea.appendText("\nConnect New Client..!");
                    System.out.println("Connect New Client..!");
                    ClientHandler connection = new ClientHandler(localSocket, this,"Client");
                    clientHandlerList.add(connection);

                    Thread thread = new Thread(connection);
                    thread.start();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public void broadcast(String message) {
        for (ClientHandler clientHandler : this.clientHandlerList) {
            clientHandler.sendMessage(message);
        }
    }

    public void sendMessage(String clientMessage) {
        if (!clientMessage.trim().equalsIgnoreCase("")) {
            System.out.println("Server Stopped..!");
            System.exit(0);
        } else {
            printWriter.println(LoginClientUIFormController.clientName + " Client says, : " + clientMessage);
            txtMessageArea.setText("");
        }
            if (clientMessage.equalsIgnoreCase("BYE") || (clientMessage.equalsIgnoreCase("logout"))) {
                System.exit(0);
            }
    }*/

    public void btnClientContactOnAction(ActionEvent actionEvent) {
        /*clientName = txtClientID.getText().isEmpty() ? "Unknown" : txtClientID.getText();
        Client.clientName = clientName;
        System.out.println(Client.clientName);
        URL resource = getClass().getResource("lk/ijse/socket/view/ClientUIForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = new Stage();
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();

        ClientLoginFormContext.getScene().getWindow().hide();*/
    }

    public void btnSettingOnAction(ActionEvent actionEvent) {
    }

    public void btnClientLogoutOnAction(ActionEvent actionEvent) {

        /*sendMessage("logout");
        Stage stage = (Stage) btnClientLogout.getScene().getWindow();
        stage.close();*/
    }
}
