package Connection;

import Packets.MessagePacket;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * TaggedOutputStream class - represents a tagged output stream
 * @ param recipientTag - the recipient tag
 * @ param outputStream - the output stream
 * @ method send - sends a message packet
 * @ method shouldSend - checks if the message should be sent
 * @ method TagAll - a constant for sending messages to all clients
 */
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
        // send the message only if the tag is correct
        if (!shouldSend(message.getFrom(), message.getRecipient())) {
            return;
        }
        // send the message
        this.outputStream.writeUTF(message.toJson());
    }
}
