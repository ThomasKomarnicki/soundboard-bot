package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.AudioChannel;
import sx.blah.discord.handle.audio.IAudioManager;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.Image;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

/**
 * Created by tdk10 on 7/20/2016.
 */
public class MockGuild implements IGuild {
    @Override
    public String getOwnerID() {
        return null;
    }

    @Override
    public IUser getOwner() {
        return null;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getIconURL() {
        return null;
    }

    @Override
    public List<IChannel> getChannels() {
        return null;
    }

    @Override
    public IChannel getChannelByID(String s) {
        return null;
    }

    @Override
    public List<IUser> getUsers() {
        return null;
    }

    @Override
    public IUser getUserByID(String s) {
        return null;
    }

    @Override
    public List<IChannel> getChannelsByName(String s) {
        return null;
    }

    @Override
    public List<IVoiceChannel> getVoiceChannelsByName(String s) {
        return null;
    }

    @Override
    public List<IUser> getUsersByName(String s) {
        return null;
    }

    @Override
    public List<IUser> getUsersByName(String s, boolean b) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<IRole> getRoles() {
        return null;
    }

    @Override
    public List<IRole> getRolesForUser(IUser iUser) {
        return null;
    }

    @Override
    public IRole getRoleByID(String s) {
        return null;
    }

    @Override
    public List<IRole> getRolesByName(String s) {
        return null;
    }

    @Override
    public List<IVoiceChannel> getVoiceChannels() {
        return null;
    }

    @Override
    public IVoiceChannel getVoiceChannelByID(String s) {
        return null;
    }

    @Override
    public IVoiceChannel getAFKChannel() {
        return null;
    }

    @Override
    public int getAFKTimeout() {
        return 0;
    }

    @Override
    public IRole createRole() throws MissingPermissionsException, RateLimitException, DiscordException {
        return null;
    }

    @Override
    public List<IUser> getBannedUsers() throws RateLimitException, DiscordException {
        return null;
    }

    @Override
    public void banUser(IUser iUser) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void banUser(IUser iUser, int i) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void pardonUser(String s) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void kickUser(IUser iUser) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void editUserRoles(IUser iUser, IRole[] iRoles) throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public void setDeafenUser(IUser iUser, boolean b) throws MissingPermissionsException, DiscordException, RateLimitException {

    }

    @Override
    public void setMuteUser(IUser iUser, boolean b) throws DiscordException, RateLimitException, MissingPermissionsException {

    }

    @Override
    public void setUserNickname(IUser iUser, String s) throws MissingPermissionsException, DiscordException, RateLimitException {

    }

    @Override
    public void changeName(String s) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeRegion(IRegion iRegion) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeIcon(Optional<Image> optional) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeIcon(Image image) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeAFKChannel(Optional<IVoiceChannel> optional) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeAFKChannel(IVoiceChannel iVoiceChannel) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeAFKTimeout(int i) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void deleteGuild() throws DiscordException, RateLimitException, MissingPermissionsException {

    }

    @Override
    public void leaveGuild() throws DiscordException, RateLimitException {

    }

    @Override
    public IChannel createChannel(String s) throws DiscordException, MissingPermissionsException, RateLimitException {
        return null;
    }

    @Override
    public IVoiceChannel createVoiceChannel(String s) throws DiscordException, MissingPermissionsException, RateLimitException {
        return null;
    }

    @Override
    public IRegion getRegion() {
        return null;
    }

    @Override
    public void transferOwnership(IUser iUser) throws RateLimitException, MissingPermissionsException, DiscordException {

    }

    @Override
    public IRole getEveryoneRole() {
        return null;
    }

    @Override
    public List<IInvite> getInvites() throws DiscordException, RateLimitException {
        return null;
    }

    @Override
    public void reorderRoles(IRole... iRoles) throws DiscordException, RateLimitException, MissingPermissionsException {

    }

    @Override
    public int getUsersToBePruned(int i) throws DiscordException, RateLimitException {
        return 0;
    }

    @Override
    public int pruneUsers(int i) throws DiscordException, RateLimitException {
        return 0;
    }

    @Override
    public void addBot(String s, Optional<EnumSet<Permissions>> optional) throws MissingPermissionsException, DiscordException, RateLimitException {

    }

    @Override
    public void addBot(String s, EnumSet<Permissions> enumSet) throws MissingPermissionsException, DiscordException, RateLimitException {

    }

    @Override
    public AudioChannel getAudioChannel() throws DiscordException {
        return null;
    }

    @Override
    public IAudioManager getAudioManager() {
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
    public IGuild copy() {
        return null;
    }
}
