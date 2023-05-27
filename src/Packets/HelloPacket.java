package Packets;

import com.google.gson.Gson;

public class HelloPacket {
    private String nickname;

    public HelloPacket(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return this.nickname;
    }

    public String toJson() {
        var g = new Gson();
        return g.toJson(this);
    }

    public static HelloPacket fromJson(String data) {
        var g = new Gson();
        return g.fromJson(data, HelloPacket.class);
    }
}
