package xyz.doglandia.soundboard.persistence.database;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by tdk10 on 8/15/2016.
 */
public class QueryResultParser {

    private static final String PRIVILEGED_ROLES = "privileged_roles";

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

        do{

            if(guildOptions == null){
                guildOptions = new GuildOptions(resultSet.getInt("_id"));
                guildOptions.setGuildId(resultSet.getString("guild_id"));

                if(resultSet.getArray(PRIVILEGED_ROLES) != null) {
                    String[] privilegedRoles = (String[]) resultSet.getArray(PRIVILEGED_ROLES).getArray();

                    guildOptions.setRollsThatCanAddClips(Arrays.asList(privilegedRoles));
                }

            }


            String soundboardName = resultSet.getString("soundboard_name");

            if(!guildOptions.getSoundBoards().containsKey(soundboardName)) {
                SoundBoard soundBoard = new SoundBoard(guildOptions, resultSet.getInt("soundboard_id"));
                soundBoard.setName(soundboardName);
                guildOptions.getSoundBoards().put(soundboardName, soundBoard);
            }

            SoundClip soundClip = new SoundClip(resultSet.getInt("sound_clip_id"), resultSet.getString("sound_clip_name") , resultSet.getString("clip_url"));
            guildOptions.getSoundBoards().get(soundboardName).addClip(soundClip);
        }
        while (resultSet.next());




        return guildOptions;
    }


    public SoundClip createSoundClip(ResultSet soundClipResults) {


    }
}
