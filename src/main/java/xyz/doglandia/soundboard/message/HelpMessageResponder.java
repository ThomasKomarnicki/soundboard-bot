package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IMessage;
import xyz.doglandia.soundboard.text.TextDispatcher;

/**
 * Created by tkomarnicki on 8/25/16.
 */
public class HelpMessageResponder implements MessageSession.MessageResponder {

    TextDispatcher textDispatcher;

    public HelpMessageResponder(TextDispatcher textDispatcher){
        this.textDispatcher = textDispatcher;
    }

    @Override
    public boolean onMessageReceived(MessageSession messageSession, IMessage message) {
        return false;
    }
}
