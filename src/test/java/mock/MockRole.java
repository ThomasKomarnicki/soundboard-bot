package mock;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.awt.*;
import java.util.EnumSet;

/**
 * Created by tdk10 on 8/27/2016.
 */
public class MockRole implements IRole {

    private String name;

    public MockRole(String name){
        this.name = name;
    }
    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public EnumSet<Permissions> getPermissions() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isManaged() {
        return false;
    }

    @Override
    public boolean isHoisted() {
        return false;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean isMentionable() {
        return false;
    }

    @Override
    public IGuild getGuild() {
        return null;
    }

    @Override
    public void changeColor(Color color) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeHoist(boolean b) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeName(String s) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changePermissions(EnumSet<Permissions> enumSet) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void changeMentionable(boolean b) throws RateLimitException, DiscordException, MissingPermissionsException {

    }

    @Override
    public void delete() throws MissingPermissionsException, RateLimitException, DiscordException {

    }

    @Override
    public String mention() {
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
    public IRole copy() {
        return null;
    }
}
