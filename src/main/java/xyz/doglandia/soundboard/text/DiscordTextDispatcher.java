package xyz.doglandia.soundboard.text;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * Created by tdk10 on 7/21/2016.
 */
public class DiscordTextDispatcher implements TextDispatcher {


    @Override
    public void dispatchText(String message, IChannel chatChannel) {

        try {
            chatChannel.sendMessage(message);
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
}
