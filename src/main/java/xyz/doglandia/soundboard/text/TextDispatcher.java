package xyz.doglandia.soundboard.text;

import sx.blah.discord.handle.obj.IChannel;

/**
 * Created by tdk10 on 7/21/2016.
 */
public interface TextDispatcher {

    void dispatchText(String message, IChannel chatChannel);
}
