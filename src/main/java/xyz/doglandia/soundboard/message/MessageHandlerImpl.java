package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.doglandia.soundboard.audio.AudioDispatcher;
import xyz.doglandia.soundboard.audio.management.SoundboardController;
import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardAlreadyExistsException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.text.TextDispatcher;
import xyz.doglandia.soundboard.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    SoundboardController soundboardController;

    MessageSessionController messageSessionController;

    public MessageHandlerImpl(AudioDispatcher audioDispatcher, TextDispatcher textDispatcher, SoundboardController soundboardController){
        this.textDispatcher = textDispatcher;
        this.audioDispatcher = audioDispatcher;
        this.soundboardController = soundboardController;

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
                joinChannel(message, messageParams.getParam(CHANNEL_NAME));
                break;
            case STOP_AUDIO:
                audioDispatcher.stopAllAudio(message);
                break;
        }

        return true;

    }


    @Override
    public boolean handleMention(IMessage message, IChannel chatChannel) {
        return false;
    }

    @Override
    public void handleGuildCreated(IGuild guild) {
        soundboardController.initGuild(guild.getID());

        GuildOptions guildOptions = soundboardController.getDataProvider().getGuildOptionsByGuildId(guild.getID());
        String lastConnectedId = guildOptions.getLastConnectedChannelId();
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
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handleBotRolesChanged(IGuild guild, List<IRole> roles) {

        List<String> roleNames = roles.stream().map(IRole::getName).collect(Collectors.toList());

        soundboardController.setGuildPrivilegedRoles(guild.getID(), roleNames);
    }

    @Override
    public void handleVoiceChannelJoined(IVoiceChannel channel) {
        // todo do not like the fact that data provider is exposed to update the last connected voice channel
        GuildOptions guildOptions = soundboardController.getDataProvider().getGuildOptionsByGuildId(channel.getGuild().getID());
        guildOptions.setLastConnectedChannelId(channel.getID());
        soundboardController.getDataProvider().updateGuildOptions(guildOptions);
    }

    private boolean createSoundboard(IMessage message, String soundboardName){

        try {
            soundboardController.createSoundboard(Util.getGuildFromUserMessage(message).getID(), soundboardName);
            // todo do feedback here
            textDispatcher.dispatchText("soundboard \""+soundboardName+"\" created! Type: \n*!"+soundboardName+" help* \nto get started", message.getChannel());
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
            soundboardController.saveSoundFileToSoundboard(guildId, clipUrl, soundboardName, clipName);
            textDispatcher.dispatchText("Clip added! *!"+soundboardName+" "+clipName+"*", message.getChannel());
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

        if(soundboardController.soundBoardExists(guildId, soundboardName)) {

            if (soundboardController.soundClipExists(guildId, soundboardName, clipName)) {
                SoundClip soundClip = soundboardController.getSoundClip(guildId, soundboardName, clipName);
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
            messageSessionController.startMessageSession(message, new HelpMessageResponder(textDispatcher));
            textDispatcher.dispatchText(HelpMessageResponder.HELP_START, message.getChannel());
            return true;
        }


        IGuild guild = Util.getGuildFromUserMessage(message);
        if(helpParam.equalsIgnoreCase("add")){
            handleAddHelp(message.getChannel());
        }
        else if(soundboardController.soundBoardExists(guild.getID(), helpParam)) {
            handleSoundboardHelp(message, helpParam);
        }else{
            dispatchSoundboardNotFound(helpParam, message.getChannel());
            return false;
        }

        return true;
    }

    private boolean handleSoundboardHelp(IMessage message, String soundboardName){
        String guildId = Util.getGuildFromUserMessage(message).getID();

        if(soundboardController.soundBoardExists(guildId, soundboardName)) {

            SoundBoard soundBoard = soundboardController.getSoundboard(guildId, soundboardName);
            if(soundBoard == null){
                dispatchSoundboardNotFound(soundboardName, message.getChannel());
                return false;
            }
            if(soundBoard.getClipCount() == 0){

                textDispatcher.dispatchText("*"+soundboardName+"* does not have any clips added yet" +
                        "\nTo upload a sound clip, post a file to the chat room and in the message put *!add clip <name of the clip>*", message.getChannel());
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


    private void joinChannel(IMessage message, String channelName){
        IGuild guild = Util.getGuildFromUserMessage(message);
        if(guild == null){
            textDispatcher.dispatchText("could not join channel *"+channelName+"*", message.getChannel());
        }
        List<IVoiceChannel> voiceChannels = guild.getVoiceChannelsByName(channelName);

        if(voiceChannels != null && voiceChannels.size() > 0){
            IVoiceChannel voiceChannel = voiceChannels.get(0);
            if(!voiceChannel.isConnected()) {
                try {
                    voiceChannel.join();
                } catch (MissingPermissionsException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private boolean handleAddHelp(IChannel chatChannel) {
        textDispatcher.dispatchText("Click the upload file button, in the comment put *!add <sounbard name> <clip name>*",chatChannel);

        return true;
    }

    private void dispatchSoundboardNotFound(String soundboardName, IChannel channel){
        textDispatcher.dispatchText("Soundboard *"+soundboardName+"* not found", channel);
    }

    //        if(params.length >= 2){
//            if(params[0].equalsIgnoreCase("!stats")){
//                ChannelStatistics channelStatistics = new ChannelStatistics(chatChannel);
//                if(params[1].equalsIgnoreCase("phrase") && params.length >= 3){
//                    CountStatistic phraseCountStatistic = channelStatistics.getStatsForPhrase(Util.stringFromArray(params,2,params.length));
//                    textDispatcher.dispatchText(phraseCountStatistic.getDisplayOutput(), chatChannel);
//
//                }else if(params[1].equalsIgnoreCase("count") && params.length >= 3){
//                    if(params[2].equalsIgnoreCase("messages")) {
//                        CountStatistic phraseCountStatistic = channelStatistics.getStatsForMessageCount();
//                        textDispatcher.dispatchText(phraseCountStatistic.getDisplayOutput(), chatChannel);
//                    }else if(params[2].equalsIgnoreCase("links")){
//                        MultiCountStatistic phraseCountStatistic = channelStatistics.getStatsForLinks();
//                        textDispatcher.dispatchText(phraseCountStatistic.getDisplayOutput(), chatChannel);
//                    }
//
//
//                }
//            }
//        }


}
