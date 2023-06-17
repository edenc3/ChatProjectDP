package Commands;

/**
 * Interface for all commands
 */
public interface ICommand {
    public void execute();
    public void undo();//for future uses

}
