import mock.MockAudioDispatcher;
import xyz.doglandia.soundboard.audio.AudioDispatcher;
import xyz.doglandia.soundboard.audio.management.SoundboardController;
import xyz.doglandia.soundboard.audio.management.SoundboardFilesDataCreator;
import xyz.doglandia.soundboard.audio.management.SoundboardSoundManager;
import xyz.doglandia.soundboard.audio.management.SoundboardsController;
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

    private static final String GUILD_ID = "198587533750304769";

    SoundboardController soundboardController;

    public MessageHandlerTest(){
//        soundManager = new SoundboardSoundManager(new SoundboardFilesDataCreator(new File("soundboards/")));
        soundboardController = new SoundboardsController();
    }

    @Test
    public void testPlayAudioMessage(){
        MessageHandler messageHandler = new MessageHandlerImpl(new MockAudioDispatcher() {

            @Override
            public void playAudioClip(IMessage message, String url) {

            }

        }, null, soundboardController);

        boolean handled = messageHandler.handleMessage(new MockMessage("!mlg damn son"), new MockChannel(GUILD_ID));

        assertTrue(handled);
    }

    @Test
    public void testNoAudioMessage(){

        MessageHandler messageHandler = new MessageHandlerImpl(new MockAudioDispatcher() {

            @Override
            public void playAudioClip(IMessage message, String url) {

            }


        },null, soundboardController);

        boolean handled = messageHandler.handleMessage(new MockMessage("!mlg let me be free"), new MockChannel(GUILD_ID));

        assertFalse(handled);
    }

    @Test
    public void testHelpMessage(){
        MessageHandler messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                assertTrue(message.contains( "!Pete hello"));
                assertTrue(message.contains( "!Pete apple"));
            }
        }, soundboardController);

        boolean handled = messageHandler.handleMessage(new MockMessage("!Pete help"),  new MockChannel(GUILD_ID));

        assertTrue(handled);
    }

    @Test
    public void testHelpSoundboardNotFound() {
        final boolean[] results = new boolean[2];
        MessageHandler messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null;
                results[1] = message.contains("not found");
            }
        }, soundboardController);

        boolean handled = messageHandler.handleMessage(new MockMessage("!this_soundboard_doesnt_exist help"),  new MockChannel(GUILD_ID));

        assertFalse(handled);
        assertTrue(results[0]);
        assertTrue(results[1]);
    }

    @Test
    public void testHelpAdd(){
        final boolean[] results = new boolean[2];
        MessageHandlerImpl messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null; // should happen before assertTrue on results[0]
//                assertNotNull(message);
//                assertTrue(message.contains( "!Pete hello"));
                System.out.println("testHelpAdd() result:");
                System.out.println(message);

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!add help", channel), channel);
        assertTrue(handled);
        assertTrue(results[0]);
        System.out.println("finished testHelpAdd()");
    }

    @Test
    public void testCreateSoundboard(){
        final boolean[] results = new boolean[2];
        MessageHandlerImpl messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null; // should happen before assertTrue on results[0]

                System.out.println("testCreateSoundboard() result:");
                System.out.println(message);

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!create soundboard TestBoard", channel), channel);

        assertTrue(handled);

        assertTrue(results[0]);

    }
}
