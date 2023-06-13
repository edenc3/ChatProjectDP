import Connection.Connection;
import Packets.MessagePacket;

import javax.swing.*;
import java.io.IOException;
import GUI.ClientGUI;

/**
 * This class is the entry point of the client application.
 * It is responsible for starting the GUI.
 */
public class Client {
    public static void main(String[] args) {
        class GUIGenerator implements Runnable {
            @Override
            public void run() {
                    (new ClientGUI()).start();

            }
        }
        SwingUtilities.invokeLater(new GUIGenerator());
    }
}
