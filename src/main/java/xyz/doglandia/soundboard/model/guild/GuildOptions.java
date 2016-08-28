package xyz.doglandia.soundboard.model.guild;

import xyz.doglandia.soundboard.model.soundboard.SoundBoard;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tdk10 on 8/7/2016.
 */
public class GuildOptions {

    private int id;

    private HashSet<String> rolesThatCanAddClips;

    private String guildId;

    private HashMap<String, SoundBoard> soundBoards;

    public GuildOptions(){
        soundBoards = new HashMap<>();
        rolesThatCanAddClips = new HashSet<>();
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

    public void setRolesThatCanAddClips(List<String> rolesThatCanAddClips) {
        this.rolesThatCanAddClips = new HashSet<>();
        this.rolesThatCanAddClips.addAll(rolesThatCanAddClips);

    }

    public List<String> getRolesThatCanAddClips() {
        List<String> asList = new ArrayList<>();
        asList.addAll(rolesThatCanAddClips);
        return asList;
    }

    public int getId() {
        return id;
    }

//    public HashMap<String, SoundBoard> getSoundBoards() {
//        return soundBoards;
//    }

    public boolean hasSoundboard(String soundboardName){
        return soundBoards.containsKey(SoundBoard.getNameAsKey(soundboardName));
    }

    public SoundBoard getSoundboard(String soundboardName){
        return soundBoards.get(SoundBoard.getNameAsKey(soundboardName));
    }

    public void putSoundboard(SoundBoard soundBoard){
        soundBoards.put(soundBoard.getNameAsKey(), soundBoard);
    }

    public boolean matchesRoles(List<String> roleNames){
        for(String role : roleNames){
            if(rolesThatCanAddClips.contains(role)){
                return true;
            }
        }

        return false;
    }


}
