package Connection;

import Packets.MessagePacket;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * MessageProducer class - represents a message producer
 * @ param messages - the queue of messages using BlockingQueue
 * @ method produce - adds a message to the queue
 */
public abstract class MessageProducer implements Runnable {
    private BlockingQueue<MessagePacket> messages;

    public MessageProducer(BlockingQueue<MessagePacket> messages) {
        this.messages = messages;
    }

    void produce(MessagePacket message) {
        messages.add(message);
    }

    public abstract void produce() throws IOException;

    @Override
    public void run() {
        // Producer until an error
        while (true) {
            try {
                produce();
            } catch (Exception e) {
                break;
            }
        }
    }
}
