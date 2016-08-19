package xyz.doglandia.soundboard.audio.management;

import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.io.IOException;

/**
 * Created by tdk10 on 7/16/2016.
 */
public interface SoundsManager {

    boolean soundClipExists(String guildId, String soundboardName, String clipParam);

    SoundClip getSoundClip(String guildId, String soundboardName, String clipParam);

    boolean soundBoardExists(String guildId, String soundboardName);

    SoundBoard getSoundboard(String guildId, String soundboardName);

    void saveSoundFileToSoundboard(String guildId, String url, String soundboardName, String clipName) throws SoundboardExistException, IOException, InvalidAudioClipException;
}
