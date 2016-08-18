package xyz.doglandia.soundboard.persistence;

import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.database.QueryBuilder;
import xyz.doglandia.soundboard.persistence.database.QueryResultParser;
import xyz.doglandia.soundboard.util.Sensitive;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class DatabaseProvider implements DataProvider {

    private static final String DB_NAME = "soundboardapp_1";

    private static final String GUILD_ID = "guild_id";


    private Connection connection;

    private String databaseName;
    private QueryBuilder queryBuilder;

    private FilesManager filesManager;

    private QueryResultParser queryResultParser;

    public DatabaseProvider(FilesManager filesManager){
        this.filesManager = filesManager;
        databaseName = DB_NAME;

        queryResultParser = new QueryResultParser();

        queryBuilder = new QueryBuilder(databaseName);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://ec2-52-43-81-161.us-west-2.compute.amazonaws.com:5432/"+databaseName;
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password", Sensitive.DB_PASSWORD);
        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public GuildOptions getGuildOptionsByGuildId(String guildId) {

        try {
            Statement st = connection.createStatement();
            String query = queryBuilder.getGuildOptions(guildId);
            ResultSet resultSet = st.executeQuery(query);

            if(resultSet.next()){
                return queryResultParser.createGuildOptionsFromJoinedResults(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public void addNewGuildOptions(GuildOptions guildOptions) {
        try {
            Statement st = connection.createStatement();
            String query = queryBuilder.insertGuildOptions(guildOptions);
            st.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGuildOptions(GuildOptions guildOptions) {
        try {
            Statement st = connection.createStatement();
            String query = queryBuilder.updateGuildOptions(guildOptions);
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSoundboard(GuildOptions guildOptions, SoundBoard soundBoard) {
        // todo create folder in S3
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.addSoundboard(guildOptions, soundBoard));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addSoundClip(SoundBoard soundBoard, SoundClip soundClip) {
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.addSoundClip(soundBoard, soundClip));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSoundboard(SoundBoard soundBoard) {
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.deleteSoundboard(soundBoard));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClip(SoundClip soundClip) {
        // todo delete folder on s3
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.deleteSoundClip(soundClip));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
