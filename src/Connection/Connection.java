package Connection;

import Packets.HelloPacket;
import Packets.MessagePacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Connection implements Runnable {
    private Socket socket;
    private MessageConsumer consumer;
    private MessageProducer producer;

    private Connection(Socket socket, MessageConsumer consumer, MessageProducer producer) {
        this.socket = socket;
        this.consumer = consumer;
        this.producer = producer;
    }

    public static Connection createClientConnection(String nickname, BlockingQueue<MessagePacket> inMessages, BlockingQueue<MessagePacket> outMessages, String address, int port) throws IOException {
        var socket = new Socket(address, port);

        // Produce messages from socket's input and write them to the `inMessages` queue for the GUI to use
        var inputStream = new DataInputStream(socket.getInputStream());
        var producer = new InputStreamMessageProducer(inMessages, inputStream);

        // Consume messages from `outMessages` and write them to the tagged output stream
        var outputStream = new DataOutputStream(socket.getOutputStream());
        // Send everything - there isn't a real purpose for the tagged output stream here
        var taggedOutputStream = new TaggedOutputStream(TaggedOutputStream.TagAll, outputStream);
        var consumer = new OutputStreamMessageConsumer(outMessages, taggedOutputStream);

        // Packet hello - send client information to the server
        outputStream.writeUTF(new HelloPacket(nickname).toJson());

        return new Connection(socket, consumer, producer);
    }

    public static Connection createServerConnection(Socket socket, BlockingQueue<MessagePacket> mainQueue, BlockingQueue<MessagePacket> individualQueue) throws IOException {
        var inputStream = new DataInputStream(socket.getInputStream());

        // Packet hello - get client information from the input stream
        var helloPacket = HelloPacket.fromJson(inputStream.readUTF());

        // Produce messages from socket's input and write them to the `mainQueue` queue
        var producer = new InputStreamMessageProducer(mainQueue, inputStream);

        // Consume messages from `individualQueue` and write them to the tagged output stream
        var outputStream = new DataOutputStream(socket.getOutputStream());
        var taggedOutputStream = new TaggedOutputStream(helloPacket.getNickname(), outputStream);
        var consumer = new OutputStreamMessageConsumer(individualQueue, taggedOutputStream);

        return new Connection(socket, consumer, producer);
    }

    @Override
    public void run() {
        // Run the consumer on a separate thread while running the producer on the current thread
        new Thread(consumer).start();
        producer.run();
    }

    public void close() throws IOException {
        this.socket.close();
    }
}
