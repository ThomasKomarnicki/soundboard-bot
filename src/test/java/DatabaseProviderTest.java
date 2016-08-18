import org.junit.Test;
import xyz.doglandia.soundboard.model.guild.GuildOptions;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;
import xyz.doglandia.soundboard.persistence.DatabaseProvider;
import xyz.doglandia.soundboard.persistence.S3FileManager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 8/12/2016.
 */
public class DatabaseProviderTest {

    DatabaseProvider databaseProvider;

    public DatabaseProviderTest(){
        databaseProvider = new DatabaseProvider(new S3FileManager());

    }

    @Test
    public void testAddingGuild(){

        GuildOptions guildOptions = createTestOptions();

        databaseProvider.addNewGuildOptions(guildOptions);

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

        assertNotNull(guildOptions.getSoundBoards());
        assertTrue(guildOptions.getSoundBoards().size() > 0);

        SoundBoard pete = guildOptions.getSoundBoards().get("Pete");
        assertNotNull(pete);
        assertTrue(pete.hasClip("apple"));
        assertTrue(pete.hasClip("value"));
        assertTrue(pete.getSoundClip("apple").getUrl().equals("https://s3.amazonaws.com/soundboard-app/staging/test_guild_id_2/Pete/apple.mp3"));
    }

    @Test
    public void testCreateDeleteSoundboard(){
        GuildOptions guildOptions = databaseProvider.getGuildOptionsByGuildId("test_guild_id_2");

        SoundBoard soundBoard = new SoundBoard();
        soundBoard.setName("TestSoundboard1");
        databaseProvider.createSoundboard(guildOptions, soundBoard);

        guildOptions = databaseProvider.getGuildOptionsByGuildId(guildOptions.getGuildId());

        assertNotNull(guildOptions.getSoundBoards());
        assertTrue(guildOptions.getSoundBoards().size() > 0);
        assertTrue(guildOptions.getSoundBoards().containsKey("TestSoundboard1"));

        databaseProvider.deleteSoundboard(guildOptions.getSoundBoards().get("TestSoundboard1"));
    }

    @Test
    public void testCreateDeleteSoundClip(){
        GuildOptions guildOptions = databaseProvider.getGuildOptionsByGuildId("test_guild_id_2");

        SoundBoard soundBoard = guildOptions.getSoundBoards().get("Pete");
        SoundClip soundClip = new SoundClip("test_sound_clip","http://www.google.com");

        databaseProvider.addSoundClip(soundBoard, soundClip);


        guildOptions = databaseProvider.getGuildOptionsByGuildId(guildOptions.getGuildId());
        soundBoard = guildOptions.getSoundBoards().get("Pete");

        assertNotNull(soundBoard.getClips().get("test_sound_clip"));


        databaseProvider.deleteClip(soundBoard.getClips().get("test_sound_clip"));
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
