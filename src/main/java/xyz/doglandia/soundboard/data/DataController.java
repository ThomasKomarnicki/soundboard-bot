package xyz.doglandia.soundboard.data;

import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardAlreadyExistsException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by tdk10 on 7/16/2016.
 */
public interface DataController {

    boolean soundClipExists(String guildId, String soundboardName, String clipParam);

    SoundClip getSoundClip(String guildId, String soundboardName, String clipParam);

    boolean soundBoardExists(String guildId, String soundboardName);

    SoundBoard getSoundboard(String guildId, String soundboardName);

    void saveSoundFileToSoundboard(String guildId, String url, String soundboardName, String clipName) throws SoundboardExistException, IOException, InvalidAudioClipException;

    void initGuild(String guildId);

    void createSoundboard(String guildId, String soundboardName) throws SoundboardAlreadyExistsException;

    void setGuildPrivilegedRoles(String guildId, List<String> roleNames);

    void quit();

    boolean matchesPermissions(String guildId, List<String> userRoles);

    GuildOptions getGuildOptionsById(String guildId);

    void updateGuildOptions(GuildOptions guildOptions);

    Collection<SoundBoard> getSoundboards(String guildId);
}
