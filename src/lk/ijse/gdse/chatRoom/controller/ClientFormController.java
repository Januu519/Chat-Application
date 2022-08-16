package lk.ijse.gdse.chatRoom.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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

    private FileChooser fileChooser;
    private File filePath;
    private PrintStream writer;



    public void galleryAction(MouseEvent mouseEvent) {
        /*System.out.println("clicked camera");*/
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(boxNameLbl.getText() + " " + "img" + filePath.getPath());



    }

    public void handleSendEvent(MouseEvent mouseEvent) throws IOException {
        goMessage();
    }



   public void goMessage() throws IOException {

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

       String sendThing = boxNameLbl.getText()+" : "+typeField.getText();
       dataOutputStream.writeUTF(sendThing.trim());
       dataOutputStream.flush();
       typeField.clear();
    }



    public void enterEvent(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            goMessage();
        }
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
                    System.out.println("last message : "+message);
                    chatBox.appendText(message+"\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
