package xyz.doglandia.soundboard.audio.management;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.model.soundboard.SoundboardMeta;

import java.io.*;

/**
 * meta.json file contains information about subdirectories which contain soundboard files
 */

@Deprecated
public class SoundboardSoundManager implements SoundsManager {

    private SoundboardMeta soundboardMeta;

    private SoundboardDataCreator soundboardDataCreator;

    public SoundboardSoundManager(SoundboardDataCreator soundboardDataCreator){
        this.soundboardDataCreator = soundboardDataCreator;
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);


        soundboardMeta = soundboardDataCreator.createData();

    }


    @Override
    public boolean soundClipExists(String soundboardName, String clipParam) {
        if(soundBoardExists(soundboardName)){
            return soundboardMeta.getSoundBoardByName(soundboardName.toLowerCase()).hasClip(clipParam);
        }
        return false;
    }

    @Override
    public SoundClip getSoundClip(String soundboardName, String clipParam) {
        if(soundBoardExists(soundboardName)){
            return soundboardMeta.getSoundBoardByName(soundboardName.toLowerCase()).getSoundClip(clipParam);
        }
        return null;
    }

    @Override
    public boolean soundBoardExists(String soundboardName) {
        return soundboardMeta.hasSoundboard(soundboardName.toLowerCase());
    }

    @Override
    public SoundBoard getSoundboard(String soundboardName) {
        return soundboardMeta.getSoundBoardByName(soundboardName);
    }

    @Override
    public void saveSoundFileToSoundboard(String url, String soundboardName, String clipName) throws SoundboardExistException, IOException, InvalidAudioClipException{

        if(!soundBoardExists(soundboardName)){
            throw new SoundboardExistException(soundboardName);
        }

        if(!url.endsWith(".mp3")){
            throw new InvalidAudioClipException();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .build();

        Response response = client.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();

        File newClip = new File("soundboards"+File.separator+soundboardName+File.separator+clipName+".mp3");
        OutputStream outStream = new FileOutputStream(newClip);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();

//        soundboardMeta.getSoundBoardByName(soundboardName).addClip(new SoundClip(clipName, newClip));

    }
}
