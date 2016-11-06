package xyz.doglandia.soundboard.persistence;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.io.File;
import java.util.List;

/**
 * Created by tdk10 on 8/11/2016.
 */
public interface DataProvider {

    GuildOptions getGuildOptionsByGuildId(String guildId);

    void createGuildOptions(String guildId);

    void updateGuildOptions(GuildOptions guildOptions);

    SoundBoard createSoundboard(GuildOptions guildOptions, String soundboardName);

    SoundClip createSoundClip(SoundBoard soundBoard, String name, File file);



    void deleteSoundboard(SoundBoard soundBoard);

    void deleteClip(SoundClip soundClip);

    void close();


}
