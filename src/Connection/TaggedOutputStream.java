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

    private Boolean shouldSend(String from, String recipient) {
        // Don't send messages from a client to itself
        if (from.equals(this.recipientTag) && !this.recipientTag.equals(TagAll)) {
            return false;
        }

        // Always send if the recipient tag or the recipient are equal to TagAll
        if (this.recipientTag.equals(TagAll) || recipient.equals(TagAll)) {
            return true;
        }

        // Only send the message if the tag is correct
        return this.recipientTag.equals(recipient);
    }

    public void send(MessagePacket message) throws IOException {
        if (!shouldSend(message.getFrom(), message.getRecipient())) {
            return;
        }

        this.outputStream.writeUTF(message.toJson());
    }
}
