package il.ac.hit.Design_Pattern_Course;

import javax.swing.*;

import il.ac.hit.Design_Pattern_Course.GUI.ClientGUI;

/**
 * This class is the entry point of the client application.
 * It is responsible for starting the il.ac.hit.Design_Pattern_Course.GUI.
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
