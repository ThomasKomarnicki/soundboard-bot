package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkomarnicki on 8/24/16.
 */
public class MessageSession {

    private String channelId;

    private List<IMessage> messages;

    private String userId;

    public MessageSession(IMessage startingMessage, String channelId){
        this.userId = startingMessage.getAuthor().getID();
        this.channelId = channelId;

        messages = new ArrayList<>();
        messages.add(startingMessage);
    }
}