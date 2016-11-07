package xyz.doglandia.soundboard.model.soundboard;

/**
 * Created by tdk10 on 11/6/2016.
 */
public class ClipAlias {

    private SoundClip soundClip;

    private String targetText;

    public ClipAlias(SoundClip soundClip, String targetText){
        this.soundClip = soundClip;
        this.targetText = targetText;
    }

    public SoundClip getSoundClip() {
        return soundClip;
    }

    public void setSoundClip(SoundClip soundClip) {
        this.soundClip = soundClip;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }
}
