package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by tdk10 on 7/22/2016.
 */
public class MockMessage implements IMessage{

    private String content;
    private IChannel channel;

    public MockMessage(String content) {
        this.content = content;
    }

    public MockMessage(String content, IChannel channel){
        this(content);
        this.channel = channel;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public IChannel getChannel() {
        return channel;
    }

    @Override
    public IUser getAuthor() {
        return null;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return null;
    }

    @Override
    public List<IUser> getMentions() {
        return null;
    }

    @Override
    public List<IRole> getRoleMentions() {
        return null;
    }

    @Override
    public List<Attachment> getAttachments() {
        return null;
    }

    @Override
    public void reply(String s) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public IMessage edit(String s) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public boolean mentionsEveryone() {
        return false;
    }

    @Override
    public void delete() throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public Optional<LocalDateTime> getEditedTimestamp() {
        return null;
    }

    @Override
    public boolean isPinned() {
        return false;
    }

    @Override
    public IGuild getGuild() {
        return null;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public IDiscordClient getClient() {
        return null;
    }

    @Override
    public IMessage copy() {
        return null;
    }
}
