package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.doglandia.soundboard.audio.AudioDispatcher;
import xyz.doglandia.soundboard.audio.management.SoundsManager;
import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import xyz.doglandia.soundboard.text.TextDispatcher;
import xyz.doglandia.soundboard.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class MessageHandlerImpl implements MessageHandler {

    private AudioDispatcher audioDispatcher;
    private TextDispatcher textDispatcher;
    private SoundsManager soundsManager;



    public MessageHandlerImpl(AudioDispatcher audioDispatcher, TextDispatcher textDispatcher, SoundsManager soundsManager){
        this.textDispatcher = textDispatcher;
        this.audioDispatcher = audioDispatcher;
        this.soundsManager = soundsManager;
    }

    @Override
    public boolean handleMessage(IMessage message, IChannel chatChannel) {
        String[] params = message.getContent().split(" ");

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



        if(params.length >= 2){
            if(params[0].startsWith("!") && params[0].length() > 1){



                String soundboardName =  params[0].substring(1,params[0].length());
                if (params[1].equalsIgnoreCase("help")){
                    return handleHelpRequest(soundboardName, chatChannel);
                }
                if(params[0].equalsIgnoreCase("!add")){
                    if(params[1].equalsIgnoreCase("help")){
                        return handleAddHelp(chatChannel);
                    }else {
                        if(message.getAttachments() != null && message.getAttachments().size() >= 1){
                            return handleAddClip(params[1], params[2], message.getAttachments().get(0).getUrl(), chatChannel);
                        }

                        return handleAddClip(params[1], params[2], params[3], chatChannel);

                    }
                }

                return handleSoundClipRequest(message, soundboardName,  Arrays.copyOfRange(params,1,params.length));
            }else{
                if(params[0].equalsIgnoreCase("join") && params[1].length() > 0){
                    handleJoin(message, params);
                }
            }
        }else if(params.length == 1){
            if(params[0].equalsIgnoreCase("stop")){
                audioDispatcher.stopAllAudio(message);
            }
        }
        return false;
    }


    @Override
    public boolean handleMention(IMessage message, IChannel chatChannel) {
        return false;
    }

    private boolean handleSoundClipRequest(IMessage message, String soundboardName, String[] clipParam){
        String expandedName = Util.fromArrayWithUnderscores(clipParam,0,clipParam.length);

        if(soundsManager.soundClipExists(soundboardName, expandedName)){
            SoundClip soundClip = soundsManager.getSoundClip(soundboardName, expandedName);
            audioDispatcher.playAudioClip(message, soundClip.getUrl());
            return true;
        }
        return false;
    }

    private boolean handleHelpRequest(String soundboardName, IChannel channel){

        if(soundsManager.soundBoardExists(soundboardName)) {

            SoundBoard soundBoard = soundsManager.getSoundboard(soundboardName);

            StringBuilder helpBuilder = new StringBuilder();
            for (Map.Entry<String, SoundClip> entry : soundBoard.getClips().entrySet()){
                helpBuilder.append("!"+soundboardName+" "+entry.getKey().replace("_"," ")+"\n");
            }

            textDispatcher.dispatchText(helpBuilder.toString(), channel);
        }

        return false;
    }


    private void handleJoin(IMessage message, String[] params){
        IGuild guild = Util.getGuildFromUserMessage(message);
        List<IVoiceChannel> voiceChannels = guild.getVoiceChannelsByName(Util.stringFromArray(params,1,params.length));
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

    /**
     *
     * @param soundboardName
     * @param clipName
     * @param clipUrl
     * @return if the soundclip was added successfully
     */
    private boolean handleAddClip(String soundboardName, String clipName, String clipUrl, IChannel channel){
        try {
            soundsManager.saveSoundFileToSoundboard(clipUrl, soundboardName, clipName);
            channel.sendMessage("Clip added! *!"+soundboardName+" "+clipName+"*");
            return true;
        } catch (SoundboardExistException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (InvalidAudioClipException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean handleAddHelp(IChannel chatChannel) {
        try {
            chatChannel.sendMessage("Click the upload file button, in the comment put *!add <sounbard_name> <clip_name>*");
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        return true;
    }
}
