package il.ac.hit.Design_Pattern_Course.GUI;

/**
 * IGUI interface
 * @see IGUI
 */
public interface IGUI {
    public void start();
    public IState getState();
    public void setState(IState state);

}
