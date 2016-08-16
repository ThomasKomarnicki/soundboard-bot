package xyz.doglandia.soundboard;

import sx.blah.discord.Discord4J;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.util.LogMarkers;
import xyz.doglandia.soundboard.audio.DiscordAudioDispatcher;
import xyz.doglandia.soundboard.audio.management.SoundboardDataCreator;
import xyz.doglandia.soundboard.audio.management.SoundboardFilesDataCreator;
import xyz.doglandia.soundboard.audio.management.SoundboardSoundManager;
import xyz.doglandia.soundboard.audio.management.SoundsManager;
import xyz.doglandia.soundboard.discord.DiscordEventListener;
import xyz.doglandia.soundboard.message.MessageHandlerImpl;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;
import xyz.doglandia.soundboard.stats.ChannelStatistics;
import xyz.doglandia.soundboard.text.DiscordTextDispatcher;

import java.io.File;

/**
 * Created by tdk10 on 7/16/2016.
 */
public class SoundboardBot {

    private IDiscordClient client;


    public SoundboardBot(String token){

        try {
            client = new ClientBuilder().withToken(token).login();

        } catch (DiscordException e) {
            e.printStackTrace();
        }

        SoundboardDataCreator soundboardDataCreator = new SoundboardFilesDataCreator(new File("soundboards/"));
        SoundsManager soundsManager = new SoundboardSoundManager(soundboardDataCreator);

        DiscordEventListener eventListener = new DiscordEventListener(new MessageHandlerImpl(new DiscordAudioDispatcher(), new DiscordTextDispatcher(), soundsManager));
        client.getDispatcher().registerListener(eventListener);



    }


}
