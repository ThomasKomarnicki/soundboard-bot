package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IMessage;

import java.util.HashMap;

/**
 * Created by tkomarnicki on 8/24/16.
 */
public class MessageSessionController {

    private HashMap<String, MessageSession> sessions;

    public MessageSessionController() {
        this.sessions = new HashMap<>();
    }

    public boolean userInMessageSession(String userId){
        return sessions.containsKey(userId);
    }

    public MessageSession getMessageSession(String userId){
        return sessions.get(userId);
    }

    public void endSession(String userId){
        if(sessions.containsKey(userId)){
            sessions.remove(userId);
        }
    }

    public void startMessageSession(IMessage message, MessageSession.MessageResponder messageResponder){
        MessageSession messageSession = new MessageSession(message, this, messageResponder);
        sessions.put(message.getAuthor().getID(), messageSession);
    }


}
