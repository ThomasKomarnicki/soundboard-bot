package xyz.doglandia.soundboard.model.guild;

import xyz.doglandia.soundboard.model.soundboard.ClipAlias;

/**
 * Created by tdk10 on 11/6/2016.
 */
public interface ClipAliasCollection {

    ClipAlias getMatchingAliasForText(String messageText);
}
