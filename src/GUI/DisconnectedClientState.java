package GUI;

import javax.swing.*;
import java.awt.*;

public class DisconnectedClientState implements IState{

    public DisconnectedClientState(ClientGUI GUIref) {
        GUIref.setConnectButtonColor(Color.GRAY);
        GUIref.setDisconnectButtonColor(Color.RED);
        GUIref.endConnection();
    }

    @Override
    public boolean convertStatusButton(JButton button) {
        return false;
    }
}
