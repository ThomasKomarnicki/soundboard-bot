import org.junit.Test;
import xyz.doglandia.soundboard.data.SoundboardDataController;
import xyz.doglandia.soundboard.data.DataController;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import static org.junit.Assert.*;

public class DataControllerTest {

    DataController dataController;

    public DataControllerTest(){
        dataController = new SoundboardDataController();

    }

    @Test
    public void testGettingSoundboard(){
        SoundBoard soundBoard = dataController.getSoundboard("test_guild_id_2", "Pete");

        assertNotNull(soundBoard);
        assertNotNull(soundBoard.getSoundClip("hello"));


    }

    @Test
    public void testGettingSoundClip(){
        SoundClip soundClip = dataController.getSoundClip("test_guild_id_2", "Pete", "hello");

        assertNotNull(soundClip);
    }

}
