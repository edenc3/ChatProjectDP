package il.ac.hit.Design_Pattern_Course.Commands;

/**
 * ICommand interface.
 * Interface for all commands
 * @see ICommand
 */
public interface ICommand {
    public void execute();
    public void undo();//for future uses

}
