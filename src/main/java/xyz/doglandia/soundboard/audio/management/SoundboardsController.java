package xyz.doglandia.soundboard.audio.management;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.DataProvider;
import xyz.doglandia.soundboard.persistence.DatabaseProvider;
import xyz.doglandia.soundboard.persistence.S3FileManager;

import java.io.*;
import java.util.HashMap;

/**
 * controls interaction between DataProvider and Soundboard app
 */
public class SoundboardsController implements SoundboardController {

    private DataProvider dataProvider;

    private HashMap<String, GuildOptions> guilds;

    public SoundboardsController(){
        dataProvider = new DatabaseProvider(new S3FileManager()); // uses database provider and s3 file manager
        guilds = new HashMap<>();
    }


    @Override
    public boolean soundClipExists(String guildId, String soundboardName, String clipParam) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        if(!soundBoardExists(guildId, soundboardName)){
            return false;
        }
        return (guildOptions.getSoundboard(soundboardName).hasClip(clipParam));

    }

    @Override
    public SoundClip getSoundClip(String guildId, String soundboardName, String clipParam) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        if(!guildOptions.hasSoundboard(soundboardName)){
            return null;
        }
        return (guildOptions.getSoundboard(soundboardName).getSoundClip(clipParam));
    }

    @Override
    public boolean soundBoardExists(String guildId, String soundboardName) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        return guildOptions.hasSoundboard(soundboardName);

    }

    @Override
    public SoundBoard getSoundboard(String guildId, String soundboardName) {

        if(!soundBoardExists(guildId, soundboardName)){
            GuildOptions guildOptions = getGuildOptions(guildId);
            return guildOptions.getSoundboard(soundboardName);
        }

        return null;
    }

    @Override
    public void saveSoundFileToSoundboard(String guildId, String url, String soundboardName, String clipName) throws SoundboardExistException, IOException, InvalidAudioClipException {
        // download sound file

        SoundBoard soundBoard = getSoundboard(guildId, soundboardName);

        if(!url.endsWith(".mp3")){
            throw new InvalidAudioClipException();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .build();

        Response response = client.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();

        File newClip = new File(soundBoard.getNameAsKey()+File.separator+SoundBoard.getNameAsKey(clipName)+".mp3");
        OutputStream outStream = new FileOutputStream(newClip);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();

        SoundClip soundClip = dataProvider.createSoundClip(soundBoard, clipName, newClip);
        soundBoard.addClip(soundClip);

    }

    @Override
    public void initGuild(String guildId) {
        GuildOptions guildOptions = getOrCreateGuildOptions(guildId);
        guilds.put(guildId, guildOptions);
    }

    @Override
    public void createSoundboard(String guildId, String soundboardName) throws SoundboardExistException {
        GuildOptions guildOptions = getGuildOptions(guildId);

        SoundBoard soundBoard = guildOptions.getSoundboard(soundboardName.toLowerCase());
        if(soundBoard != null){
            // if soundboard already exists, throw exception
            throw new SoundboardExistException(soundboardName);
        }else{
            dataProvider.createSoundboard(guildOptions, soundboardName);
        }
    }


    private GuildOptions getGuildOptions(String guildId){
        if(guilds.containsKey(guildId)){
            return guilds.get(guildId);
        }else{
            return getOrCreateGuildOptions(guildId);
        }
    }

    private GuildOptions getOrCreateGuildOptions(String guildId){
        GuildOptions guildOptions = dataProvider.getGuildOptionsByGuildId(guildId);

        if(guildOptions == null){
            dataProvider.createGuildOptions(guildId);
        }

        guildOptions = dataProvider.getGuildOptionsByGuildId(guildId);
        guilds.put(guildOptions.getGuildId(), guildOptions);

        return guildOptions;
    }
}
