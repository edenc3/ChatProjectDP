package GUI;

import javax.swing.*;
import java.awt.*;

public class DisconnectedClientState implements IState{

    public DisconnectedClientState(ClientGUI guiRef) {
        guiRef.setConnectButtonColor(Color.GRAY);
        guiRef.setDisconnectButtonColor(Color.RED);
        guiRef.endConnection(); 
    }

    @Override
    public boolean convertStatusButton(JButton button) {
        return false;
    }
}
