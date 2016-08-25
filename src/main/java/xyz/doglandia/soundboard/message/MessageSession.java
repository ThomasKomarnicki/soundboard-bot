package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkomarnicki on 8/24/16.
 */
public class MessageSession {

    public interface MessageResponder{
        /**
         *
         * @param messageSession
         * @param message
         * @return if handled or not
         */
        boolean onMessageReceived(MessageSession messageSession, IMessage message);
    }

    private List<IMessage> messages;

    private String userId;

    private MessageSessionController messageSessionController;
    private MessageResponder messageResponder;

    public MessageSession(IMessage startingMessage, MessageSessionController messageSessionController, MessageResponder messageResponder){
        this.messageSessionController = messageSessionController;
        this.messageResponder = messageResponder;
        this.userId = startingMessage.getAuthor().getID();

        messages = new ArrayList<>();
        messages.add(startingMessage);

        messageResponder.onMessageReceived(this, startingMessage);
    }

//    public IMessage getLastBotMessage(){
//        for(int i = messages.size()-1; i >= 0; i--){
//            if(!messages.get(i).getAuthor().getID().equals(userId)){
//                return messages.get(i);
//            }
//        }
//        return null;
//    }

    public IMessage getLastUserMessage(){
        for(int i = messages.size()-1; i >= 0; i--){
            if(messages.get(i).getAuthor().getID().equals(userId)){
                return messages.get(i);
            }
        }
        return null;
    }

    public boolean AddUserMessage(IMessage message){
        messages.add(message);
        boolean handled = messageResponder.onMessageReceived(this, message);
        if(!handled){
            endSession();
        }
        return handled;
    }

    public void endSession() {
        messageSessionController.endSession(userId);

    }
}
