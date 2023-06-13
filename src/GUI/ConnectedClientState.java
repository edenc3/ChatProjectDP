package GUI;

import javax.swing.*;

public class ConnectedClientState implements IState{
    @Override
    public boolean convertStatusButton(JButton button) {
        return true;
    }
}
