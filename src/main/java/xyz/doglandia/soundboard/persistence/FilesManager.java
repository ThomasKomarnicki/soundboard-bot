package xyz.doglandia.soundboard.persistence;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.io.File;

/**
 * Created by tdk10 on 8/15/2016.
 */
public interface FilesManager {

    String uploadFile(String key, File downloadedSoundFile);
}
