package GUI;

import javax.swing.*;
import java.awt.*;

public class ConnectedClientState implements IState{
    @Override
    //future use
    public boolean convertStatusButton(JButton button) {
        return true;
    }

    public ConnectedClientState(ClientGUI GUIref) {
        GUIref.setConnectButtonColor(Color.GREEN);
        GUIref.setDisconnectButtonColor(Color.GRAY);
        GUIref.startConnection();
    }
}
