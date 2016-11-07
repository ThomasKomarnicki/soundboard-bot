package xyz.doglandia.soundboard.persistence;

import org.apache.commons.io.FilenameUtils;
import xyz.doglandia.soundboard.BotEnvironment;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.ClipAlias;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.database.QueryBuilder;
import xyz.doglandia.soundboard.persistence.database.QueryResultParser;

import java.io.File;
import java.sql.*;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class DatabaseProvider implements DataProvider {

    private static DatabaseProvider instance;
    public static DatabaseProvider instantiate(){
        if(instance == null){
            instance = new DatabaseProvider(new S3FileManager());
        }
        return instance;
    }

    private static final String DB_NAME = "soundboardapp_1";

    private static final String GUILD_ID = "guild_id";




    private Connection connection;

    private String databaseName;

    private FilesManager filesManager;

    private QueryBuilder queryBuilder;
    private QueryResultParser queryResultParser;

    private DatabaseProvider(FilesManager filesManager){
        this.filesManager = filesManager;
        databaseName = DB_NAME;

        queryResultParser = new QueryResultParser();

        queryBuilder = new QueryBuilder(databaseName);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = BotEnvironment.getInstance().getDatabaseConnection()+databaseName;
        Properties props = new Properties();
        String[] creds = BotEnvironment.getInstance().getDatabaseCreds();
        props.setProperty("user",creds[0]);
        props.setProperty("password", creds[1]);
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

                GuildOptions guildOptions = queryResultParser.createGuildOptionsFromJoinedResults(resultSet);

                query = queryBuilder.getGlobalSoundboards();
                resultSet = st.executeQuery(query);
                Collection<SoundBoard> globalSoundboards = queryResultParser.createSoundBoardList(resultSet);
                for(SoundBoard soundBoard : globalSoundboards){
                    guildOptions.addGlobalSoundboard(soundBoard);
                }

                query = queryBuilder.getClipAliasesForGuild(guildId);
                resultSet = st.executeQuery(query);
                List<ClipAlias> aliases = queryResultParser.createClipAliases(resultSet, guildOptions);
                guildOptions.setClipAliases(aliases);

                return guildOptions;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createGuildOptions(String guildId) {

        try {
            Statement st = connection.createStatement();
            String query = queryBuilder.createNewGuildOptions(guildId);
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
    public SoundBoard createSoundboard(GuildOptions guildOptions, String soundboardName) {
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.addSoundboard(guildOptions, soundboardName.toLowerCase(), soundboardName));

            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(queryBuilder.getSoundboard(guildOptions, soundboardName.toLowerCase()));
            SoundBoard soundBoard = queryResultParser.createSoundBoard(guildOptions, resultSet);
            return soundBoard;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SoundClip createSoundClip(SoundBoard soundBoard, String name, File file) {
        String url = filesManager.uploadFile(createFileKey(soundBoard, name, file), file);

        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.addSoundClip(soundBoard, name, url));

            // query recently added sound clip and add to soundboard
//            if(result){
                st = connection.createStatement();
                ResultSet soundClipResults = st.executeQuery(queryBuilder.getSoundClip(soundBoard, name));
                SoundClip soundClip = queryResultParser.createSoundClip(soundClipResults);
                return soundClip;
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        // todo delete file on s3
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.deleteSoundClip(soundClip));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSoundboardByName(String soundboardName){

        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.deleteSoundboardByName(soundboardName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSoundClipByName(String clipName){
        try {
            Statement st = connection.createStatement();
            st.execute(queryBuilder.deleteClipByName(clipName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean soundboardExists(String guildId, String soundboardName){
        try {
            Statement st = connection.createStatement();
            return st.execute(queryBuilder.soundboardExists(guildId, soundboardName));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean soundClipExists(String guildId, String soundboardName, String clipName){
        try {
            Statement st = connection.createStatement();
            return st.execute(queryBuilder.soundClipExists(guildId, soundboardName, clipName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String createFileKey(SoundBoard soundBoard, String name, File file){
        String ext = FilenameUtils.getExtension(file.getName());
        return BotEnvironment.getInstance().getS3BucketDivision() +"/"+soundBoard.getGuildOptions().getGuildId()+"/"+soundBoard.getNameAsKey()+"/"+name+"."+ext;
    }

}
