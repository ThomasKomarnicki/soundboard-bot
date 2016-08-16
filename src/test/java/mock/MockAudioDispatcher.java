package mock;

import xyz.doglandia.soundboard.audio.AudioDispatcher;
import sx.blah.discord.handle.obj.IMessage;

import java.io.File;

/**
 * Created by tdk10 on 7/20/2016.
 */
public class MockAudioDispatcher implements AudioDispatcher {
    @Override
    public void playAudioClip(IMessage message, File file) {

    }

    @Override
    public void playAudioClip(IMessage message, String url) {

    }

    @Override
    public void stopAllAudio(IMessage message) {

    }
}
