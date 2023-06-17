package GUI;
import Commands.CommandManager;
import Commands.SendCommand;
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
    /*
     * This class is responsible for writing messages to the GUI.
     */
    private JTextArea messagesText;

    public MessageWriter(JTextArea messagesText) {
        this.messagesText = messagesText;
    }

    public void write(MessagePacket msg) {
        // If the message is a broadcast, the recipient is TaggedOutputStream.TagAll
        var room = msg.getRecipient().equals(TaggedOutputStream.TagAll) ? "Broadcast" : "DM";
        messagesText.append(String.format("%s from %s: %s\n", room, msg.getFrom(), msg.getMessage()));
    }
}

class GUIMessageConsumer extends MessageConsumer {
    /*
     * This class is responsible for consuming messages from the server.
     */
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

/**
 * This class is the entry point of the client application.
 * It is responsible for starting the GUI for each client.
 * It also creates the connection object and the message consumer object.
 */

public class ClientGUI {
    private JFrame frame;
    private JTextField tf;//text field for writing messages
    private JTextField tfRecipient;
    private JTextField tfNickname;
    private JTextField tfServer;
    private JTextField tfPort;
    private JButton bt;
    private JTextArea messagesText;//text area for displaying messages
    private JPanel panelSouth, panelCenter, panelNorth;
    private JButton btConnect, btDisconnect;
    private JLabel recipientLabel;
    private JLabel nicknameLabel;
    private JLabel serverLabel;
    private JLabel portLabel;
    private BlockingQueue<MessagePacket> inMessages;
    private BlockingQueue<MessagePacket> outMessages;
    private Connection connection;
    private IState state;
    private CommandManager _commandManager;

    public ClientGUI() {
        /*
         * Constructor that initializes the GUI components.
         */
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

        _commandManager = new CommandManager();

        // In messages - are messages from the server, and should be displayed in the GUI
        inMessages = new LinkedBlockingQueue<>();

        // Out messages - are messages that are written from the GUI and should be sent to the server
        outMessages = new LinkedBlockingQueue<>();
        //this Thread is responsible for sending messages to the server from the GUI
        new Thread(new GUIMessageConsumer(inMessages, messagesText)).start();

        //We start the GUI in the disconnected state
        btDisconnect.setBackground(Color.RED);
        btConnect.setBackground(Color.GRAY);


    }
    /*
        * This method is responsible for starting the connection to the server.
        * it public because it is called from the state classes.
        * in the next version of the project, this method will be moved to the state classes, and will be private, for better security.
     */
    public int startConnection() {
        try {
            this.connection = Connection.createClientConnection(tfNickname.getText(), inMessages, outMessages, tfServer.getText(), Integer.parseInt(tfPort.getText()));
            new Thread(this.connection).start();
            return 1;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    /*
     * This method is responsible for closing the connection to the server.
     * it public because it is called from the state classes.
     * in the next version of the project, this method will be moved to the state classes, and will be private, for better security.
     */
    public int endConnection(){
        try {
            connection.close();
            connection = null;
            return 1;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public void setConnectButtonColor(Color color) {
        btConnect.setBackground(color);
    }
    public void setDisconnectButtonColor(Color color) {
        btDisconnect.setBackground(color);
    }


    public void start() {
        /*
         * This method is responsible for starting the GUI, and adding the action listeners to the buttons.
         * by creating a new thread for the connection object.
         */
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

    public JTextField getTf() {
        return this.tf;
    }

    public JTextField getTfRecipient() {
        return this.tfRecipient;
    }

    public JTextField getTfNickname() {
        return this.tfNickname;
    }

    public JTextArea getMessagesText() {
        return this.messagesText;
    }

    public BlockingQueue<MessagePacket> getOutMessages() {
        return this.outMessages;
    }

    public Connection getConnection() {
        return this.connection;
    }

    /**
     * This class is responsible for sending messages from the GUI to the server.
     * It is an inner class of the ClientGUI class.
     * It implements the ActionListener interface.
     * And it overrides the actionPerformed method.
     * The actionPerformed method is called when the user clicks the send button.
     */
    class SendButtonsObserver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //send message to the server
            //before using SendCommand: outMessages.add(new MessagePacket(connection.getNickname(), tfRecipient.getText(), tf.getText()));
            //after using SendCommand:
            //ICommand command = new SendCommand(ClientGUI.this);
            //command.execute();
            // now let's create better practice with object of commandManager
            _commandManager.addCommand(new SendCommand(ClientGUI.this));
            _commandManager.executeCommands();
            //display the message in the GUI
            var recipient = tfRecipient.getText().equals(TaggedOutputStream.TagAll) ? "everyone" : tfRecipient.getText();
            messagesText.append(String.format("From me to %s: %s\n", recipient, tf.getText()));
            //clear the input text field after sending the message
            tf.setText("");

            /*
            public void actionPerformed(ActionEvent e) {
            String message = textArea.getText();

            // Convert the message into a Command object.
            Command command = new SendCommand(message);

            // Execute the command.
            CommandManager.getInstance().executeCommand(command);
            */
        }
    }

    /**
     * This class is responsible for creating the connection object and starting it.
     * It is an inner class of the ClientGUI class.
     * It implements the ActionListener interface.
     * And it overrides the actionPerformed method.
     * The actionPerformed method is called when the user clicks the connect button.
     */
    class ConnectButtonsObserver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //this method using design pattern state
            //it will start connectState, that change to button colors and start connection
            setState(new ConnectedClientState(ClientGUI.this));
        }
    }

    /**
     * This class is responsible for closing the connection object.
     * It is an inner class of the ClientGUI class.
     * It implements the ActionListener interface.
     * And it overrides the actionPerformed method.
     * The actionPerformed method is called when the user clicks the disconnect button.
     */
    class DisconnectButtonsObserver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //this method using design pattern state
            //it will start connectState, that change to button colors and start connection
            setState(new DisconnectedClientState(ClientGUI.this));
        }
    }
}