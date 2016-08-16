package xyz.doglandia.soundboard.exception;

/**
 * Created by tdk10 on 8/7/2016.
 */
public class SoundboardExistException extends Exception {

    public SoundboardExistException(String soundboardName) {
        super("A soundboard named "+soundboardName+" does not exist");
    }
}
