package xyz.doglandia.soundboard.model.guild;

import xyz.doglandia.soundboard.model.soundboard.ClipAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tdk10 on 11/6/2016.
 */
public class GuildClipAliasCollection implements ClipAliasCollection {

    private List<ClipAlias> aliases;

    public GuildClipAliasCollection(List<ClipAlias> aliases) {
        this.aliases = aliases;
    }

    public void addClipAlias(ClipAlias clipAlias){
        aliases.add(clipAlias);
    }

    @Override
    public ClipAlias getMatchingAliasForText(String messageText) {

        for(ClipAlias clipAlias : aliases){
            if(messageText.contains(clipAlias.getTargetText())){
                return clipAlias;
            }
        }

        return null;
    }
}
