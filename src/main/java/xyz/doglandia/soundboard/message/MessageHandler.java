package xyz.doglandia.soundboard.message;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;

import java.util.List;

/**
 * handles the following message contents:
 *
 *  Play Clip:
 *      !'soundboardName' 'clipName'
 *
 *  Add Clip:
 *      !add 'soundboardName' 'clipName' (with message attachment)
 *
 *  Create Soundboard:
 *      !create soundboard 'soundboardName'
 *
 *  Soundboard Help:
 *      !soundboard help
 *
 *  Help Flow:
 *      !help
 *
 *          To see more, enter the number associated with the following help topics
 *          (1) Help with creating a soundboard
 *          (2) Help with adding a sound clip to a soundboard
 *          (3) Help with playing a clip from a soundboard
 *
 *  Add Help:
 *      !help add
 *
 *   Soundboard General Help:
 *      !help soundboard
 *
 */
public interface MessageHandler {

    boolean handleMessage(IMessage message, IChannel chatChannel);
    boolean handleMention(IMessage message, IChannel chatChannel);

    void handleGuildCreated(IGuild guild);

    void handleBotRolesChanged(IGuild guild, List<IRole> roles);
}
