package lk.ijse.gdse.chatRoom.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public AnchorPane chatContext;
    public JFXTextArea chatBox;
    public TextField typeField;
    public ImageView galleryId;
    public Label boxNameLbl;
    public static String chatName;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;


    public void galleryAction(MouseEvent mouseEvent) {
        System.out.println("clicked camera");

    }

    public void handleSendEvent(MouseEvent mouseEvent) throws IOException {

    }

    public void goMessage() {


    }

    public void enterEvent(KeyEvent keyEvent)  {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boxNameLbl.setText(chatName);

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 2000);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (socket.isConnected()) {
                    String message = dataInputStream.readUTF();
                    chatBox.appendText("\n" + chatName + " : " + message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
