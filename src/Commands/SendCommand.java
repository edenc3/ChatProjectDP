package Commands;

public class SendCommand implements ICommand{
    private String message;

    public SendCommand(String message){
        this.message = message;
    }
    @Override
    public void execute() {
        //Send message to server
    }

    @Override
    public void undo() {//for future uses
        //Undo send message
    }
}
