package Packets;

import com.google.gson.Gson;
/**
 * HelloPacket class - represents a hello packet for new clients
 * using Gson library from Google to convert the HelloPacket to json and vice versa
 * @ param nickname - the nickname of the user
 */
public class HelloPacket {
    private String nickname;

    public HelloPacket(String nickname){
        /*
        Constructor for HelloPacket
         */
        this.nickname = nickname;
    }

    public String getNickname(){
        return this.nickname;
    }

    public String toJson() {
        /*
        Converts the HelloPacket to json
         */
        var g = new Gson();
        return g.toJson(this);
    }

    public static HelloPacket fromJson(String data) {
        /*
        Converts the json to HelloPacket
         */
        var g = new Gson();
        return g.fromJson(data, HelloPacket.class);
    }
}
