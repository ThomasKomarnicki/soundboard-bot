package xyz.doglandia.soundboard.model.soundboard;

import xyz.doglandia.soundboard.model.guild.GuildOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class SoundBoard {

    private int id;

    private String name;

    private GuildOptions guildOptions;

    public SoundBoard(){
        clips = new HashMap<>();
    }

    public SoundBoard(GuildOptions guildOptions, int id){
        this();
        this.guildOptions = guildOptions;
        this.id = id;
    }

    private Map<String, SoundClip> clips;

    public boolean hasClip(String clipName){
        return clips.containsKey(clipName);
    }

    public SoundClip getSoundClip(String clipName){
        return clips.get(clipName);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addClip(SoundClip soundClip){
        clips.put(soundClip.getName(), soundClip);
    }

    public String getName() {
        return name;
    }

    public String getNameAsKey(){
        return name.toLowerCase();
    }

    public Map<String, SoundClip> getClips() {
        return clips;
    }

    public int getId() {
        return id;
    }

    public GuildOptions getGuildOptions() {
        return guildOptions;
    }
}
