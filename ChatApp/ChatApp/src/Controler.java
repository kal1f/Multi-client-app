import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import network.TCPConnection;
import network.TCPConnectionListener;

import java.io.IOException;

public class Controler implements TCPConnectionListener {

    @FXML
    public TextArea dialogWn;

    @FXML
    public TextField nickname;

    TCPConnection connection;
    public void onToSendClick(MouseEvent mouseEvent) {
        connection.sendMessage(nickname.getText());
    }

    public void onExitClicked(MouseEvent mouseEvent) {
        try {
            connection = new TCPConnection(this, "127.0.0.1", 8189);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMessage(String msg){
        dialogWn.appendText(msg);

    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMessage(value+"\n\r");
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {

    }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) {

    }
}
