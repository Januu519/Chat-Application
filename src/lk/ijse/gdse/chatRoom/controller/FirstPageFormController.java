package lk.ijse.gdse.chatRoom.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstPageFormController implements Initializable {
    public TextField txtName;
    public PasswordField pwdPassword;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message = "";

    public static String userName;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtName.getText();

        Parent parent = FXMLLoader.load(getClass().getResource("../views/ClientForm.fxml"));
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(parent));
        stage2.setResizable(false);
        stage2.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
