package Commands;

import GUI.ClientGUI;
import Packets.MessagePacket;


public class SendCommand implements ICommand{
    MessagePacket _messagePacket;
    ClientGUI _guiRef;
    public SendCommand(ClientGUI guiRef){
        _guiRef = guiRef;
        _messagePacket = new MessagePacket(guiRef.getConnection().getNickname(), guiRef.getTfRecipient().getText(), guiRef.getTf().getText());
    }
    @Override
    public void execute() {
        //Send message to server
        _guiRef.getOutMessages().add(_messagePacket);
    }

    @Override
    public void undo() {//for future uses
        //Undo send message
    }
}
