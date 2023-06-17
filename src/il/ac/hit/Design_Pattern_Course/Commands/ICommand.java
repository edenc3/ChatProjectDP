package il.ac.hit.Design_Pattern_Course.Commands;

/**
 * Interface for all commands
 */
public interface ICommand {
    public void execute();
    public void undo();//for future uses

}
