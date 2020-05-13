package chat.client.templates;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import network.data;

public class DataViewController {
    @FXML
    private AnchorPane dataPane;
    @FXML
    public TextField server_ip;
    @FXML
    public TextField port;
    @FXML
    public TextField name;
    @FXML
    public String sPort;


    @FXML
    public void toConnect(javafx.event.ActionEvent actionEvent) throws Exception{
        System.out.println("Clicked");
        data.ip = server_ip.getText();
        this.sPort = port.getText();
        data.name = name.getText();
        data.port = Integer.parseInt(sPort);


        AnchorPane pane = FXMLLoader.load(getClass().getResource("/chat/client/templates/ChatView.fxml"));
        dataPane.getChildren().setAll(pane);

    }
}
