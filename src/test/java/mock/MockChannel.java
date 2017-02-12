package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.IShard;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

import java.io.File;
import java.io.FileNotFoundException;
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
    public IMessage sendMessage(EmbedObject embedObject) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendMessage(String s, boolean b) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendMessage(String s, EmbedObject embedObject) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendMessage(String s, EmbedObject embedObject, boolean b) throws RateLimitException, DiscordException, MissingPermissionsException {
        return null;
    }


    @Override
    public IMessage sendFile(File file) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public IMessage sendFile(String s, File file) throws FileNotFoundException, DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendFile(EmbedObject embedObject, File file) throws FileNotFoundException, DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendFile(String s, InputStream inputStream, String s1) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendFile(EmbedObject embedObject, InputStream inputStream, String s) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendFile(String s, boolean b, InputStream inputStream, String s1) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendFile(String s, boolean b, InputStream inputStream, String s1, EmbedObject embedObject) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IMessage sendFile(MessageBuilder messageBuilder, InputStream inputStream, String s) throws DiscordException, RateLimitException, MissingPermissionsException {
        return null;
    }

    @Override
    public IInvite createInvite(int i, int i1, boolean b, boolean b1) throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }



    @Override
    public void toggleTypingStatus() {

    }

    @Override
    public void setTypingStatus(boolean b) {

    }

    @Override
    public boolean getTypingStatus() {
        return false;
    }

    @Override
    public void edit(String s, int i, String s1) throws DiscordException, RateLimitException, MissingPermissionsException {

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
    public List<IWebhook> getWebhooks() {
        return null;
    }

    @Override
    public IWebhook getWebhookByID(String s) {
        return null;
    }

    @Override
    public List<IWebhook> getWebhooksByName(String s) {
        return null;
    }

    @Override
    public IWebhook createWebhook(String s) throws MissingPermissionsException, DiscordException, RateLimitException {
        return null;
    }

    @Override
    public IWebhook createWebhook(String s, Image image) throws MissingPermissionsException, DiscordException, RateLimitException {
        return null;
    }

    @Override
    public IWebhook createWebhook(String s, String s1) throws MissingPermissionsException, DiscordException, RateLimitException {
        return null;
    }

    @Override
    public boolean isDeleted() {
        return false;
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
    public IChannel copy() {
        return null;
    }
}
