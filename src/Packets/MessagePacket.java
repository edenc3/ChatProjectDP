package Packets;

import com.google.gson.Gson;

public class MessagePacket {
    private String from;
    private String recipient;
    private String message;

    public MessagePacket(String from, String recipient, String message) {
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
        var g = new Gson();
        return g.toJson(this);
    }

    public static MessagePacket fromJson(String data) {
        var g = new Gson();
        return g.fromJson(data, MessagePacket.class);
    }
}
