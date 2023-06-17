package il.ac.hit.Design_Pattern_Course.Packets;

import com.google.gson.Gson;
/**
 * MessagePacket class - represents a message packet
 * using Gson library from Google to convert the MessagePacket to json and vice versa
 * @ param from - the sender of the message
 * @ param recipient - the recipient of the message
 * @ param message - the message
 */

public class MessagePacket {
    private String from;
    private String recipient;
    private String message;

    public MessagePacket(String from, String recipient, String message) {
        /*
        Constructor for MessagePacket
         */
        this.from = from;
        this.recipient = recipient;
        this.message = message;
    }

    public String getFrom() {
        return this.from;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getMessage() {
        return this.message;
    }

    public String toJson() {
        /*
        Converts the MessagePacket to json
         */
        var g = new Gson();
        return g.toJson(this);
    }

    public static MessagePacket fromJson(String data) {
        /*
        Converts the json to MessagePacket
         */
        var g = new Gson();
        return g.fromJson(data, MessagePacket.class);
    }
}
