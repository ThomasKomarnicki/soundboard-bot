package xyz.doglandia.soundboard.audio.management;

import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.DataProvider;
import xyz.doglandia.soundboard.persistence.DatabaseProvider;
import xyz.doglandia.soundboard.persistence.S3FileManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * controls interaction between DataProvider and Soundboard app
 */
public class SoundboardsController implements SoundsManager {

    private DataProvider dataProvider;

    private HashMap<String, GuildOptions> guilds;

    public SoundboardsController(){
        dataProvider = new DatabaseProvider(new S3FileManager()); // uses database provider and s3 file manager
        guilds = new HashMap<>();
    }


    @Override
    public boolean soundClipExists(String guildId, String soundboardName, String clipParam) {
        return false;
    }

    @Override
    public SoundClip getSoundClip(String guildId, String soundboardName, String clipParam) {
        return null;
    }

    @Override
    public boolean soundBoardExists(String guildId, String soundboardName) {
        return false;
    }

    @Override
    public SoundBoard getSoundboard(String guildId, String soundboardName) {
        return null;
    }

    @Override
    public void saveSoundFileToSoundboard(String guildId, String url, String soundboardName, String clipName) throws SoundboardExistException, IOException, InvalidAudioClipException {

    }
}
