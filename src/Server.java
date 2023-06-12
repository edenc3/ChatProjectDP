import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import Connection.Connection;

import Packets.MessagePacket;

class Messages implements Runnable {
    private BlockingQueue<MessagePacket> mainQueue;
    private List<BlockingQueue<MessagePacket>> messagesPerClient;

    public Messages() {
        this.mainQueue = new LinkedBlockingQueue<>();
        this.messagesPerClient = new ArrayList<>();
    }

    public BlockingQueue<MessagePacket> getMainQueue() {
        return this.mainQueue;
    }

    public BlockingQueue<MessagePacket> newClient() {
        // Note that currently queues are never being removed
        var messages = new LinkedBlockingQueue<MessagePacket>();
        this.messagesPerClient.add(messages);
        return messages;
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

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Messages messages;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.messages = new Messages();
    }

    @Override
    public void run() {
        // Forward all messages from the main message queue to all the clients
        new Thread(this.messages).start();

        while (true) {
            try {
                var conn = Connection.createServerConnection(this.serverSocket.accept(), this.messages.getMainQueue(), this.messages.newClient());
                new Thread(conn).start();
            } catch (IOException e) {}
        }
    }

    public static void main(String[] args) throws IOException {
        var server = new Server(1300);
        server.run();
    }
}
