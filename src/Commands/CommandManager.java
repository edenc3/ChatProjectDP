package Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<ICommand> _commands;
    public CommandManager(){
        _commands = new ArrayList<ICommand>();
    }

    public void addCommand(ICommand command){
        _commands.add(command);
    }

    //implement iterator design pattern
    public void executeCommands(){
        //execute all commands
        for (ICommand command : _commands) {
            command.execute();
        }
        //clear commands list
        _commands.clear();
    }
}
