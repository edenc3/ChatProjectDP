package GUI;

import javax.swing.*;
import java.awt.*;

public class DisconnectedClientState implements IState{

    @Override
    public boolean convertStatusButton(JButton button) {
        //future uses
        return false;
    }

    public DisconnectedClientState(ClientGUI guiRef) {
        if(guiRef.endConnection() != 0){
            guiRef.setConnectButtonColor(Color.GRAY);
            guiRef.setDisconnectButtonColor(Color.RED);
        }
    }
}
