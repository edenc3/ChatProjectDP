package GUI;
import Packets.MessagePacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Connection.Connection;
import Connection.MessageConsumer;
import Connection.TaggedOutputStream;

class MessageWriter {
    private JTextArea messagesText;

    public MessageWriter(JTextArea messagesText) {
        this.messagesText = messagesText;
    }

    public void write(MessagePacket msg) {
        var room = msg.getRecipient().equals(TaggedOutputStream.TagAll) ? "Broadcast" : "DM";
        messagesText.append(String.format("%s from %s: %s\n", room, msg.getFrom(), msg.getMessage()));
    }
}

class GUIMessageConsumer extends MessageConsumer {
    private MessageWriter writer;

    public GUIMessageConsumer(BlockingQueue<MessagePacket> inMessages, JTextArea messagesText) {
        super(inMessages);
        this.writer = new MessageWriter(messagesText);
    }

    @Override
    public void consume() throws IOException {
        writer.write(poll());
    }
}

public class ClientGUI {
    private JFrame frame;
    private JTextField tf;
    private JTextField tfRecipient;
    private JTextField tfNickname;
    private JTextField tfServer;
    private JTextField tfPort;
    private JButton bt;
    private JTextArea messagesText;
    private JPanel panelSouth, panelCenter, panelNorth;
    private JButton btConnect, btDisconnect;
    private JLabel recipientLabel;
    private JLabel nicknameLabel;
    private JLabel serverLabel;
    private JLabel portLabel;
    private BlockingQueue<MessagePacket> inMessages;
    private BlockingQueue<MessagePacket> outMessages;
    private Connection connection;

    public ClientGUI() {
        frame = new JFrame();
        tf = new JTextField(10);
        tfRecipient = new JTextField(10);
        tfNickname = new JTextField(10);
        tfServer = new JTextField(10);
        tfPort = new JTextField(10);
        bt = new JButton("Send");
        btConnect = new JButton("Connect");
        btDisconnect = new JButton("Disconnect");
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        panelNorth = new JPanel();
        recipientLabel = new JLabel("Recipient:");
        nicknameLabel = new JLabel("Nickname:");
        serverLabel = new JLabel("Server:");
        portLabel = new JLabel("Port:");
        messagesText = new JTextArea(20, 40);

        // In messages - are messages from the server, and should be displayed in the GUI
        inMessages = new LinkedBlockingQueue<>();

        // Out messages - are messages that are written from the GUI and should be sent to the server
        outMessages = new LinkedBlockingQueue<>();

        new Thread(new GUIMessageConsumer(inMessages, messagesText)).start();
    }

    public void start() {
        frame.setLayout(new BorderLayout());
        panelNorth.setBackground(Color.WHITE);
        panelNorth.add(nicknameLabel);
        panelNorth.add(tfNickname);
        panelNorth.add(serverLabel);
        panelNorth.add(tfServer);
        panelNorth.add(portLabel);
        panelNorth.add(tfPort);
        panelNorth.add(btConnect);
        panelNorth.add(btDisconnect);
        panelSouth.setBackground(Color.WHITE);
        panelCenter.setBackground(Color.PINK);
        panelCenter.add(messagesText);
        panelSouth.setLayout(new FlowLayout());
        panelSouth.add(recipientLabel);
        panelSouth.add(tfRecipient);
        panelSouth.add(bt);
        panelSouth.add(tf);
        frame.add(panelNorth,BorderLayout.NORTH);
        frame.add(panelCenter,BorderLayout.CENTER);
        frame.add(panelSouth,BorderLayout.SOUTH);
        frame.setSize(800,500);
        frame.setVisible(true);
        bt.addActionListener(new SendButtonsObserver());
        btConnect.addActionListener(new ConnectButtonsObserver());
        btDisconnect.addActionListener(new DisconnectButtonsObserver());
    }

    class SendButtonsObserver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outMessages.add(new MessagePacket(connection.getNickname(), tfRecipient.getText(), tf.getText()));
            var recipient = tfRecipient.getText().equals(TaggedOutputStream.TagAll) ? "everyone" : tfRecipient.getText();
            messagesText.append(String.format("From me to %s: %s\n", recipient, tf.getText()));
        }
    }

    class ConnectButtonsObserver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                connection = Connection.createClientConnection(tfNickname.getText(), inMessages, outMessages, tfServer.getText(), Integer.parseInt(tfPort.getText()));
                new Thread(connection).start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    class DisconnectButtonsObserver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                connection.close();
                connection = null;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}