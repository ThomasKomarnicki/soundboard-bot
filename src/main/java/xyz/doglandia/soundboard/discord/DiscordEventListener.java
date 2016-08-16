package xyz.doglandia.soundboard.discord;

import xyz.doglandia.soundboard.message.MessageHandler;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.util.MissingPermissionsException;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class DiscordEventListener {

    private MessageHandler messageHandler;

    public DiscordEventListener(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }


    @EventSubscriber
    public void onReady(ReadyEvent event){

    }


    @EventSubscriber
    public void onGuildCreated(GuildCreateEvent event){
//        guild = event.getGuild();
//        messageListener.onGuildConnected(event.getGuild());

//        try {
//            event.getGuild().getVoiceChannelsByName("General").get(0).join();
//        } catch (MissingPermissionsException e) {
//            e.printStackTrace();
//        }
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event){
        String messageContent = event.getMessage().getContent();

        messageHandler.handleMessage(event.getMessage(), event.getMessage().getChannel());

    }

    @EventSubscriber
    public void onMention(MentionEvent event){
        System.out.println("mentioned in "+event.getMessage().getChannel().getName());

        messageHandler.handleMention(event.getMessage(), event.getMessage().getChannel());

    }
}
