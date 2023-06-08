package socket.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class LoginClientUIFormController {
    public AnchorPane ClientLoginFormContext;
    public JFXTextField txtClientID;
    public JFXButton btnConnect;

    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;

    public static String clientName;
    public static ArrayList<Client> clients = new ArrayList<>();

    private void loadClientChat() {
        try {
            Stage exitStage = (Stage) btnConnect.getScene().getWindow();
            exitStage.close();
            URL resource = this.getClass().getResource("lk/ijse/socket/view/ClientUIForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            anableMove(scene,stage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void anableMove(Scene scene, Stage primaryStage) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        scene.setOnMousePressed(event -> {
            xOffset.set(primaryStage.getX() - event.getScreenX());
            yOffset.set(primaryStage.getY() - event.getScreenY());
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset.get());
            primaryStage.setY(event.getScreenY() + yOffset.get());
        });
    }

    private boolean checkClient(String txtClientName) {
        for (Client client : clients) {
            System.out.println("Checking Connect Client..!");
            System.out.println(client.name +" says, : ");

            if (client.name.equalsIgnoreCase(txtClientName)) {
                return false;
            }
        }

        clientName = txtClientName;
        clients.add(new Client(clientName));
        return true;
    }

    public void btnConnectOnAction(ActionEvent actionEvent) {
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

        boolean checkClientResult = checkClient(txtClientID.getText());
        System.out.println(txtClientID.getText() + " txtid ******  " + checkClientResult);
        if (checkClientResult) {
            loadClientChat();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Name Already exsist..!").show();
            txtClientID.setText("");
        }
        for (Client client : clients) {
            System.out.println("onnnn " + client.name);
        }
    }
}
