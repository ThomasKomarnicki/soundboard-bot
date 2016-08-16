package xyz.doglandia.soundboard.audio;

import sx.blah.discord.handle.obj.IMessage;

import java.io.File;

/**
 * Created by tdk10 on 7/16/2016.
 */
public interface AudioDispatcher {

    void playAudioClip(IMessage message, File file);
    void playAudioClip(IMessage message, String url);
    void stopAllAudio(IMessage message);
}
