package Connection;

import Packets.MessagePacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * TaggedOutputStream class - represents a tagged output stream
 * @ param outputStream - the output stream
 */
public class OutputStreamMessageConsumer extends MessageConsumer {
    TaggedOutputStream outputStream;

    public OutputStreamMessageConsumer(BlockingQueue<MessagePacket> messages, TaggedOutputStream outputStream) {
        /*
        Constructor for OutputStreamMessageConsumer
         */
        super(messages);
        this.outputStream = outputStream;
    }

    @Override
    public void consume() throws IOException {
        // send the message
        outputStream.send(super.poll());
    }
}
