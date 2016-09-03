import mock.MockAudioDispatcher;
import org.junit.After;
import xyz.doglandia.soundboard.audio.management.SoundboardController;
import xyz.doglandia.soundboard.audio.management.SoundboardsController;
import xyz.doglandia.soundboard.message.HelpMessageResponder;
import xyz.doglandia.soundboard.message.MessageHandler;
import xyz.doglandia.soundboard.message.MessageHandlerImpl;
import mock.MockChannel;
import mock.MockMessage;
import org.junit.Test;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import xyz.doglandia.soundboard.persistence.DatabaseProvider;
import xyz.doglandia.soundboard.text.TextDispatcher;

import static org.junit.Assert.*;

/**
 * requires environment to
 */
public class MessageHandlerTest {

    public static final String GUILD_ID = "198587533750304769";

    SoundboardController soundboardController;

    DatabaseProvider databaseProvider;

    public MessageHandlerTest(){
//        soundManager = new SoundboardSoundManager(new SoundboardFilesDataCreator(new File("soundboards/")));
        soundboardController = new SoundboardsController();

        databaseProvider = DatabaseProvider.instantiate();
    }


    @Test
    public void testPlayAudioMessage(){
        final boolean[] results = new boolean[1];
        MessageHandler messageHandler = new MessageHandlerImpl(new MockAudioDispatcher() {

            @Override
            public void playAudioClip(IMessage message, String url) {
                results[0] = url != null;
            }

        }, null, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!pete hello", channel), channel);
        assertTrue(results[0]);
        assertTrue(handled);
    }

    @Test
    public void testNoAudioMessage(){

        MessageHandler messageHandler = new MessageHandlerImpl(new MockAudioDispatcher() {

            @Override
            public void playAudioClip(IMessage message, String url) {

            }


        }, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!pete let me be free", channel), channel);

        assertFalse(handled);
    }

    @Test
    public void testHelpSession(){
        final int[] messageCount = new int[]{0};
        final boolean[] results = new boolean[2];

        MessageHandler messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                if(messageCount[0] == 0){
                    results[0] = message.equals(HelpMessageResponder.HELP_START);
                }else if(messageCount[0] == 1){
                    results[0] = message.equals(HelpMessageResponder.ADD_HELP);
                }
                else if(messageCount[0] == 2){
                    results[0] = message.equals(HelpMessageResponder.HELP_START);
                }else if(messageCount[0] == 3){
                    results[0] = message.equals(HelpMessageResponder.CREATE_SOUNDBOARD_HELP);
                }

                messageCount[0] = messageCount[0] + 1;
            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!help", channel), channel);
        assertTrue(handled);
        assertTrue(results[0]);

        handled = messageHandler.handleMessage(new MockMessage("1", channel), channel);
        assertTrue(handled);
        assertTrue(results[0]);

        handled = messageHandler.handleMessage(new MockMessage("!help", channel), channel);
        assertTrue(handled);
        assertTrue(results[0]);

        handled = messageHandler.handleMessage(new MockMessage("2", channel), channel);
        assertTrue(handled);
        assertTrue(results[0]);

    }

    @Test
    public void testHelpMessage(){
        MessageHandler messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                assertTrue(message.contains("!pete hello"));

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!help pete", channel),  channel);

        assertTrue(handled);
    }

    @Test
    public void testHelpForEmptySoundboard(){

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

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!this_soundboard_doesnt_exist dog", channel),  channel);

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
//                System.out.println("testHelpAdd() result:");
//                System.out.println(message);

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!help add", channel), channel);
        assertTrue(handled);
        assertTrue(results[0]);
//        System.out.println("finished testHelpAdd()");
    }

    @Test
    public void testCreateSoundboard(){
        final boolean[] results = new boolean[2];
        MessageHandlerImpl messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null; // should happen before assertTrue on results[0]

                results[1] = message.contains("soundboard \"TestBoard\" created! Type:");

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!create soundboard TestBoard", channel), channel);

        boolean existsInDatabase = databaseProvider.soundboardExists(GUILD_ID, "TestBoard");
        assertTrue(existsInDatabase);
        assertTrue(results[0]);
        assertTrue(results[1]);
    }

    @Test
    public void testDuplicateSoundboard(){
        final boolean[] results = new boolean[2];
        MessageHandlerImpl messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null; // should happen before assertTrue on results[0]
                results[1] = message.contains("already exists");

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);
        boolean handled = messageHandler.handleMessage(new MockMessage("!create soundboard pete", channel), channel);

        assertTrue(results[0]);
        assertTrue(results[1]);
    }

    @Test
    public void testAddClip(){
        final boolean[] results = new boolean[2];
        MessageHandlerImpl messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null; // should happen before assertTrue on results[0]
                results[1] = message.contains("Clip added");

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);

        IMessage.Attachment attachment = new IMessage.Attachment("test_sound", 1000, null, "https://s3.amazonaws.com/soundboard-app/extra/test_sound.mp3");
        boolean handled = messageHandler.handleMessage(new MockMessage("!add pete test_sound", channel, attachment), channel);

        boolean existsInDatabase = databaseProvider.soundClipExists(GUILD_ID, "pete", "test_sound");
        assertTrue(existsInDatabase);

        assertTrue(results[0]);
        assertTrue(results[1]);

        assertTrue(handled);
    }

    @Test
    public void testAddClipLongName(){
        final boolean[] results = new boolean[2];
        MessageHandlerImpl messageHandler = new MessageHandlerImpl(null, new TextDispatcher() {
            @Override
            public void dispatchText(String message, IChannel chatChannel) {
                results[0] = message != null; // should happen before assertTrue on results[0]
                results[1] = message.contains("Clip added");

            }
        }, soundboardController);

        IChannel channel = new MockChannel(GUILD_ID);

        IMessage.Attachment attachment = new IMessage.Attachment("test_sound", 1000, null, "https://s3.amazonaws.com/soundboard-app/extra/test_sound.mp3");
        boolean handled = messageHandler.handleMessage(new MockMessage("!add pete test sound with space", channel, attachment), channel);

        boolean existsInDatabase = databaseProvider.soundClipExists(GUILD_ID, "pete", "test sound with space");
        assertTrue(existsInDatabase);

        assertTrue(results[0]);
        assertTrue(results[1]);

        assertTrue(handled);
    }



    @After
    public void cleanUp(){

        // delete test rows


        databaseProvider.deleteSoundClipByName("test_sound");
        databaseProvider.deleteSoundboardByName("testboard");
        databaseProvider.deleteSoundClipByName("test sound with space");

    }
}
