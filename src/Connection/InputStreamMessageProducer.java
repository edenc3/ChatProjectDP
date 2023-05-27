package Connection;

import Packets.MessagePacket;
import Utils.ChatException;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class InputStreamMessageProducer extends MessageProducer {
    DataInputStream inputStream;

    public InputStreamMessageProducer(BlockingQueue<MessagePacket> messages, DataInputStream inputStream) {
        super(messages);
        this.inputStream = inputStream;
    }

    @Override
    public void produce() throws IOException {
        super.produce(MessagePacket.fromJson(inputStream.readUTF()));
    }
}
