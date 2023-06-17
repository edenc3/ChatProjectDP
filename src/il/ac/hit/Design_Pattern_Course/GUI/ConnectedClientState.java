package il.ac.hit.Design_Pattern_Course.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * ConnectedClientState class - represents a connected client state
 * Implements IState
 * Implements design pattern - State
 * @see IState
 * @see ConnectedClientState
 * @ param guiRef - the gui reference
 * @ method convertStatusButton - converts the status button
 */
public class ConnectedClientState implements IState{

    @Override
    public boolean convertStatusButton(JButton button) {
        //future uses
        return true;
    }

    public ConnectedClientState(ClientGUI guiRef) {
        if(guiRef.startConnection() != 0) {
            guiRef.setConnectButtonColor(Color.GREEN);
            guiRef.setDisconnectButtonColor(Color.GRAY);
        }

    }
}
