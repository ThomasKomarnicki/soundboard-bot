package xyz.doglandia.soundboard.persistence.database;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.util.SqlUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class QueryBuilder {

    private static final String GUILD_OPTS_TABLE = "guild_opts";
    private static final String SOUNDBOARDS_TABLE = "soundboards";
    private static final String SOUND_CLIPS_TABLE = "sound_clips";
    private static final String GLOBAL = "global";

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

    public PreparedStatement getGuildOptions(Connection c, String guildId) throws SQLException {

            PreparedStatement s = c.prepareStatement( "SELECT " +
            "guild_opts.guild_id, guild_opts._id, guild_opts.privileged_roles, guild_opts.last_connected_channel_id, soundboards._id as soundboard_id, " +
            "soundboards.name as soundboard_name, soundboards.display_name as soundboard_display_name, sound_clips.clip_url, sound_clips._id as sound_clip_id, " +
            "sound_clips.name as sound_clip_name " +
                    "FROM " +
            "guild_opts LEFT JOIN soundboards ON (guild_opts._id = soundboards.guild_opts_id OR " +
            "soundboards._id = ANY (added_soundboards)) " +
            "LEFT JOIN sound_clips ON (sound_clips.soundboard_id = soundboards._id) " +
                    "WHERE " +
            "guild_opts.guild_id = ?;");
            s.setString(1, guildId);
            return s;

    }

    public PreparedStatement getGlobalSoundboards(Connection c) throws SQLException {
        return c.prepareStatement("SELECT " +
                "soundboards._id as soundboard_id, " +
                "soundboards.name as soundboard_name, soundboards.display_name as soundboard_display_name, sound_clips.clip_url, sound_clips._id as sound_clip_id, " +
                "sound_clips.name as sound_clip_name " +
                "FROM " +
                "guild_opts LEFT JOIN soundboards ON (guild_opts._id = soundboards.guild_opts_id OR " +
                "soundboards._id = ANY (added_soundboards)) " +
                "LEFT JOIN sound_clips ON (sound_clips.soundboard_id = soundboards._id) " +
                "WHERE " +
                "guild_opts.guild_id = '" + GLOBAL +"';");
    }

    public PreparedStatement createNewGuildOptions(Connection c, String guildId) throws SQLException {
        PreparedStatement s = c.prepareStatement("INSERT INTO "+GUILD_OPTS_TABLE+" (guild_id) VALUES (?);");
        s.setString(1, guildId);
        return s;
    }

    public PreparedStatement getAllGuildOptions(Connection c) throws SQLException {
        return c.prepareStatement("SELECT * FROM "+GUILD_OPTS_TABLE+";");
    }

    public PreparedStatement addSoundboard(Connection c, GuildOptions guildOptions, String soundboardName, String displayName) throws SQLException {
        PreparedStatement s = c.prepareStatement("INSERT INTO " + SOUNDBOARDS_TABLE + " (guild_opts_id, name, display_name) VALUES (?, ?, ?);");
        s.setInt(1,guildOptions.getId());
        s.setString(2, soundboardName);
        s.setString(3, displayName);
        return s;

    }

    public PreparedStatement addSoundClip(Connection c, SoundBoard soundBoard, String name, String url) throws SQLException {
        PreparedStatement s = c.prepareStatement("INSERT INTO " + SOUND_CLIPS_TABLE + " (soundboard_id, name, clip_url) VALUES (?, ?, ?);");
        s.setInt(1, soundBoard.getId());
        s.setString(2, name);
        s.setString(3, url);
        return s;
    }

    public PreparedStatement deleteSoundboard(Connection c, SoundBoard soundBoard) throws SQLException {
        PreparedStatement s = c.prepareStatement("DELETE FROM "+SOUNDBOARDS_TABLE+" WHERE _id = ?;");
        s.setInt(1, soundBoard.getId());
        return s;
    }

    public PreparedStatement deleteSoundClip(Connection c, SoundClip soundClip) throws SQLException {
        PreparedStatement s = c.prepareStatement("DELETE FROM "+SOUND_CLIPS_TABLE+" WHERE _id = ?;");
        s.setInt(1, soundClip.getId());
        return s;
    }

    public PreparedStatement getSoundClip(Connection c, SoundBoard soundBoard, String name) throws SQLException {
        PreparedStatement s = c.prepareStatement("SELECT * FROM "+SOUND_CLIPS_TABLE+" WHERE soundboard_id = "+soundBoard.getId() +" AND name = '"+name+"';");
        s.setInt(1, soundBoard.getId());
        s.setString(2, name);
        return s;
    }

    public PreparedStatement getSoundboard(Connection c, GuildOptions guildOptions, String name) throws SQLException {
        PreparedStatement s = c.prepareStatement("SELECT * FROM "+SOUNDBOARDS_TABLE+" WHERE guild_opts_id = ? AND name = ?;");
        s.setInt(1, guildOptions.getId());
        s.setString(2, name);
        return s;
    }


    public PreparedStatement deleteSoundboardByName(Connection c, String soundboardName) throws SQLException {
        PreparedStatement s = c.prepareStatement("DELETE FROM "+SOUNDBOARDS_TABLE +" WHERE name = ?;");
        s.setString(1, soundboardName);
        return s;
    }

    public PreparedStatement deleteClipByName(Connection c, String soundClipName) throws SQLException {
        PreparedStatement s = c.prepareStatement("DELETE FROM "+SOUND_CLIPS_TABLE +" WHERE name = ?;");
        s.setString(1, soundClipName);
        return s;
    }

    public PreparedStatement soundboardExists(Connection c, String guildId, String soundboardName) throws SQLException {
        PreparedStatement s = c.prepareStatement("SELECT EXISTS (SELECT 1 from "+SOUNDBOARDS_TABLE+" WHERE name = '"+soundboardName+"');");
        s.setString(1, soundboardName);
        return s;
    }

    public PreparedStatement soundClipExists(Connection c, String guildId, String soundboardName, String clipName) throws SQLException {
        PreparedStatement s = c.prepareStatement("SELECT EXISTS (SELECT 1 from "+SOUND_CLIPS_TABLE+" WHERE name = '"+clipName+"');");
        s.setString(1, clipName);
        return s;
    }

}
