package socket.controller.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TaskReadThread implements Runnable{
    Socket remoteSocket;
    ClientUIFormController clientUIFormController;
    BufferedReader bufferedReader;

    public TaskReadThread(Socket remoteSocket, ClientUIFormController clientUIFormController) {
        this.remoteSocket = remoteSocket;
        this.clientUIFormController = clientUIFormController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
                String message = bufferedReader.readLine();
                clientUIFormController.txtMessageArea.appendText(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    remoteSocket.close();
                    bufferedReader.close();
                    System.out.println("Close Remote Socket..!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Error Connection..!");
            }
        }
    }
}
