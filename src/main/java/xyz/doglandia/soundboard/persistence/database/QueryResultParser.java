package xyz.doglandia.soundboard.persistence.database;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by tdk10 on 8/15/2016.
 */
public class QueryResultParser {

    private static final String PRIVILEGED_ROLES = "privileged_roles";
    private static final String LAST_CONNECTED_CHANNEL_ID = "last_connected_channel_id";

    public QueryResultParser(){

    }

    /**
     * expects a joined table with guild opts, soundboards and songs
     * @param resultSet
     * @return
     */
    public GuildOptions createGuildOptionsFromJoinedResults(ResultSet resultSet) throws SQLException {
        if(resultSet == null){
            return null;
        }


        GuildOptions guildOptions = null;

        if(resultSet.isBeforeFirst()){
            resultSet.next();
        }
        do{

            if(guildOptions == null){
                guildOptions = new GuildOptions(resultSet.getInt("_id"));
                guildOptions.setGuildId(resultSet.getString("guild_id"));

                if(resultSet.getArray(PRIVILEGED_ROLES) != null) {
                    String[] privilegedRoles = (String[]) resultSet.getArray(PRIVILEGED_ROLES).getArray();

                    guildOptions.setRolesThatCanAddClips(Arrays.asList(privilegedRoles));
                    guildOptions.setLastConnectedChannelId(resultSet.getString(LAST_CONNECTED_CHANNEL_ID));
                }

            }

            String soundboardName = resultSet.getString("soundboard_name");


            if(soundboardName != null && !guildOptions.hasSoundboard(soundboardName)) {
                SoundBoard soundBoard = new SoundBoard(guildOptions, resultSet.getInt("soundboard_id"));
                soundBoard.setName(soundboardName);
                soundBoard.setDisplayName(resultSet.getString("soundboard_display_name"));
                guildOptions.putSoundboard(soundBoard);
            }

            if(resultSet.getString("sound_clip_name") != null) {
                SoundClip soundClip = new SoundClip(resultSet.getInt("sound_clip_id"), resultSet.getString("sound_clip_name"), resultSet.getString("clip_url"));
                guildOptions.getSoundboard(soundboardName).addClip(soundClip);
            }

        }
        while (resultSet.next());

        return guildOptions;
    }


    public SoundClip createSoundClip(ResultSet soundClipResults) throws SQLException {
        soundClipResults.next();
        SoundClip soundClip = new SoundClip(soundClipResults.getInt("_id"), soundClipResults.getString("name"), soundClipResults.getString("clip_url"));
        return soundClip;
    }

    public SoundBoard createSoundBoard(GuildOptions guildOptions, ResultSet resultSet) throws SQLException {
        resultSet.next();
        SoundBoard soundBoard = new SoundBoard(guildOptions, resultSet.getInt("_id"));
        soundBoard.setName(resultSet.getString("name"));
        soundBoard.setDisplayName(resultSet.getString("display_name"));

        return soundBoard;
    }

    public Collection createSoundBoardList(ResultSet resultSet) throws SQLException {
        if(resultSet == null){
            return new ArrayList<>();
        }
        HashMap soundBoards = new HashMap();

        if(resultSet.isBeforeFirst()){
            resultSet.next();
        }

        do{


            String soundboardName = resultSet.getString("soundboard_name");


            if(soundboardName != null && !soundBoards.containsKey(soundboardName)) {
                SoundBoard soundBoard = new SoundBoard(resultSet.getInt("soundboard_id"));
                soundBoard.setName(soundboardName);
                soundBoard.setDisplayName(resultSet.getString("soundboard_display_name"));
                soundBoards.put(soundboardName, soundBoard);
            }

            if(resultSet.getString("sound_clip_name") != null) {
                SoundClip soundClip = new SoundClip(resultSet.getInt("sound_clip_id"), resultSet.getString("sound_clip_name"), resultSet.getString("clip_url"));
                ((SoundBoard)soundBoards.get(soundboardName)).addClip(soundClip);
            }

        }
        while (resultSet.next());

        return soundBoards.values();
    }
}
