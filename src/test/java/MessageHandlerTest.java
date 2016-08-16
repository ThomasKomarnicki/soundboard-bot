import xyz.doglandia.soundboard.audio.AudioDispatcher;
import xyz.doglandia.soundboard.audio.management.SoundboardFilesDataCreator;
import xyz.doglandia.soundboard.audio.management.SoundboardSoundManager;
import xyz.doglandia.soundboard.message.MessageHandler;
import xyz.doglandia.soundboard.message.MessageHandlerImpl;
import mock.MockChannel;
import mock.MockMessage;
import org.junit.Test;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import xyz.doglandia.soundboard.text.TextDispatcher;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by tdk10 on 7/20/2016.
 */
public class MessageHandlerTest {

    SoundboardSoundManager soundManager;

    public MessageHandlerTest(){
        soundManager = new SoundboardSoundManager(new SoundboardFilesDataCreator(new File("soundboards/")));
    }

    @Test
    public void testPlayAudioMessage(){
        MessageHandler messageHandler = new MessageHandlerImpl(new AudioDispatcher() {
            @Override
            public void playAudioClip(IMessage message, File file) {
                assertEquals(file.getName().toLowerCase(), "damn_son.mp3");
            }

            @Override
            public void playAudioClip(IMessage message, String url) {

            }

            @Override
            public void stopAllAudio(IMessage message) {

            }
        }, null, soundManager);

        boolean handled = messageHandler.handleMessage(new MockMessage("!mlg damn son"), new MockChannel());

        assertTrue(handled);
    }

    @Test
    public void testNoAudioMessage(){

        MessageHandler messageHandler = new MessageHandlerImpl(new AudioDispatcher() {
            @Override
            public void playAudioClip(IMessage message, File file) {

            }

            @Override
            public void playAudioClip(IMessage message, String url) {

            }

            @Override
            public void stopAllAudio(IMessage message) {

            }
        },null, soundManager);

        boolean handled = messageHandler.handleMessage(new MockMessage("!mlg let me be free"), new MockChannel());

        assertFalse(handled);
    }

    @Test
    public void testHelpMessage(){
        MessageHandler messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                assertTrue(message.contains( "!mlg airhorn"));
                assertTrue(message.contains( "!mlg damn son"));
                assertTrue(message.contains( "!mlg triple"));
                assertTrue(message.contains( "!mlg wombo"));
            }
        }, soundManager);

        boolean handled = messageHandler.handleMessage(new MockMessage("!mlg help"),  new MockChannel());

        assertFalse(handled);
    }
}
