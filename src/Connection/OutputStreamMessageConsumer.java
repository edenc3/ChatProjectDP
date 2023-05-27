package Connection;

import Packets.MessagePacket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class OutputStreamMessageConsumer extends MessageConsumer {
    TaggedOutputStream outputStream;

    public OutputStreamMessageConsumer(BlockingQueue<MessagePacket> messages, TaggedOutputStream outputStream) {
        super(messages);
        this.outputStream = outputStream;
    }

    @Override
    public void consume() throws IOException {
        outputStream.send(super.poll());
    }
}
