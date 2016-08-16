package xyz.doglandia.soundboard;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import xyz.doglandia.soundboard.util.Sensitive;
import xyz.doglandia.soundboard.stats.ChannelStatistics;

/**
 * Created by tdk10 on 7/31/2016.
 */
public class MessageAdderBot {

    public static void main(String[] args){

        try {
            IDiscordClient client = new ClientBuilder().withToken(Sensitive.TOKEN).login();
            client.getDispatcher().registerListener(new IListener<GuildCreateEvent>() {
                @Override
                public void handle(GuildCreateEvent event) {
                    final IChannel channel = event.getGuild().getChannelsByName("many-messages").get(0);
//                    ChannelStatistics channelStatistics = new ChannelStatistics(channel);
//                    channelStatistics.getStatsForMessageCount();

                    for(int i =0; i < 2000; i++){
                        try {
                            channel.sendMessage(i+"message");

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (MissingPermissionsException e) {
                            e.printStackTrace();
                        } catch (RateLimitException e) {
                            e.printStackTrace();
                        } catch (DiscordException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
}
