package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Created by tdk10 on 7/16/2016.
 */
public interface MessageHandler {

    boolean handleMessage(IMessage message, IChannel chatChannel);
    boolean handleMention(IMessage message, IChannel chatChannel);

    void handleGuildCreated(IGuild guild);
}
