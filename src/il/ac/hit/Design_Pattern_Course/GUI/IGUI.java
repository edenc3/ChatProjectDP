package il.ac.hit.Design_Pattern_Course.GUI;

/**
 * IGUI interface
 * Represents the GUI interface for the application
 * In next versions, will be more windows and more states that will implement this interface
 * @see IGUI
 */
public interface IGUI {
    public void start();
    public IState getState();
    public void setState(IState state);

}
