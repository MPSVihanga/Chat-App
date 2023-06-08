package socket.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.socket.controller.Client;
import lk.ijse.socket.controller.LoginClientUIFormController;

import java.io.*;
import java.net.Socket;
import static lk.ijse.socket.controller.LoginClientUIFormController.clientName;

public class ClientUIFormController extends Thread{
    public AnchorPane ClientUIFormContext;
    public Text txtClientName;
    public TextField txtMessage;
    public JFXComboBox txtCombo;
    public JFXTextArea txtMessageArea;
    public Pane emogiPane;
    public JFXButton btnClientLogOut;
    public JFXButton btnSendEmoji;
    public JFXButton btnSendAttachment;
    public JFXButton btnSendMessage;

    public static final int BUFFER_SIZE = 500102;
    Socket remoteSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    int filesize = 6022386; // File size Temporary Hardcoded

    long start = System.currentTimeMillis();
    int bytesRead;
    int current = 0;
    private LoginClientUIFormController loginClientUIFormController;

    /*Socket remoteSocket = null;
    PrintWriter printWriter;
    public FileChooser fileChooser;
    public File filePath;*/

    /*public void initialize() throws IOException {
        txtClientName.setText(clientName);
        System.out.println("Client Name is : " + Client.clientName);
        remoteSocket = new Socket(ConnectionUtil.host, ConnectionUtil.port);
        txtMessageArea.appendText("Connected New Client..!\n");
        printWriter = new PrintWriter(remoteSocket.getOutputStream());
        TaskReadThread task = new TaskReadThread(remoteSocket, this);
        Thread thread = new Thread(task);
        thread.start();

        emogiPane.setVisible(false);
    }*/

    public void initialize() {
        connectSocket();
        txtClientName.setText(clientName);
    }

    private void connectSocket() {
        try {
            remoteSocket = new Socket("localhost", 5000);
            System.out.println("Connect with server...!");
            bufferedReader = new BufferedReader(new InputStreamReader(remoteSocket.getInputStream()));
            printWriter = new PrintWriter(remoteSocket.getOutputStream(), true);
            printWriter.println(clientName + " Added...!");
            this.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String msg = bufferedReader.readLine();
                txtMessageArea.appendText(msg + "\n");
                {
                    if (msg.endsWith("jpg")) {
                        rece(msg);
                        System.out.println("image in");
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                printWriter.close();
                remoteSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rece(String file) throws IOException {
        byte[] mybytearray = new byte[filesize];
        InputStream is = remoteSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;

        do {
            bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
            if (bytesRead >= 0) current += bytesRead;
        }

        while (bytesRead > -1);
        bos.write(mybytearray, 0, current);
        bos.flush();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        bos.close();
    }

    public void emoji1OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE03");
    }

    public void emoji2OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83E\uDD17");
    }

    public void emoji3OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("❤️❤️");
    }

    public void emoji4OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE32");
    }

    public void emoji5OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE02");
    }

    public void emoji6OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE25");
    }

    public void emoji7OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE2B");
    }

    public void emoji8OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE24");
    }

    public void btnSendEmogiOnAction(ActionEvent actionEvent) {
        if (!emogiPane.isVisible()) {
            emogiPane.setVisible(true);
            System.out.println("Sending Emoji..!");
        } else {
            emogiPane.setVisible(false);
        }
    }

    public void btnSendAttachmentOnAction(ActionEvent actionEvent) throws IOException {
        /*Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        printWriter.println(txtClientName.getText() +" : "+ filePath.getPath());
        printWriter.flush();*/
        Stage stage = (Stage) btnSendAttachment.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            txtMessage.setText(selectedFile.getAbsolutePath());
        }
        sendAttachment(txtMessage.getText());
    }

    private void sendAttachment(String file_name) throws IOException {
        File myFile = new File(file_name);
        byte[] mybytearray = new byte[(int) myFile.length()];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = remoteSocket.getOutputStream();
        System.out.println("Sending...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
    }

    public void btnSentMessageOnAction(ActionEvent actionEvent) {
        /*PrintWriter printWriter= new PrintWriter(remoteSocket.getOutputStream());
        printWriter.println(Client.clientName + " : " + txtMessage.getText());
        printWriter.flush();
        txtMessage.clear();*/
        sendMessage(txtMessage.getText());
        for (Client client : LoginClientUIFormController.clients) {
            // System.out.println(client.name + " btn Sent");
        }
    }

    public void sendMessage(String msg) {
        if (!msg.trim().equalsIgnoreCase("")) {
            printWriter.println(clientName + ": " + msg);
            txtMessage.setText("");
            if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
                System.exit(0);
            }
        }
    }

    public void btnClientLogOutOnAction(ActionEvent actionEvent) {
       /* URL resource = getClass().getResource("lk/ijse/socket/view/LoginClientUIForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage stage = new Stage();
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();*/
        sendMessage("logout...!");
        Stage stage = (Stage) btnClientLogOut.getScene().getWindow();
        stage.close();
    }
}
