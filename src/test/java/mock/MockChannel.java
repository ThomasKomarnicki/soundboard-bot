package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageList;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * Created by tdk10 on 7/20/2016.
 */
public class MockChannel implements IChannel {

    private String guildId;

    public MockChannel(String guildId) {
        this.guildId = guildId;
    }

    @Override
    public String getName() {
        return "TestChannel";
    }

    @Override
    public MessageList getMessages() {
        return null;
    }

    @Override
    public IMessage getMessageByID(String s) {
        return null;
    }

    @Override
    public IGuild getGuild() {
        return new MockGuild(guildId);
    }

    @Override
    public boolean isPrivate() {
        return false;
    }

    @Override
    public String getTopic() {
        return null;
    }

    @Override
    public String mention() {
        return null;
    }

    @Override
    public IMessage sendMessage(String s) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendMessage(String s, boolean b) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendFile(File file, String s) throws IOException, MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendFile(File file) throws IOException, MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendFile(InputStream inputStream, String s, String s1) throws IOException, MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendFile(InputStream inputStream, String s) throws IOException, MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IInvite createInvite(int i, int i1, boolean b) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public void toggleTypingStatus() {

    }

    @Override
    public boolean getTypingStatus() {
        return false;
    }

    @Override
    public void changeName(String s) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changePosition(int i) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeTopic(String s) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public void delete() throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public Map<String, PermissionOverride> getUserOverrides() {
        return null;
    }

    @Override
    public Map<String, PermissionOverride> getRoleOverrides() {
        return null;
    }

    @Override
    public EnumSet<Permissions> getModifiedPermissions(IUser iUser) {
        return null;
    }

    @Override
    public EnumSet<Permissions> getModifiedPermissions(IRole iRole) {
        return null;
    }

    @Override
    public void removePermissionsOverride(IUser iUser) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void removePermissionsOverride(IRole iRole) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void overrideRolePermissions(IRole iRole, EnumSet<Permissions> enumSet, EnumSet<Permissions> enumSet1) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void overrideUserPermissions(IUser iUser, EnumSet<Permissions> enumSet, EnumSet<Permissions> enumSet1) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public List<IInvite> getInvites() throws DiscordException, RateLimitException {
        return null;
    }

    @Override
    public List<IUser> getUsersHere() {
        return null;
    }

    @Override
    public List<IMessage> getPinnedMessages() throws RateLimitException, DiscordException {
        return null;
    }

    @Override
    public void pin(IMessage iMessage) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void unpin(IMessage iMessage) throws RateLimitException, DiscordException, MissingPermissionsException {

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
    public IChannel copy() {
        return null;
    }
}
