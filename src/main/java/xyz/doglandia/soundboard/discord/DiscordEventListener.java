package xyz.doglandia.soundboard.discord;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import xyz.doglandia.soundboard.message.MessageHandler;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.util.MissingPermissionsException;

import java.util.List;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class DiscordEventListener {

    private MessageHandler messageHandler;
    private IDiscordClient client;

    public DiscordEventListener(IDiscordClient client, MessageHandler messageHandler){
        this.messageHandler = messageHandler;
        this.client = client;
    }


    @EventSubscriber
    public void onReady(ReadyEvent event){

    }


    @EventSubscriber
    public void onGuildCreated(GuildCreateEvent event){


        messageHandler.handleGuildCreated(event.getGuild());
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

    @EventSubscriber
    public void onRoleChanged(UserRoleUpdateEvent event){
        if(event.getClient().getOurUser().getID().equals(event.getUser().getID())){
            List<IRole> roles = event.getNewRoles();
            messageHandler.handleBotRolesChanged(event.getGuild(), roles);
        }
    }

    @EventSubscriber
    public void onVoiceChannelConnected(UserVoiceChannelJoinEvent event){
        if(event.getUser().getID().equals(client.getOurUser().getID())){
            messageHandler.handleVoiceChannelJoined(event.getChannel());
        }

    }

    @EventSubscriber
    public void onVoiceChannelChanged(UserVoiceChannelMoveEvent event){

        if(event.getUser().getID().equals(client.getOurUser().getID())){
            messageHandler.handleVoiceChannelJoined(event.getNewChannel());
        }
    }
}
