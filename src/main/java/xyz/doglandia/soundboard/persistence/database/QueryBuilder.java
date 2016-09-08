package xyz.doglandia.soundboard.persistence.database;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.util.SqlUtil;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class QueryBuilder {

    private static final String GUILD_OPTS_TABLE = "guild_opts";
    private static final String SOUNDBOARDS_TABLE = "soundboards";
    private static final String SOUND_CLIPS_TABLE = "sound_clips";

    private String databaseName;

    public QueryBuilder(String databaseName){
        this.databaseName = databaseName;
    }


    public String updateGuildOptions(GuildOptions guildOptions) {
        StringBuilder stringBuilder = new StringBuilder();
        String whereClause = " WHERE guild_id = '"+guildOptions.getGuildId()+"'";

        stringBuilder.append("UPDATE guild_opts SET privileged_roles = "+ SqlUtil.stringListToValue(guildOptions.getRolesThatCanAddClips()) + ", last_connected_channel_id = '"+guildOptions.getLastConnectedChannelId()+"' ");
        stringBuilder.append(whereClause);
        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    public String getGuildOptions(String guildId) {
        return "SELECT " +
        "guild_opts.guild_id, guild_opts._id, guild_opts.privileged_roles, guild_opts.last_channel_connected_id, soundboards._id as soundboard_id, " +
        "soundboards.name as soundboard_name, soundboards.display_name as soundboard_display_name, sound_clips.clip_url, sound_clips._id as sound_clip_id, " +
        "sound_clips.name as sound_clip_name " +
                "FROM " +
        "guild_opts LEFT JOIN soundboards ON (guild_opts._id = soundboards.guild_opts_id OR " +
        "soundboards._id = ANY (added_soundboards)) " +
        "LEFT JOIN sound_clips ON (sound_clips.soundboard_id = soundboards._id) " +
                "WHERE " +
        "guild_opts.guild_id = '" + guildId +"';";
    }

    public String createNewGuildOptions(String guildId) {
        return "INSERT INTO "+GUILD_OPTS_TABLE+" (guild_id) VALUES ('"+guildId+"');";
    }

    public String getAllGuildOptions(){
        return "SELECT * FROM "+GUILD_OPTS_TABLE+";";
    }

//    public String getSoundboardsForGuild(int guildOptionsId){
//        return "SELECT * FROM "+SOUNDBOARDS_TABLE+" WHERE guild_opts_id = "+guildOptionsId + ";";
//    }
//
//    public String getClipsForSoundboard(int soundBoardId) {
//
//        return "SELECT * FROM "+SOUND_CLIPS_TABLE+" WHERE soundboard_id = "+soundBoardId + ";";
//    }

    public String addSoundboard(GuildOptions guildOptions, String soundboardName, String displayName) {
        return "INSERT INTO " + SOUNDBOARDS_TABLE + " (guild_opts_id, name, display_name) VALUES ("+guildOptions.getId()+", '"+soundboardName+"', '"+ displayName+"');";
    }

    public String addSoundClip(SoundBoard soundBoard, String name, String url) {
        return "INSERT INTO " + SOUND_CLIPS_TABLE + " (soundboard_id, name, clip_url) VALUES ("+soundBoard.getId()+", '"+name+"', '"+url+"');";
    }

    public String deleteSoundboard(SoundBoard soundBoard) {
        return "DELETE FROM "+SOUNDBOARDS_TABLE+" WHERE _id = "+soundBoard.getId()+";";
    }

    public String deleteSoundClip(SoundClip soundClip) {
        return "DELETE FROM "+SOUND_CLIPS_TABLE+" WHERE _id = "+soundClip.getId()+";";
    }

    public String getSoundClip(SoundBoard soundBoard, String name) {
        return "SELECT * FROM "+SOUND_CLIPS_TABLE+" WHERE soundboard_id = "+soundBoard.getId() +" AND name = '"+name+"';";
    }

    public String getSoundboard(GuildOptions guildOptions, String name) {
        return "SELECT * FROM "+SOUNDBOARDS_TABLE+" WHERE guild_opts_id = "+guildOptions.getId() +" AND name = '"+name+"';";
    }


    public String deleteSoundboardByName(String soundboardName) {
        return "DELETE FROM "+SOUNDBOARDS_TABLE +" WHERE name = '"+soundboardName+"';";
    }

    public String deleteClipByName(String soundClipName) {
        return "DELETE FROM "+SOUND_CLIPS_TABLE +" WHERE name = '"+soundClipName+"';";
    }

    public String soundboardExists(String guildId, String soundboardName) {
        return "SELECT EXISTS (SELECT 1 from "+SOUNDBOARDS_TABLE+" WHERE name = '"+soundboardName+"');";
    }

    public String soundClipExists(String guildId, String soundboardName, String clipName) {
        return "SELECT EXISTS (SELECT 1 from "+SOUND_CLIPS_TABLE+" WHERE name = '"+clipName+"');";
    }
}
