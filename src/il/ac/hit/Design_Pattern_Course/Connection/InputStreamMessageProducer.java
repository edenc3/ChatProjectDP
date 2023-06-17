package il.ac.hit.Design_Pattern_Course.Connection;

import il.ac.hit.Design_Pattern_Course.Packets.MessagePacket;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
/**
 * InputStreamMessageProducer class - represents a message producer
 * @ param inputStream - the input stream
 * @ method produce - adds a message to the queue
 */
public class InputStreamMessageProducer extends MessageProducer {
    DataInputStream inputStream;

    public InputStreamMessageProducer(BlockingQueue<MessagePacket> messages, DataInputStream inputStream) {
        /*
        Constructor for InputStreamMessageProducer
         */
        super(messages);
        this.inputStream = inputStream;
    }

    @Override
    public void produce() throws IOException {
        super.produce(MessagePacket.fromJson(inputStream.readUTF()));
    }
}
