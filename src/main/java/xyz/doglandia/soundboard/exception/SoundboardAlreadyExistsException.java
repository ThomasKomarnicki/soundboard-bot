package xyz.doglandia.soundboard.exception;

/**
 * Created by tdk10 on 8/24/2016.
 */
public class SoundboardAlreadyExistsException extends Exception {

    public SoundboardAlreadyExistsException(String soundboardName) {
        super("Soundboard \""+ soundboardName +"\" already exists");
    }
}
