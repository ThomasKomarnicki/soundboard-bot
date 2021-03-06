package xyz.doglandia.soundboard.model.soundboard;

import xyz.doglandia.soundboard.model.guild.GuildOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class SoundBoard {

    private int id;

    private boolean isGlobal = false;

    private String name;
    private String displayName;

    private GuildOptions guildOptions;

    private Map<String, SoundClip> clips;
    private Map<Integer, SoundClip> clipIdsMap;

    public SoundBoard(){
        clips = new HashMap<>();
        clipIdsMap = new HashMap<>();
    }

    public SoundBoard(int id){
        this();
        this.id = id;
    }

    public SoundBoard(GuildOptions guildOptions, int id){
        this();
        this.guildOptions = guildOptions;
        this.id = id;
    }

    public boolean hasClip(String clipName){
        return clips.containsKey(clipName);
    }

    public boolean hasClip(int clipId){
        return clipIdsMap.containsKey(clipId);
    }

    public SoundClip getSoundClip(String clipName){
        return clips.get(clipName);
    }

    public SoundClip getSoundClip(int clipId) {
        return clipIdsMap.get(clipId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addClip(SoundClip soundClip){
        clips.put(soundClip.getName(), soundClip);
        clipIdsMap.put(soundClip.getId(), soundClip);
    }

    public String getName() {
        return name;
    }

    public String getNameAsKey(){
        return getNameAsKey(name);
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

    public void setGuildOptions(GuildOptions guildOptions) {
        this.guildOptions = guildOptions;
    }

    public static String getNameAsKey(String name){
        return name.toLowerCase();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getClipCount() {
        return clips.size();
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(boolean global) {
        isGlobal = global;
    }
}
