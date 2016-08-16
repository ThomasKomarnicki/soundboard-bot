package xyz.doglandia.soundboard.persistence.database;

import xyz.doglandia.soundboard.model.guild.GuildOptions;

import java.sql.ResultSet;

/**
 * Created by tdk10 on 8/15/2016.
 */
public class QueryResultParser {

    public QueryResultParser(){

    }

    /**
     * expects a joined table with guild opts, soundboards and songs
     * @param resultSet
     * @return
     */
    public GuildOptions createGuildOptionsFromJoinedResults(ResultSet resultSet){

        GuildOptions guildOptions = new GuildOptions();


        return guildOptions;
    }
}
