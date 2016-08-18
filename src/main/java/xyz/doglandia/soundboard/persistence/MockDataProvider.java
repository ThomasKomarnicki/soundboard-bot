package xyz.doglandia.soundboard.persistence;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tdk10 on 8/11/2016.
 */
public class MockDataProvider implements DataProvider {

    HashMap<String, GuildOptions> guildOptions;

    public MockDataProvider(){
        guildOptions = new HashMap<>();
    }



    @Override
    public GuildOptions getGuildOptionsByGuildId(String guildId) {
        return guildOptions.get(guildId);
    }

//    @Override
//    public List<GuildOptions> getAllGuildOptions() {
//        List<GuildOptions> list = new ArrayList<>();
//        for(GuildOptions opts : guildOptions.values()){
//            list.add(opts);
//        }
//        return list;
//    }

    @Override
    public void addNewGuildOptions(GuildOptions guildOptions) {
        this.guildOptions.put(guildOptions.getGuildId(), guildOptions);
    }

    @Override
    public void updateGuildOptions(GuildOptions guildOptions) {

    }

    @Override
    public void createSoundboard(GuildOptions guildOptions, SoundBoard soundBoard) {

    }

    @Override
    public void addSoundClip(SoundBoard soundBoard, SoundClip soundClip) {

    }

    @Override
    public void deleteSoundboard(SoundBoard soundBoard) {

    }

    @Override
    public void deleteClip(SoundClip soundClip) {

    }
}
