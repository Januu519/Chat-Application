package lk.ijse.gdse.chatRoom.util;

import lk.ijse.gdse.chatRoom.controller.FirstPageFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName;
    private String message;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.clientName = FirstPageFormController.name;
            clientHandlers.add(this);

            broadcastMessage(clientName +" is connected to the chat...!");
        } catch (IOException e) {
            closeAll(socket);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try {
                messageFromClient =dataInputStream.readUTF();
                System.out.println("in run method : "+messageFromClient);
                broadcastMessage(messageFromClient);

            } catch (IOException e) {
                closeAll(socket);
                break;
            }

    }
}
    public void broadcastMessage(String message){
        serch();
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.dataOutputStream.writeUTF(message+"\n");
                clientHandler.dataOutputStream.flush();
            } catch (IOException e) {
                closeAll(socket);
            }
        }
    }

    private void serch() {
        for (ClientHandler clientHandler:clientHandlers) {
            Socket s = clientHandler.socket;
           // System.out.println("in for each : "+s);
            String name = clientHandler.clientName;
         //   System.out.println("client name is : "+name);
            String msg = clientHandler.message;
           // System.out.println("client mesage is : "+msg);
        }
    }

    public void removeClientHandler (){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+clientName+" has left from the chat");
    }

    private void closeAll(Socket socket) {
        removeClientHandler();

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

