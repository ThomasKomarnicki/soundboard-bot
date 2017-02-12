package xyz.doglandia.soundboard.discord;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserRoleUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelEvent;
import sx.blah.discord.handle.obj.IRole;
import xyz.doglandia.soundboard.exception.SoundboardExceptionHandler;
import xyz.doglandia.soundboard.message.MessageHandler;
import sx.blah.discord.api.events.EventSubscriber;

import java.util.List;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class DiscordEventListener {

    private MessageHandler messageHandler;
    private IDiscordClient client;

    private SoundboardExceptionHandler exceptionHandler;

    public DiscordEventListener(IDiscordClient client, MessageHandler messageHandler){
        this.messageHandler = messageHandler;
        this.client = client;

        exceptionHandler = new SoundboardExceptionHandler();
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
        try {
            String messageContent = event.getMessage().getContent();
            messageHandler.handleMessage(event.getMessage(), event.getMessage().getChannel());
        }catch (Exception e){
            e.printStackTrace();
            exceptionHandler.handleException(e);
        }

    }

    @EventSubscriber
    public void onMention(MentionEvent event){
        try {
            System.out.println("mentioned in " + event.getMessage().getChannel().getName());
            messageHandler.handleMention(event.getMessage(), event.getMessage().getChannel());
        }
        catch (Exception e){
            exceptionHandler.handleException(e);
        }

    }

    @EventSubscriber
    public void onRoleChanged(UserRoleUpdateEvent event){
        if(event.getClient().getOurUser().getID().equals(event.getUser().getID())){
            List<IRole> roles = event.getNewRoles();
            messageHandler.handleBotRolesChanged(event.getGuild(), roles);
        }
    }

    @EventSubscriber
    public void onVoiceChannelConnected(UserVoiceChannelEvent event){
        try{
            if(event.getUser().getID().equals(client.getOurUser().getID())){
                messageHandler.handleVoiceChannelJoined(event.getVoiceChannel());
            }
        }
        catch (Exception e){
            exceptionHandler.handleException(e);
        }

    }

    @EventSubscriber
    public void onVoiceChannelChanged(sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent event){

        try {
            if (event.getUser().getID().equals(client.getOurUser().getID())) {
                messageHandler.handleVoiceChannelJoined(event.getNewChannel());
            }
        }
        catch (Exception e){
            exceptionHandler.handleException(e);
        }
    }
}
