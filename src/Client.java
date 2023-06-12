import Connection.Connection;
import Packets.MessagePacket;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        // In messages - are messages from the server, and should be displayed in the GUI
        var inMessages = new LinkedBlockingQueue<MessagePacket>();

        // Out messages - are messages that are written from the GUI and should be sent to the server
        var outMessages = new LinkedBlockingQueue<MessagePacket>();

        // Be aware of one edge case - when the user sends a broadcast message, the user will also receive that message
        // but if the user sends a message to someone specific, the user won't receive the message
        // (this should be handled in the GUI)

        outMessages.add(new MessagePacket(args[0], args[1], args[2]));

        var conn = Connection.createClientConnection(args[0], inMessages, outMessages, "localhost", 1300);
        new Thread(conn).start();

        while (true) {
            var m = inMessages.take();
            System.out.println(m.getFrom() + ": " + m.getMessage());
        }
    }
}
