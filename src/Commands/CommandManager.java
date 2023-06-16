package Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<ICommand> commands = new ArrayList<ICommand>();

    public void addCommand(ICommand command){
        commands.add(command);
    }

    //implement iterator design pattern
    public void executeCommands(){
        for (ICommand command : commands) {
            command.execute();
        }
    }
}
