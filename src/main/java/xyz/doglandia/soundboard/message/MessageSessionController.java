package xyz.doglandia.soundboard.message;

import java.util.HashMap;

/**
 * Created by tkomarnicki on 8/24/16.
 */
public class MessageSessionController {

    private HashMap<String, MessageSession> sessions;

    public MessageSessionController() {
        this.sessions = new HashMap<>();
    }

    public boolean UserInMessageSession(String userId){
        return sessions.containsKey(userId);
    }
}
