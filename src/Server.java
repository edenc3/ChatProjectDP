import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import Connection.Connection;

import Packets.MessagePacket;

/**
 * This class implements the Iterator design pattern.
 * This class is responsible for storing all the messages that are sent by the clients.
 * It has a list of queues, one for each client, that stores the messages that are sent to each client.
 * The list of queues is used to later forward the messages to each individual client.
 * @ param messagesPerClient The list of queues, one for each client, that stores the messages that are sent to each client
 *
 * implement iterator design pattern
 */
class ClientsMessages implements Iterable<BlockingQueue<MessagePacket>> {
    private List<BlockingQueue<MessagePacket>> messagesPerClient;

    public ClientsMessages() {
        this.messagesPerClient = new ArrayList<>();
    }

    @Override
    public Iterator<BlockingQueue<MessagePacket>> iterator() {
        return messagesPerClient.iterator();
    }

    public BlockingQueue<MessagePacket> newClient() {
        // Note that currently queues are never being removed
        var messages = new LinkedBlockingQueue<MessagePacket>();
        this.messagesPerClient.add(messages);
        return messages;
    }
}

/**
 * This class is responsible for storing all the messages that are sent by the clients.
 * It has a main queue that stores all the messages that are sent by the clients.
 * It also has a list of queues, one for each client, that stores the messages that are sent to each client.
 * The main queue is used to forward the messages to all the clients.
 * The list of queues is used to forward the messages to each individual client.
 * @ param mainQueue The main queue that stores all the messages that are sent by the clients
 * @ param messagesPerClient The list of queues, one for each client, that stores the messages that are sent to each client
 */
class Messages implements Runnable {
    private BlockingQueue<MessagePacket> mainQueue;
    private ClientsMessages messagesPerClient;

    public Messages() {
        //constructor for the messages class
        this.mainQueue = new LinkedBlockingQueue<>();
        this.messagesPerClient = new ClientsMessages();
    }

    public BlockingQueue<MessagePacket> getMainQueue() {
        return this.mainQueue;
    }

    public BlockingQueue<MessagePacket> newClient() {
        return this.messagesPerClient.newClient();
    }

    // Forwards the messages from the main queue to each individual client
    public void forward() throws InterruptedException {
        while (true) {
            var message = this.mainQueue.take();
            for (var messages : this.messagesPerClient) {
                messages.add(message);
            }
        }
    }

    @Override
    public void run() {
        try {
            forward();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * This class is responsible for creating a server socket and listening for new connections.
 * When a new connection is established, it creates a new thread to handle the connection.
 * It also creates a new thread to forward all the messages from the main message queue to all the clients.
 * @ param serverSocket The server socket to listen on
 * @ param messages The messages object that stores all the messages that are sent by the clients
 */
public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Messages messages;

    public Server(int port) throws IOException {
        /*
        Constructor for the server class
        param port The port to listen on
         */
        this.serverSocket = new ServerSocket(port);
        this.messages = new Messages();
    }

    @Override
    public void run() {
        /*
        Forward all messages from the main message queue to all the clients
        This is done in a separate thread so that the server can continue to listen for new connections
        param conn The connection object that handles the connection with the client
         */
        new Thread(this.messages).start();

        while (true) {
            try {
                var conn = Connection.createServerConnection(this.serverSocket.accept(), this.messages.getMainQueue(), this.messages.newClient());
                new Thread(conn).start();
            } catch (IOException e) {}
        }
    }

    public static void main(String[] args) throws IOException {
        /*
        Create a new server and run it
         */
        var server = new Server(1300);
        server.run();
    }
}
