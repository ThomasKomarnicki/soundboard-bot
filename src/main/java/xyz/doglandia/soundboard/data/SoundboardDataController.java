package xyz.doglandia.soundboard.data;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.doglandia.soundboard.BotEnvironment;
import xyz.doglandia.soundboard.exception.InvalidAudioClipException;
import xyz.doglandia.soundboard.exception.SoundboardAlreadyExistsException;
import xyz.doglandia.soundboard.exception.SoundboardExistException;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.DataProvider;
import xyz.doglandia.soundboard.persistence.DatabaseProvider;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * controls interaction between DataProvider and Soundboard app
 */
public class SoundboardDataController implements DataController {

    private DataProvider dataProvider;

    private HashMap<String, GuildOptions> guilds;

    public SoundboardDataController(){
        dataProvider = DatabaseProvider.instantiate(); // uses database provider and s3 file manager
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
    public boolean localSoundBoardExists(String guildId, String soundboardName) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        return guildOptions.hasSoundboard(soundboardName) && !guildOptions.getSoundboard(soundboardName).isGlobal();
    }

    @Override
    public SoundBoard getSoundboard(String guildId, String soundboardName) {

        if(soundBoardExists(guildId, soundboardName)){
            GuildOptions guildOptions = getGuildOptions(guildId);
            return guildOptions.getSoundboard(soundboardName);
        }

        return null;
    }

    private SoundBoard getLocalSoundboard(String guildId, String soundboardName){
        if(localSoundBoardExists(guildId, soundboardName)){
            GuildOptions guildOptions = getGuildOptions(guildId);
            return guildOptions.getSoundboard(soundboardName);
        }

        return null;
    }



    @Override
    public void saveSoundFileToSoundboard(String guildId, String url, String soundboardName, String clipName) throws SoundboardExistException, IOException, InvalidAudioClipException {
        // download sound file

        SoundBoard soundBoard = getLocalSoundboard(guildId, soundboardName);


        if(!url.endsWith(".mp3")){
            throw new InvalidAudioClipException();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                .build();

        Response response = client.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();



        File newClip = new File(BotEnvironment.getInstance().getTempFilesDir()+File.separator+soundBoard.getNameAsKey()+"-"+SoundBoard.getNameAsKey(clipName)+".mp3");
        System.out.println("creating file at: "+newClip.getAbsolutePath());
        newClip.createNewFile();
        OutputStream outStream = new FileOutputStream(newClip);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();

        SoundClip soundClip = dataProvider.createSoundClip(soundBoard, clipName, newClip);
        soundBoard.addClip(soundClip);

        newClip.delete();

    }

    @Override
    public boolean initGuild(String guildId) {
        GuildOptions guildOptions = dataProvider.getGuildOptionsByGuildId(guildId);
        boolean newlyCreated = false;
        if(guildOptions == null){
            newlyCreated = true;
            guildOptions = getGuildOptions(guildId);

        }

        guilds.put(guildId, guildOptions);

        return newlyCreated;
    }

    @Override
    public void createSoundboard(String guildId, String soundboardName) throws SoundboardAlreadyExistsException {
        GuildOptions guildOptions = getGuildOptions(guildId);

        SoundBoard soundBoard = guildOptions.getSoundboard(soundboardName.toLowerCase());
        if(soundBoard != null){
            // if soundboard already exists, throw exception
            throw new SoundboardAlreadyExistsException(soundboardName);
        }else{

            soundBoard = dataProvider.createSoundboard(guildOptions, soundboardName);
            guildOptions.putSoundboard(soundBoard);

        }
    }

    @Override
    public void setGuildPrivilegedRoles(String guildId, List<String> roleNames) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        guildOptions.setRolesThatCanAddClips(roleNames);

        dataProvider.updateGuildOptions(guildOptions);
    }

    @Override
    public void quit() {
        dataProvider.close();
    }

    @Override
    public boolean matchesPermissions(String guildId, List<String> userRoles) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        return guildOptions.matchesRoles(userRoles);
    }

    @Override
    public GuildOptions getGuildOptionsById(String guildId) {
        return getGuildOptions(guildId);
    }

    @Override
    public void updateGuildOptions(GuildOptions guildOptions) {
        dataProvider.updateGuildOptions(guildOptions);
    }

    @Override
    public Collection<SoundBoard> getSoundboards(String guildId) {
        GuildOptions guildOptions = getGuildOptions(guildId);
        return guildOptions.getAllSoundboards();
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
