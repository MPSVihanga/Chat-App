package lk.ijse.socket.controller.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private final ArrayList<ClientHandler> clients;
    private final Socket localSocket;
    private final BufferedReader bufferedReader;
    private final PrintWriter printWriter;

    public ClientHandler (Socket localSocket,ArrayList<ClientHandler> clients) throws IOException {
        this.clients = clients;
        this.localSocket = localSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
        this.printWriter = new PrintWriter(localSocket.getOutputStream(), true);
    }

    /*public void sendMessage(String message) {
        try {
            PrintWriter printWriter = new PrintWriter(localSocket.getOutputStream());
            printWriter.println(message);
            System.out.println("Client Message : "+message);
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void run() {
       /* try {
            InputStreamReader inputStreamReader = new InputStreamReader(localSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while (true) {
                String readLine = bufferedReader.readLine();
                System.out.println("Client Send Message : " + readLine);
                serverUIFormController.broadcast(readLine);
                serverUIFormController.txtMessageArea.appendText(readLine + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                localSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/
        try {
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                if (message.equalsIgnoreCase( "exit")) {
                    break;
                }
                for (ClientHandler cl : clients) {
                    cl.printWriter.println(message);
                    System.out.println(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                printWriter.close();
                localSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
