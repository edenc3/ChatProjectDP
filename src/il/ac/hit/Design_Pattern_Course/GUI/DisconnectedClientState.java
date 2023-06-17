package il.ac.hit.Design_Pattern_Course.GUI;

import javax.swing.*;
import java.awt.*;
/**
 * DisconnectedClientState class - represents a disconnected client state
 * Implements IState
 * Implements design pattern - State
 * @see IState
 * @see DisconnectedClientState
 * @ param guiRef - the gui reference
 * @ method convertStatusButton - converts the status button
 */
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
