package chat.client;

import network.TCPConnection;
import network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private static final String TITLE = "Talker";
    private static final String IP_ADDR = "192.168.43.36";
    private static final int PORT = 8189;
    private static final int HEIGHT = 400;
    private static final int WIDTH = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientWindow();

            }
        });
    }

    private final JTextArea log = new JTextArea();
    private final JTextField fieldNickname = new JTextField("nickname");
    private final JTextField fieldInput = new JTextField();
    private final JCheckBox ai = new JCheckBox("Bot");

    private TCPConnection connection;

    private ClientWindow() {
        setTitle("Talker");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        log.setEditable(false);
        log.setLineWrap(true);

        JScrollPane scrollBar = new JScrollPane(log);
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));

        JButton enter = new JButton("Enter");
        enter.addActionListener(this);
        bp.add(ai);
        bp.add(fieldNickname);
        bp.add(fieldInput);
        bp.add(enter);

        fieldInput.addActionListener(this);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);

        setVisible(true);

        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if(fieldInput.getText().equals("")) return;
        if(fieldInput.getText().trim().length() > 0)
        {
            log.append(fieldInput.getText() + "\n");
        }
        fieldInput.setText(null);
        fieldInput.requestFocusInWindow();
        connection.sendMessage(fieldNickname.getText() + ":" + msg);
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
    public void onDisconnect(TCPConnection tcpConnection) {
        printMessage("Connection close");
    }

    @Override
    public void onExeption(TCPConnection tcpConnection, Exception e) {
        printMessage("Connection exception: " + e);
    }


    private synchronized void printMessage(String msg){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
