import org.junit.Test;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.DatabaseProvider;
import xyz.doglandia.soundboard.persistence.S3FileManager;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class DatabaseProviderTest {

    DatabaseProvider databaseProvider;

    public DatabaseProviderTest(){
        databaseProvider = DatabaseProvider.instantiate();

    }

    @Test
    public void testAddingGuild(){

//        GuildOptions guildOptions = createTestOptions();

        databaseProvider.createGuildOptions("test_guild_id_3");

    }

    @Test
    public void testUpdateGuild(){
        GuildOptions guildOptions = createTestOptions();

        List<String> roles = new ArrayList<>();
        roles.add("TestRole1");
        roles.add("TestRole2");
        guildOptions.setRollsThatCanAddClips(roles);

        databaseProvider.updateGuildOptions(guildOptions);

        roles = new ArrayList<>();
        roles.add("UpdatedRole1");
        roles.add("UpdatedRole2");
        guildOptions.setRollsThatCanAddClips(roles);

        databaseProvider.updateGuildOptions(guildOptions);

        GuildOptions refreshed = databaseProvider.getGuildOptionsByGuildId(guildOptions.getGuildId());

        assertArrayEquals(roles.toArray(), refreshed.getRollsThatCanAddClips().toArray());
    }

    @Test
    public void testQueryGuild(){

        GuildOptions guildOptions = databaseProvider.getGuildOptionsByGuildId("test_guild_id_2");
        assertTrue(guildOptions != null);
        assertEquals(guildOptions.getGuildId(), "test_guild_id_2");

//        assertNotNull(guildOptions.getSoundBoards());
//        assertTrue(guildOptions.getSoundBoards().size() > 0);

        SoundBoard pete = guildOptions.getSoundboard("Pete");
        assertNotNull(pete);
        assertTrue(pete.hasClip("apple"));
        assertTrue(pete.hasClip("value"));
        assertTrue(pete.getSoundClip("apple").getUrl().equals("https://s3.amazonaws.com/soundboard-app/staging/test_guild_id_2/Pete/apple.mp3"));
    }

    @Test
    public void testCreateDeleteSoundboard(){
        GuildOptions guildOptions = databaseProvider.getGuildOptionsByGuildId("test_guild_id_2");

        SoundBoard soundBoard = databaseProvider.createSoundboard(guildOptions, "testsoundboard1");

        guildOptions = databaseProvider.getGuildOptionsByGuildId(guildOptions.getGuildId());

//        assertNotNull(guildOptions.getSoundBoards());
//        assertTrue(guildOptions.getSoundBoards().size() > 0);
        assertTrue(guildOptions.hasSoundboard("testsoundboard1"));

        databaseProvider.deleteSoundboard(guildOptions.getSoundboard("testsoundboard1"));
    }

    @Test
    public void testCreateDeleteSoundClip(){
        GuildOptions guildOptions = databaseProvider.getGuildOptionsByGuildId("test_guild_id_2");

        SoundBoard soundBoard = guildOptions.getSoundboard("Pete");

        databaseProvider.createSoundClip(soundBoard, "test_clip", new File("test/test_clip.mp3"));

        guildOptions = databaseProvider.getGuildOptionsByGuildId(guildOptions.getGuildId());
        soundBoard = guildOptions.getSoundboard("Pete");

        assertNotNull(soundBoard.getClips().get("test_clip"));


        databaseProvider.deleteClip(soundBoard.getClips().get("test_clip"));
    }

    private static GuildOptions createTestOptions(){
        GuildOptions guildOptions = new GuildOptions();
        guildOptions.setGuildId("test_guild_id_2");

        List<String> roles = new ArrayList<>();
        roles.add("TestRole1");
        roles.add("TestRole2");
        guildOptions.setRollsThatCanAddClips(roles);
        return guildOptions;
    }
}
