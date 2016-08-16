package xyz.doglandia.soundboard.audio;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.audio.AudioPlayer;
import xyz.doglandia.soundboard.util.Util;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by tdk10 on 7/19/2016.
 */
public class DiscordAudioDispatcher implements AudioDispatcher {


    public DiscordAudioDispatcher(){

    }

    @Override
    public void playAudioClip(IMessage message, File file) {
        IGuild guild = null;
        try {
            guild = message.getGuild();
        } catch (Exception e){
            System.out.println("tried to get guild of channel");
        }

        if(guild == null){
            // get guild from channel users
            guild = Util.getGuildFromUserMessage(message);
        }


        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);

        try {
            audioPlayer.queue(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playAudioClip(IMessage message, String url) {
        IGuild guild = null;
        try {
            guild = message.getGuild();
        } catch (Exception e){
            System.out.println("tried to get guild of channel");
        }

        if(guild == null){
            // get guild from channel users
            guild = Util.getGuildFromUserMessage(message);
        }
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);

        try {
            audioPlayer.queue(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopAllAudio(IMessage message) {
        IGuild guild = null;
        try {
            guild = message.getGuild();
        } catch (Exception e){
            System.out.println("tried to get guild of channel");
        }

        if(guild == null){
            // get guild from channel users
            guild = Util.getGuildFromUserMessage(message);
        }

        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);
        audioPlayer.clean();
    }


}
