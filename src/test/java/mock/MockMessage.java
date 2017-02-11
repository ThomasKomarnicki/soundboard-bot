package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageTokenizer;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by tdk10 on 7/22/2016.
 */
public class MockMessage implements IMessage{

    private String content;
    private IChannel channel;

    private Attachment attachment;

    public MockMessage(String content) {
        this.content = content;
    }

    public MockMessage(String content, IChannel channel){
        this(content);
        this.channel = channel;
    }

    public MockMessage(String content, IChannel channel, Attachment attachment){
        this(content, channel);
        this.attachment = attachment;

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
        return new MockUser("mock_id","mock_name");
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
    public List<IChannel> getChannelMentions() {
        return null;
    }

    @Override
    public List<Attachment> getAttachments() {
        if(attachment != null) {
            List<Attachment> attachments = new ArrayList<>();
            attachments.add(attachment);
            return attachments;
        }else{
            return null;
        }
    }

    @Override
    public List<IEmbed> getEmbedded() {
        return null;
    }

    @Override
    public void reply(String s) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void reply(String s, EmbedObject embedObject) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public IMessage edit(String s) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage edit(String s, EmbedObject embedObject) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public boolean mentionsEveryone() {
        return false;
    }

    @Override
    public boolean mentionsHere() {
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
        return channel.getGuild();
    }

    @Override
    public String getFormattedContent() {
        return null;
    }

    @Override
    public List<IReaction> getReactions() {
        return null;
    }

    @Override
    public IReaction getReactionByIEmoji(IEmoji iEmoji) {
        return null;
    }

    @Override
    public IReaction getReactionByName(String s) {
        return null;
    }

    @Override
    public void removeAllReactions() throws RateLimitException, MissingPermissionsException, DiscordException {

    }

    @Override
    public void addReaction(IReaction iReaction) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void addReaction(IEmoji iEmoji) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void addReaction(String s) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void removeReaction(IUser iUser, IReaction iReaction) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void removeReaction(IReaction iReaction) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public MessageTokenizer tokenize() {
        return null;
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String getWebhookID() {
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
    public IShard getShard() {
        return null;
    }

    @Override
    public IMessage copy() {
        return null;
    }
}
