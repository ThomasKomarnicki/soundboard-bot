package xyz.doglandia.soundboard.model.guild;

import sx.blah.discord.handle.obj.IGuild;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tdk10 on 8/7/2016.
 */
public class GuildOptions {

    private int id;

    private List<String> rollsThatCanAddClips;

    private String guildId;

    private HashMap<String, SoundBoard> soundBoards;

    public GuildOptions(){
        soundBoards = new HashMap<>();
    }

    public GuildOptions(int id){
        this();
        this.id = id;
    }


    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public void setRollsThatCanAddClips(List<String> rollsThatCanAddClips) {
        this.rollsThatCanAddClips = new ArrayList<>();
        this.rollsThatCanAddClips.addAll(rollsThatCanAddClips);
    }

    public List<String> getRollsThatCanAddClips() {
        return rollsThatCanAddClips;
    }

    public int getId() {
        return id;
    }

    public HashMap<String, SoundBoard> getSoundBoards() {
        return soundBoards;
    }
}
