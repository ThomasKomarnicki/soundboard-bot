import org.junit.Test;
import xyz.doglandia.soundboard.audio.management.SoundboardsController;
import xyz.doglandia.soundboard.audio.management.SoundsManager;
import xyz.doglandia.soundboard.model.soundboard.SoundBoard;
import xyz.doglandia.soundboard.model.soundboard.SoundClip;

import static org.junit.Assert.*;

public class SoundboardControllerTest {

    SoundsManager soundsManager;

    public SoundboardControllerTest(){
        soundsManager = new SoundboardsController();

    }

    @Test
    public void testGettingSoundboard(){
        SoundBoard soundBoard = soundsManager.getSoundboard("test_guild_id_2", "Pete");

        assertNotNull(soundBoard);
        assertNotNull(soundBoard.getSoundClip("hello"));


    }

    @Test
    public void testGettingSoundClip(){
        SoundClip soundClip = soundsManager.getSoundClip("test_guild_id_2", "Pete", "hello");

        assertNotNull(soundClip);
    }

}
