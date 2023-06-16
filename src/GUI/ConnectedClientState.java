package GUI;

import javax.swing.*;
import java.awt.*;

public class ConnectedClientState implements IState{
    @Override
    //future use
    public boolean convertStatusButton(JButton button) {
        return true;
    }

    public ConnectedClientState(ClientGUI guiRef) {
        guiRef.setConnectButtonColor(Color.GREEN);
        guiRef.setDisconnectButtonColor(Color.GRAY);
        guiRef.startConnection();
    }
}
