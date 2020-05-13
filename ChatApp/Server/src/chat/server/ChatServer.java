package chat.server;

import Bot.SimpleBot;
import network.TCPConnection;
import network.TCPConnectionListener;
import network.data;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {

    public static void main(String[]args){
        new ChatServer();
    }
    private  final ArrayList<TCPConnection> connections =new ArrayList<>();
    private ChatServer(){
        System.out.println("Server running...");
        try(ServerSocket serverSocket=new ServerSocket(8189)){
            while (true){
                try {
                    new TCPConnection(this, serverSocket.accept());

                }catch (IOException e){

                    System.out.println("TCPConnection exeption...");
                }}
        }catch (IOException e){
            throw new RuntimeException(e);

        }
    }


    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection);
    }

    @Override
    public synchronized  void onReceiveString(TCPConnection tcpConnection, String value) {
            SimpleBot simpleBot = new SimpleBot();
            sendToAllConnections(value);
            sendToAllConnections(simpleBot.sayInReturn(value, true));

    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnect: "+tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnections exeption...");
    }

    private void sendToAllConnections(String message){

        System.out.println(message);
        final int lh=connections.size();
        for (int i=0;i<lh;i++){
            connections.get(i).sendMessage(message);
        }
    }
}
