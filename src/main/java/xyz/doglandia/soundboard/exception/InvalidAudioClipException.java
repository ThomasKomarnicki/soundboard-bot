package xyz.doglandia.soundboard.exception;

/**
 * Created by tdk10 on 8/7/2016.
 */
public class InvalidAudioClipException extends Exception {

    public InvalidAudioClipException() {
        super("Audio clip must be an mp3 file");
    }
}
