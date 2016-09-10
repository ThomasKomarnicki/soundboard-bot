import mock.MockGuild;
import mock.MockRole;
import org.junit.Test;
import sx.blah.discord.handle.obj.IRole;
import xyz.doglandia.soundboard.data.DataController;
import xyz.doglandia.soundboard.data.SoundboardDataController;
import xyz.doglandia.soundboard.message.MessageHandler;
import xyz.doglandia.soundboard.message.MessageHandlerImpl;
import xyz.doglandia.soundboard.util.Util;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tdk10 on 8/27/2016.
 */
public class GuildRolesTest {

    @Test
    public void testRoleChange(){

        DataController dataController = new SoundboardDataController();

        MessageHandler messageHandler = new MessageHandlerImpl(null, null, dataController);

        List<IRole> roles = new ArrayList<>();
        roles.add(new MockRole("test_role_1"));
        roles.add(new MockRole("test_role_2"));

        messageHandler.handleBotRolesChanged(new MockGuild(MessageHandlerTest.GUILD_ID), roles);

        List<String> userRoles = Util.ListRoleNames(roles);

        assertTrue(dataController.matchesPermissions(MessageHandlerTest.GUILD_ID, userRoles));



    }
}
