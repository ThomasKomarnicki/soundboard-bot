package xyz.doglandia.soundboard.message;

import com.brsanthu.googleanalytics.EventHit;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.doglandia.soundboard.analytics.Analytics;
import xyz.doglandia.soundboard.audio.AudioDispatcher;
import xyz.doglandia.soundboard.data.DataController;
import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardAlreadyExistsException;
import xyz.doglandia.soundboard.exception.SoundboardExceptionHandler;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.ClipAlias;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.text.TextDispatcher;
import xyz.doglandia.soundboard.util.Sensitive;
import xyz.doglandia.soundboard.util.Util;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xyz.doglandia.soundboard.message.MessageParams.Keys.*;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class MessageHandlerImpl implements MessageHandler {

    AudioDispatcher audioDispatcher;
    TextDispatcher textDispatcher;
    DataController dataController;

    MessageSessionController messageSessionController;

    public MessageHandlerImpl(AudioDispatcher audioDispatcher, TextDispatcher textDispatcher, DataController dataController){
        this.textDispatcher = textDispatcher;
        this.audioDispatcher = audioDispatcher;
        this.dataController = dataController;

        messageSessionController = new MessageSessionController();
    }

    @Override
    public boolean handleMessage(IMessage message, IChannel chatChannel) {

        if(messageSessionController.userInMessageSession(message.getAuthor().getID())){

            boolean messageSessionHandled = messageSessionController.getMessageSession(message.getAuthor().getID()).AddUserMessage(message);

            if(messageSessionHandled){
                return true;
            }
        }

        MessageParams messageParams = new MessageParams(message);

        switch (messageParams.getType()){
            case NONE:
                return false;
            case ADD_CLIP:
                return addClip(message, messageParams.getParam(SOUNDBOARD_NAME), messageParams.getParam(CLIP_NAME), messageParams.getParam(CLIP_URL));
            case ADD_SOUNDBOARD:
                return createSoundboard(message, messageParams.getParam(SOUNDBOARD_NAME));
            case PLAY_CLIP:
                return handleSoundClipRequest(message, messageParams.getParam(SOUNDBOARD_NAME), messageParams.getParam(CLIP_NAME));
            case HELP:
                return handleHelpRequest(message, messageParams.getParam(HELP_PARAM));
            case JOIN_CHANNEL:
                joinChannel(message);
                break;
            case STOP_AUDIO:
                audioDispatcher.stopAllAudio(message);
                break;
            case LIST_SOUNDBOARD:
                listSoundboards(chatChannel);
                break;
            case ADD_ALIAS:
                break;
        }

//        checkForClipAliasMatch(message, chatChannel);

        return true;

    }

    private void checkForClipAliasMatch(IMessage message, IChannel channel){
        GuildOptions guildOptions = dataController.getGuildOptionsById(channel.getGuild().getID());
        ClipAlias clipAlias = guildOptions.getClipAliasCollection().getMatchingAliasForText(message.getContent()); // returns null if no match
        if(clipAlias != null){
            String url = clipAlias.getSoundClip().getUrl();
            audioDispatcher.playAudioClip(message, url);
        }
    }


    @Override
    public boolean handleMention(IMessage message, IChannel chatChannel) {
        return false;
    }

    @Override
    public void handleGuildCreated(IGuild guild) {
        boolean newlyCreated = dataController.initGuild(guild.getID());

        GuildOptions guildOptions = dataController.getGuildOptionsById(guild.getID());
        String lastConnectedId = guildOptions.getLastConnectedChannelId();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000); // wait for it to be ready aka todo
                    IVoiceChannel voiceChannel = null;
                    if(lastConnectedId != null){
                        // find voice channel
                        voiceChannel = guild.getVoiceChannelByID(lastConnectedId);
                    }
                    if(voiceChannel == null) {
                        if (guild.getVoiceChannels().size() > 0) {
                            voiceChannel = guild.getVoiceChannels().get(0);
                        }
                    }
                    if(voiceChannel != null){
                        try {
                            voiceChannel.join();
                        } catch (MissingPermissionsException e) {
                            SoundboardExceptionHandler.Instance.handleException(e);
                            e.printStackTrace();

                            notifyServerOwnerAboutMissingPermissions(guild);

                        }
                    }
                } catch (InterruptedException e) {
                    SoundboardExceptionHandler.Instance.handleException(e);
                    e.printStackTrace();
                }
            }
        }).start();


        if(newlyCreated) {
            IChannel defaultTextChannel = findDefaultChatChannel(guild);
            Analytics.sendEvent(new EventHit("Guild","GuildCreated",guild.getName(),1));
            if (defaultTextChannel != null) {
                textDispatcher.dispatchText(HelpMessageResponder.GET_STARTED, defaultTextChannel);
            }
        }

    }

    private IChannel findDefaultChatChannel(IGuild guild){
       List<IChannel> generals = guild.getChannelsByName("General");
        if(generals != null && generals.size() > 0){
            return generals.get(0);
        }

        for(IChannel channel : guild.getChannels()){
            Map<String, IChannel.PermissionOverride> permissionsMap = channel.getRoleOverrides();
            EnumSet<Permissions> permissions = channel.getModifiedPermissions(guild.getEveryoneRole());
            if(permissions.contains(Permissions.SEND_MESSAGES)){
                return channel;
            }
        }

        return null;
    }

    @Override
    public void handleBotRolesChanged(IGuild guild, List<IRole> roles) {

        List<String> roleNames = roles.stream().map(IRole::getName).collect(Collectors.toList());

        dataController.setGuildPrivilegedRoles(guild.getID(), roleNames);
    }

    @Override
    public void handleVoiceChannelJoined(IVoiceChannel channel) {
        // todo do not like the fact that data provider is exposed to update the last connected voice channel
        GuildOptions guildOptions = dataController.getGuildOptionsById(channel.getGuild().getID());
        guildOptions.setLastConnectedChannelId(channel.getID());
        dataController.updateGuildOptions(guildOptions);
    }

    private boolean createSoundboard(IMessage message, String soundboardName){

        try {
            dataController.createSoundboard(Util.getGuildFromUserMessage(message).getID(), soundboardName);
            Analytics.sendEvent(new EventHit("Guild","CreateSoundboard",message.getGuild().getName()+"-"+soundboardName,1));
            // todo do feedback here
            textDispatcher.dispatchText("soundboard \""+soundboardName+"\" created! Type: \n*!help "+soundboardName+"* \nto get started", message.getChannel());
            return true;
        } catch (SoundboardAlreadyExistsException e) {
            e.printStackTrace();
            textDispatcher.dispatchText("soundboard \""+soundboardName+"\" already exists", message.getChannel());
            return false;
        }
    }

    /**
     *
     * @param soundboardName
     * @param clipName
     * @param clipUrl
     * @return if the soundclip was added successfully
     */
    private boolean addClip(IMessage message, String soundboardName, String clipName, String clipUrl){
        String guildId = Util.getGuildFromUserMessage(message).getID();
        try {
            dataController.saveSoundFileToSoundboard(guildId, clipUrl, soundboardName, clipName);
            textDispatcher.dispatchText("Clip added! *!"+soundboardName+" "+clipName+"*", message.getChannel());

            Analytics.sendEvent(new EventHit("Soundboard","ClipPlay",message.getGuild().getName()+"-"+soundboardName+"-"+clipName,1));
            return true;
        } catch (SoundboardExistException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAudioClipException e) {
            e.printStackTrace();
        }

        textDispatcher.dispatchText("there was a problem adding the clip *"+clipName+"* to soundboard *"+soundboardName+"*", message.getChannel());

        return false;
    }

    private boolean handleSoundClipRequest(IMessage message, String soundboardName, String clipName){
//        String expandedName = Util.fromArrayWithUnderscores(clipParam,0,clipParam.length);
        // todo multiple word clipName

        String guildId = Util.getGuildFromUserMessage(message).getID();

        if(dataController.soundBoardExists(guildId, soundboardName)) {

            if (dataController.soundClipExists(guildId, soundboardName, clipName)) {
                SoundClip soundClip = dataController.getSoundClip(guildId, soundboardName, clipName);
                String url = soundClip.getUrl();
                audioDispatcher.playAudioClip(message, url);
                return true;
            }else{
                textDispatcher.dispatchText("sound clip *" + clipName + "* was not found for soundboard *"+soundboardName+"*", message.getChannel());
            }
        }else {
            textDispatcher.dispatchText("the soundboard " + soundboardName + " was not found", message.getChannel());
        }
        return false;
    }

    private boolean handleHelpRequest(IMessage message, String helpParam){

        if(helpParam == null || helpParam.isEmpty()){
            messageSessionController.startMessageSession(message, new HelpMessageResponder(textDispatcher,this));
            textDispatcher.dispatchText(HelpMessageResponder.HELP_START, message.getChannel());
            Analytics.sendEvent(new EventHit("Guild","Help","StartHelp",1));
            return true;
        }


        IGuild guild = Util.getGuildFromUserMessage(message);
        if(helpParam.equalsIgnoreCase("add")){
            handleAddHelp(message.getChannel());
        }
        else if(dataController.soundBoardExists(guild.getID(), helpParam)) {
            handleSoundboardHelp(message, helpParam);
        }else{
            dispatchSoundboardNotFound(helpParam, message.getChannel());
            return false;
        }

        return true;
    }

    private boolean handleSoundboardHelp(IMessage message, String soundboardName){
        String guildId = Util.getGuildFromUserMessage(message).getID();

        if(dataController.soundBoardExists(guildId, soundboardName)) {

            SoundBoard soundBoard = dataController.getSoundboard(guildId, soundboardName);
            if(soundBoard == null){
                dispatchSoundboardNotFound(soundboardName, message.getChannel());
                return false;
            }
            if(soundBoard.getClipCount() == 0){

                textDispatcher.dispatchText("*"+soundboardName+"* does not have any clips added yet" +
                        "\nTo upload a sound clip, post a file to the chat room and in the message put *!add clip <name of the clip>*", message.getChannel());
                Analytics.sendEvent(new EventHit("Guild","Help","SoundboardHelp",1));
            }else {

                StringBuilder helpBuilder = new StringBuilder();

                for (Map.Entry<String, SoundClip> entry : soundBoard.getClips().entrySet()) {
                    helpBuilder.append("!" + soundboardName + " " + entry.getKey().replace("_", " ") + "\n");
                }

                textDispatcher.dispatchText(helpBuilder.toString(), message.getChannel());
            }
        }

        return false;
    }


    private void joinChannel(IMessage message){
        IGuild guild = Util.getGuildFromUserMessage(message);
        if(guild == null){
            textDispatcher.dispatchText("could not join channel", message.getChannel());
        }
        for(IVoiceChannel voiceChannel : guild.getVoiceChannels()){
            for(IUser user : voiceChannel.getConnectedUsers()){
                if(user.getID().equals(message.getAuthor().getID())){
                    try {

                        voiceChannel.join();
                        return;
                    } catch (MissingPermissionsException e) {
                        SoundboardExceptionHandler.Instance.handleException(e);
                        e.printStackTrace();
                        sendMissingVoicePermissionsMessage(message.getChannel());
                    }
                }
            }
        }

        textDispatcher.dispatchText("Could not join your voice channel. Are you connected to a voice channel?",message.getChannel());
    }


    private boolean handleAddHelp(IChannel chatChannel) {
        textDispatcher.dispatchText("Click the upload file button, in the comment put *!add <sounbard name> <clip name>*",chatChannel);
        Analytics.sendEvent(new EventHit("Guild","Help","AddHelp",1));

        return true;
    }

    private void dispatchSoundboardNotFound(String soundboardName, IChannel channel){
        textDispatcher.dispatchText("Soundboard *"+soundboardName+"* not found", channel);
    }

    public void listSoundboards(IChannel channel){
        Collection<SoundBoard> soundBoards = dataController.getSoundboards(channel.getGuild().getID());

        if(soundBoards == null || soundBoards.size() == 0){
            textDispatcher.dispatchText("There are no soundboards for the server yet...\n"+HelpMessageResponder.CREATE_SOUNDBOARD_HELP, channel);
            return;
        }

        StringBuilder builder = new StringBuilder();
        for(SoundBoard soundBoard : soundBoards){
            builder.append("*!");
            builder.append(soundBoard.getName());
            builder.append("*");
            builder.append('\n');
        }
        builder.append("\n\nTo see what sound clips are available in each soundboard, enter *!help <soundboard name>*");

        Analytics.sendEvent(new EventHit("Guild","Help","ListSoundboards",1));
        textDispatcher.dispatchText(builder.toString(), channel);
    }

    private void sendMissingVoicePermissionsMessage(IChannel channel){
        textDispatcher.dispatchText("Looks like Soundboardbot is missing permission to join this server's voice channels." +
                "\nPlease have the server admin add the bot to the server again with this link:\n" +
                Sensitive.AUTH_URL, channel);
    }

    private void notifyServerOwnerAboutMissingPermissions(IGuild guild){
        try {
            IChannel channel = guild.getOwner().getOrCreatePMChannel();
            guild.getName();

            textDispatcher.dispatchText("SoundboardBot can't connect to voice channels in Your Server \""+guild.getName()+"\".\n" +
                    "To fix this, click the link below to update the bot's permissions:\n" +
                    Sensitive.AUTH_URL, channel);

        } catch (DiscordException e) {
            SoundboardExceptionHandler.Instance.handleException(e);
            e.printStackTrace();
        } catch (RateLimitException e) {
            SoundboardExceptionHandler.Instance.handleException(e);
            e.printStackTrace();
        }
    }


}
