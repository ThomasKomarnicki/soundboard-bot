package xyz.doglandia.soundboard.persistence.database;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.util.SqlUtil;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class QueryBuilder {

    private static final String GUILD_OPTS_TABLE = "guild_opts.guild_opts";
    private static final String SOUNDBOARDS_TABLE = "guild_opts.soundboards";
    private static final String SOUND_CLIPS_TABLE = "guild_opts.sound_clips";

    private String databaseName;

    public QueryBuilder(String databaseName){
        this.databaseName = databaseName;
    }

    public String insertGuildOptions(GuildOptions guildOptions){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO guild_opts.guild_opts (guild_id, privileged_roles) VALUES (");
        stringBuilder.append("'"+guildOptions.getGuildId()+"',");
        if(guildOptions.getRollsThatCanAddClips() != null && guildOptions.getRollsThatCanAddClips().size() > 0){

            // add roles to insert string

        }

        stringBuilder.append(");");

        return stringBuilder.toString();
    }

    public String updateGuildOptions(GuildOptions guildOptions) {
        StringBuilder stringBuilder = new StringBuilder();
        String whereClause = " WHERE guild_id = '"+guildOptions.getGuildId()+"'";

        stringBuilder.append("UPDATE guild_opts.guild_opts SET privileged_roles = "+ SqlUtil.stringListToValue(guildOptions.getRollsThatCanAddClips()));
        stringBuilder.append(whereClause);
        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    public String getGuildOptions(String guildId) {
        return "SELECT * FROM "+GUILD_OPTS_TABLE+" WHERE guild_id = '"+guildId + "';";
    }

    public String getAllGuildOptions(){
        return "SELECT * FROM "+GUILD_OPTS_TABLE+";";
    }

    public String getSoundboardsForGuild(int guildOptionsId){
        return "SELECT * FROM "+SOUNDBOARDS_TABLE+" WHERE guild_opts_id = "+guildOptionsId + ";";
    }

    public String getClipsForSoundboard(int soundBoardId) {

        return "SELECT * FROM "+SOUND_CLIPS_TABLE+" WHERE soundboard_id = "+soundBoardId + ";";
    }

    public String addSoundboard(GuildOptions guildOptions, SoundBoard soundBoard) {
        return "INSERT INTO " + SOUNDBOARDS_TABLE + " (guild_opts_id, name) VALUES ("+guildOptions.getId()+", '"+soundBoard.getName()+"');";
    }

    public String addSoundClip(SoundBoard soundBoard, SoundClip soundClip) {
        return "INSERT INTO " + SOUND_CLIPS_TABLE + " (soundboard_id, name, clip_url) VALUES ("+soundBoard.getId()+", '"+soundClip.getName()+"', '"+soundClip.getUrl()+"');";
    }

    public String deleteSoundboard(SoundBoard soundBoard) {
        return "DELETE FROM "+SOUNDBOARDS_TABLE+" WHERE _id = "+soundBoard.getId()+";";
    }

    public String deleteSoundClip(SoundClip soundClip) {
        return "DELETE FROM "+SOUND_CLIPS_TABLE+" WHERE _id = "+soundClip.getId()+";";
    }
}
