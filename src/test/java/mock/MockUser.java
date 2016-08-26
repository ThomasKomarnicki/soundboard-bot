package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.List;
import java.util.Optional;

/**
 * Created by tdk10 on 8/25/2016.
 */
public class MockUser implements IUser {

    private String id;
    private String name;

    public MockUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getGame() {
        return null;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public String getAvatar() {
        return null;
    }

    @Override
    public String getAvatarURL() {
        return null;
    }

    @Override
    public Presences getPresence() {
        return null;
    }

    @Override
    public String getDisplayName(IGuild iGuild) {
        return null;
    }

    @Override
    public String mention() {
        return null;
    }

    @Override
    public String mention(boolean b) {
        return null;
    }

    @Override
    public String getDiscriminator() {
        return null;
    }

    @Override
    public List<IRole> getRolesForGuild(IGuild iGuild) {
        return null;
    }

    @Override
    public Optional<String> getNicknameForGuild(IGuild iGuild) {
        return null;
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public void moveToVoiceChannel(IVoiceChannel iVoiceChannel) throws DiscordException, RateLimitException, MissingPermissionsException {

    }

    @Override
    public Optional<IVoiceChannel> getVoiceChannel() {
        return null;
    }

    @Override
    public List<IVoiceChannel> getConnectedVoiceChannels() {
        return null;
    }

    @Override
    public IPrivateChannel getOrCreatePMChannel() throws RateLimitException, DiscordException {
        return null;
    }

    @Override
    public boolean isDeaf(IGuild iGuild) {
        return false;
    }

    @Override
    public boolean isMuted(IGuild iGuild) {
        return false;
    }

    @Override
    public boolean isDeafLocally() {
        return false;
    }

    @Override
    public boolean isMutedLocally() {
        return false;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public IDiscordClient getClient() {
        return null;
    }

    @Override
    public IUser copy() {
        return null;
    }
}
