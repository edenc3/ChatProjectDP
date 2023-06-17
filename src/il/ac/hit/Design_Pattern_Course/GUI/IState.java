package il.ac.hit.Design_Pattern_Course.GUI;

import javax.swing.*;
/**
 * IState interface
 * Represents the state interface for the application
 * In next versions, will be more states that will implement this interface
 * @see IState
 */
public interface IState {
    public boolean convertStatusButton(JButton button);

}
