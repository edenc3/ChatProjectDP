package GUI;

import javax.swing.*;

public class DisconnectedClientState implements IState{

    @Override
    public boolean convertStatusButton(JButton button) {
        return false;
    }
}
