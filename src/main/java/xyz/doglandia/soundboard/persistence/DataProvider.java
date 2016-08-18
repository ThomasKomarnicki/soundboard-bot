package xyz.doglandia.soundboard.persistence;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.util.List;

/**
 * Created by tdk10 on 8/11/2016.
 */
public interface DataProvider {

    GuildOptions getGuildOptionsByGuildId(String guildId);

    void addNewGuildOptions(GuildOptions guildOptions);

    void updateGuildOptions(GuildOptions guildOptions);

    void createSoundboard(GuildOptions guildOptions, SoundBoard soundBoard);

    void addSoundClip(SoundBoard soundBoard, SoundClip soundClip);

    void deleteSoundboard(SoundBoard soundBoard);

    void deleteClip(SoundClip soundClip);


}
