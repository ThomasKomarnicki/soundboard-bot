package xyz.doglandia.soundboard.audio.management;

import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.model.soundboard.SoundboardMeta;

import java.io.File;

/**
 * Created by tdk10 on 7/17/2016.
 */
public class SoundboardFilesDataCreator implements  SoundboardDataCreator {

    private File soundboardsRoot;

    public SoundboardFilesDataCreator(File soundboardsRoot){
        this.soundboardsRoot = soundboardsRoot;
    }


    @Override
    public SoundboardMeta createData() {
        SoundboardMeta soundboardMeta = new SoundboardMeta();
        for (File childFile : soundboardsRoot.listFiles()){
            if(childFile.isDirectory()){
                SoundBoard soundBoard = createSoundboardFromDirectory(childFile);
                soundboardMeta.addSoundBoard(soundBoard, soundBoard.getName().toLowerCase());
            }
        }

        return soundboardMeta;
    }

    private SoundBoard createSoundboardFromDirectory(File dir){
        SoundBoard soundBoard = new SoundBoard();
        soundBoard.setName(dir.getName());

//        for(File file : dir.listFiles()){
//
//            if(file.getName().endsWith(".mp3") || file.getName().endsWith(".wav")){
//                SoundClip soundClip = new SoundClip(file.getName().substring(0,file.getName().length()-4), file);
//                soundBoard.addClip(soundClip);
//            }
//        }

        return soundBoard;
    }
}
