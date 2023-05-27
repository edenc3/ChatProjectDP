package Connection;

import Packets.MessagePacket;

import java.io.DataOutputStream;
import java.io.IOException;

public class TaggedOutputStream {
    public static String TagAll = "*";

    String recipientTag;
    DataOutputStream outputStream;

    public TaggedOutputStream(String recipientTag, DataOutputStream outputStream) {
        this.recipientTag = recipientTag;
        this.outputStream = outputStream;
    }

    public void send(MessagePacket message) throws IOException {
        // Only send the message if the tag is correct
        // Always send if the recipient tag or the recipient are equal to TagAll
        if (!this.recipientTag.equals(TagAll) && !this.recipientTag.equals(message.getRecipient()) && !message.getRecipient().equals(TagAll)) {
            return;
        }

        this.outputStream.writeUTF(message.toJson());
    }
}
