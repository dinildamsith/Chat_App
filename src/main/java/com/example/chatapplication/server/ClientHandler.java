package com.example.chatapplication.server;

import com.example.chatapplication.controller.ChatFormController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    public static final List<ClientHandler> clientHandlerList = new ArrayList<>();
    public static ChatFormController chatFormController;
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final String clientName;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientName = inputStream.readUTF();
        clientHandlerList.add(this);
    }

    public static void broadcast(String msg) throws IOException {
        for (ClientHandler handler : clientHandlerList) {
            handler.sendMessage("SERVER", msg);
        }
    }

    @Override
    public void run() {
       l1: while (socket.isConnected()) {
            try {
                String utf = inputStream.readUTF();
                if (utf.equals("*image*")) {
                    receiveImage();
                } else {
                    for (ClientHandler handler : clientHandlerList) {
                        if (!handler.clientName.equals(clientName)) {
                            handler.sendMessage(clientName, utf);
                        }
                    }
                }
            } catch (IOException e) {

                clientHandlerList.remove(this);
//                System.out.println(clientName+" removed");
//                System.out.println(clientHandlerList.size());
                break;
            }
        }
    }

    public void sendMessage(String sender, String msg) throws IOException {
        outputStream.writeUTF(sender + ": " + msg);
        outputStream.flush();
    }

    private void receiveImage() throws IOException {
        int size = inputStream.readInt();
        byte[] bytes = new byte[size];
        inputStream.readFully(bytes);
        for (ClientHandler handler : clientHandlerList) {
            if (!handler.clientName.equals(clientName)) {
                handler.sendImage(clientName, bytes);
//                System.out.println(clientName+" - image sent ");
            }
        }
    }

    private void sendImage(String sender, byte[] bytes) throws IOException {
        outputStream.writeUTF("*image*");
        outputStream.writeUTF(sender);
        outputStream.writeInt(bytes.length);
        outputStream.write(bytes);
        outputStream.flush();
//        System.out.println("Image Sent");
    }
}
