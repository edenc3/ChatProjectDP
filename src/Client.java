import Connection.Connection;
import Packets.MessagePacket;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        var inMessages = new LinkedBlockingQueue<MessagePacket>();
        var outMessages = new LinkedBlockingQueue<MessagePacket>();

        outMessages.add(new MessagePacket(args[0], args[1], args[2]));

        var conn = Connection.createClientConnection(args[0], inMessages, outMessages, "localhost", 9999);
        new Thread(conn).start();

        while (true) {
            var m = inMessages.take();
            System.out.println(m.getFrom() + ": " + m.getMessage());
        }
    }
}
