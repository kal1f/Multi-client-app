package chat.client.templates;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import network.TCPConnection;
import network.TCPConnectionListener;

import javafx.scene.control.*;
import network.data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatViewController implements Initializable , TCPConnectionListener {

    private static final String IP_ADDR = "192.168.43.36";
    private static final int PORT = 8189;

    private TCPConnection connection;
    @FXML
    private AnchorPane chatPane;
    @FXML
    private TextArea log;
    @FXML
    private TextArea users;
    @FXML
    private TextField fieldInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = new TCPConnection(this, data.ip, data.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadStartView(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("StartView.fxml"));
        chatPane.getChildren().setAll(pane);

    }
    @FXML
    public void sendMessage() {
        String message = fieldInput.getText();
        if(fieldInput.getText().equals("")) return;
        fieldInput.setText(null);
        fieldInput.requestFocus();
        connection.sendMessage(data.name + " : " + message);
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        printMessage("Connection ready...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMessage(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) { printMessage("Connection close"); }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) { printMessage("Connection exception: " + e); }

    private synchronized void printMessage(String msg) {
        log.appendText(msg + '\n');
        log.positionCaret(log.getLength());
    }

    public void buttonPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            sendMessage();
        }
    }
}
