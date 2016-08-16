package xyz.doglandia.soundboard.model.soundboard;

import java.io.File;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class SoundClip {

    private int id;
    private int resourceType = 1;

    private String name;

    private transient String url;

    public SoundClip(String name, String url){
        this.name = name.toLowerCase();
        this.url = url;
    }

    public SoundClip(int id, String name, String url){
        this(name, url);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }
}
