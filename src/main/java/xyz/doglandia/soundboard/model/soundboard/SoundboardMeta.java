package xyz.doglandia.soundboard.model.soundboard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class SoundboardMeta {

    public SoundboardMeta(){
        soundboards = new HashMap<>();
    }

    private Map<String, SoundBoard> soundboards;

    public SoundBoard getSoundBoardByName(String name){
        name = name.toLowerCase();
        if(soundboards.containsKey(name)){
            return soundboards.get(name);
        }
        return null;
    }

    public boolean hasSoundboard(String name){
        return soundboards.containsKey(name);
    }

    public void addSoundBoard(SoundBoard soundBoard, String dirName){
        soundboards.put(dirName, soundBoard);
    }

}
