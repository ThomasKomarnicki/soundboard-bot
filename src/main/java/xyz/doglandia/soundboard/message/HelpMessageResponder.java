package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IMessage;
import xyz.doglandia.soundboard.text.TextDispatcher;
import xyz.doglandia.soundboard.util.Sensitive;

/**
 * Created by tkomarnicki on 8/25/16.
 */
public class HelpMessageResponder implements MessageSession.MessageResponder {

    public static final String ADD_HELP = "Click the upload file button, in the comment put ***!add <sounbard name> <clip name>***";
    public static final String CREATE_SOUNDBOARD_HELP = "To create a soundboard, type ***!create soundboard <soundboard name>***";
    public static final String PLAY_HELP = "To play a sound from a soundboard, type ***!<soundboard name> <sound clip name>***\nTo see a list of sound clips for a soundboard, type ***!help <soundboard name>***";
    public static final String HELP_START = "Select one of the following:\n" +
            "(1) Help with creating a soundboard\n" +
            "(2) Help with adding a sound clip to a soundboard\n" +
            "(3) Help with playing a clip from a soundboard\n\n" +
            "(4) List Soundboards or type ***!soundboards***";
    public static final String GET_STARTED = "Hi, welcome to your brand new Soundboard Bot.\nThis bot allows you to create soundboards for your Discord server.\n" +
            "Soundboards are created right in the discord client. Sounds are played back through the Soundboard Bot when it is connected to a voice channel.\n" +
            "For help and support join this discord server: "+ Sensitive.INVITE_LINK+"\n" +
            "\ntldr: type ***!help*** to get started\n";

    TextDispatcher textDispatcher;
    MessageHandlerImpl messageHandler;

    public HelpMessageResponder(TextDispatcher textDispatcher, MessageHandlerImpl messageHandler){
        this.textDispatcher = textDispatcher;
        this.messageHandler = messageHandler;

    }

    @Override
    public boolean onMessageReceived(MessageSession messageSession, IMessage message) {

        switch (message.getContent()){
            case "2":
                textDispatcher.dispatchText(ADD_HELP, message.getChannel());
                messageSession.endSession();
                return true;
            case "1":
                textDispatcher.dispatchText(CREATE_SOUNDBOARD_HELP, message.getChannel());
                messageSession.endSession();
                return true;
            case "3":
                textDispatcher.dispatchText(PLAY_HELP, message.getChannel());
                messageSession.endSession();
                return true;
            case "4":
                messageHandler.listSoundboards(message.getChannel());
                messageSession.endSession();
                return true;
        }

        return false;
    }
}
