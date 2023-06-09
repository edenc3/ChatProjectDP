package il.ac.hit.Design_Pattern_Course.Connection;

import il.ac.hit.Design_Pattern_Course.Packets.MessagePacket;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
/**
 * MessageConsumer class - represents a message consumer
 * @ param messages - the queue of messages using BlockingQueue
 * @ method poll - pull a message from the queue
 * @ method consume - consumes a message
 */

public abstract class MessageConsumer implements Runnable {
    private BlockingQueue<MessagePacket> messages;

    public MessageConsumer(BlockingQueue<MessagePacket> messages) {
        this.messages = messages;
    }

    protected MessagePacket poll() {
        try {
            return messages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract void consume() throws IOException;

    @Override
    public void run() {
        // Consume until an error
        while (true) {
            try {
                consume();
            } catch (Exception e) {
                break;
            }
        }
    }
}
